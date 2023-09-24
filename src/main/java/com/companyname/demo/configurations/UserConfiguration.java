package com.companyname.demo.configurations;

import com.companyname.demo.dto.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    //we create 2 custom beans from same class in bean container
    @Bean //method name is alias for bean in container
    public UserDTO userDTO(){
        return new UserDTO(1,"Jovan Vukovic",22,"mosor");
    }

    @Bean
    public UserDTO user2DTO(){
        return new UserDTO(2,"Vlado Doderovic",22,"zeta");
    }
}