package com.companyname.demo.security.jwt;

import com.companyname.demo.entities.User;
import com.companyname.demo.repositories.UserRepository;
import com.companyname.demo.security.dto.JwtTokenDTO;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.validityInMinutes}")
    private long tokenValidityInMinutes;

    @Value("${jwt.refreshTokenValidityInMinutes}")
    private long refreshTokenValidityInMinutes;
    private final UserRepository userRepository;
    private static final String AUTH_KEY = "auth";

    public JwtTokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public JwtTokenDTO generateToken(Authentication authentication, boolean rememberMe) {
        // Collection<GrantedAuthority>
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // ADMINISTRATOR,MANAGER (role = String)

        String accessToken = createToken(authorities, authentication, tokenValidityInMinutes);
        String refreshToken = rememberMe ?
                createToken(authorities, authentication, refreshTokenValidityInMinutes) : null;
        return new JwtTokenDTO(accessToken, refreshToken); // accessToken = required, refreshToken = optional
    }

    private String createToken(String authorities, Authentication authentication, long validityInMinutes) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTH_KEY, authorities)
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .setExpiration(new Date(
                        System.currentTimeMillis() + validityInMinutes * 60_000
                ))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();

        String username = claims.getSubject();
        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTH_KEY).toString().split(",")) // [ROLE_ADMIN, ROLE_DEVELOPER]
                .map(SimpleGrantedAuthority::new)
                .toList();
        //null -> we skip password we only want username and roles
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException e) {// the token is invalid or has expired
            return false;
        }
    }

    public JwtTokenDTO createTokenAfterVerifiedOtp(String username, boolean rememberMe) {
        User user = userRepository.findByUsernameWithRoles(username).orElseThrow(EntityNotFoundException::new);
        List<GrantedAuthority> authorities = user.getRoles().stream().map(
                role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName())
        ).toList();
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, user.getPassword(), authorities);
        return generateToken(authentication, rememberMe);
    }
}