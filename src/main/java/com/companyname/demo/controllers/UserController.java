package com.companyname.demo.controllers;

import com.companyname.demo.dtos.UserDTO;
import com.companyname.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hello")
    ResponseEntity<String> getMessage() {
        return new ResponseEntity<>("Hello message.", HttpStatus.OK);
    }

    @GetMapping("details")
    ResponseEntity<Void> printDetails() {
        userService.printAllDetails();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
