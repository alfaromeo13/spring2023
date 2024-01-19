package com.companyname.demo.security.controller;

import com.companyname.demo.security.dto.JwtTokenDTO;
import com.companyname.demo.security.dto.LoginDTO;
import com.companyname.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authenticate")
public class AuthController {

    //here we expose APIS for login and register
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody LoginDTO login) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    login.getUsername(),
                    login.getPassword()
            );
            // this does all background logic for us.Checks users password with BCrypt
            Authentication auth = authenticationManager.authenticate(authentication);
            log.info("Authentication after successful login: {}", auth);
            JwtTokenDTO token = tokenProvider.generateToken(auth, login.isRememberMe());
            SecurityContextHolder.getContext().setAuthentication(auth);
            /*if we use OTP method then: (put the comment on the line above)!
            JwtTokenDTO token = tokenProvider.createTokenAfterVerifiedOtp(login.getUsername(), login.isRememberMe());
            otpService.generateOtpAndSendEmail(login.getUsername());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // OK (200 | 201 | 204)
             */
            return new ResponseEntity<>(token, HttpStatus.OK); // OK (200 | 201 | 204)
        } catch (Exception e) {
            log.error("Error occurred on login. Message: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }
    }

/* if we want to verify OTP code then:
    @PostMapping("verify")
    public ResponseEntity<JwtTokenDTO> verify(@RequestBody VerifyOtpDTO verifyOtpDTO) {
        boolean isOtpValid = otpService.isOtpValid(verifyOtpDTO.getUsername(), verifyOtpDTO.getOtpCode());
        if (!isOtpValid) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }

        JwtTokenDTO tokenDTO = tokenProvider.createTokenAfterVerifiedOtp(
                verifyOtpDTO.getUsername(),
                verifyOtpDTO.isRememberMe()
        );

        return new ResponseEntity<>(tokenDTO, HttpStatus.CREATED); // 201 | 200
    }
    */
}