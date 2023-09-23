package com.companyname.demo.services;

import com.companyname.demo.dtos.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserService {

    @Autowired
    @Qualifier("userDTO") //this finds alias from spring bean container
    private UserDTO user1;

    @Autowired
    @Qualifier("user2DTO")
    private UserDTO user2;

    public void printAllDetails() {
        log.info("User 1 details -> {}", user1);
        log.info("User 2 details -> {}", user2);
    }

    public List<UserDTO> getAll() {
        return List.of(
                new UserDTO("jovan",22),
                new UserDTO("Darko",21),
                new UserDTO("Marko",20)
        );
    }
}