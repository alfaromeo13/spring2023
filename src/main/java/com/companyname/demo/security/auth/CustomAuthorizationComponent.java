package com.companyname.demo.security.auth;

import com.companyname.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("customAuth")
@RequiredArgsConstructor
public class CustomAuthorizationComponent {//this is generally called DATA SECURITY
    //Bean name would be customAuthorizationComponent and to
    // make it shorter we write "authorization" in @Component

    private final UserRepository userRepository;

    public boolean hasPermission() {
        //we write our DATA SECURITY LOGIC
        return true;
    }

    public boolean hasPermissionToDelete(Integer id) {
        return true;
    }
}