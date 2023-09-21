package com.companyname.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/users/")
public class UserController {

    @GetMapping("hello")
    ResponseEntity<String> getMessage(){
        return new ResponseEntity<>("Hello message.", HttpStatus.OK);
    }
}
