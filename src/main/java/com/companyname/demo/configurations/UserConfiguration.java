package com.companyname.demo.configurations;

import com.companyname.demo.dtos.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    //we create 2 custom beans from same class in bean container
    @Bean //method name is alias for bean in container
    public UserDTO userDTO(){
        return new UserDTO("Jovan Vukovic",22);
    }

    @Bean
    public UserDTO user2DTO(){
        return new UserDTO("Vlado Doderovic",22);
    }
}