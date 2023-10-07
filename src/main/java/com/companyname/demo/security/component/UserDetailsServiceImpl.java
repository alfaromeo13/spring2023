package com.companyname.demo.security.component;

import com.companyname.demo.entities.User;
import com.companyname.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //this username is from AuthController authenticationManager.authenticate(auth);
        //and auth was from new UsernameAndPasswordAuthenticationFilter(login.getUsername())
        Optional<User> userOptional = userService.findByUsernameWithRoles(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<GrantedAuthority> authorities = user.getRoles()
                    .stream()
                    .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName()))
                    .toList();

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), authorities
            );
            //with this AuthenticationManager checks validity of our password!
            //it will use BCrypt implementation to decode and to check
            //whether login password matches with our hashed password in db.
            //also if our user doesn't have ANY ROLE, spring will say he is unauthenticated!!!
            //so user has to have at least one role in order for this to work!
            //at the end authentication object is created which has username
            //and authorities (token)
        } else throw new UsernameNotFoundException("User with username " + username + " not found!");
    }

}