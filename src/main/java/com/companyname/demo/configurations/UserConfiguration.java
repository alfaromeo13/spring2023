package com.companyname.demo.configurations;

import com.companyname.demo.dto.UserTestDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    //we create 2 custom beans from same class in bean container
    @Bean //method name is alias for bean in container
    public UserTestDTO userDTO() {
        return new UserTestDTO(1L, "Jovan Vukovic", 22, "mosor");
    }

    @Bean
    public UserTestDTO user2DTO() {
        return new UserTestDTO(2L, "Vlado Doderovic", 22, "zeta");
    }
}