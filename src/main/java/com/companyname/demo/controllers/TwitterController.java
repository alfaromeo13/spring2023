package com.companyname.demo.controllers;

import com.companyname.demo.services.TwitterApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/twitter")
public class TwitterController {

    private final TwitterApiService twitterApiService;

    @GetMapping
    ResponseEntity<List<String>> getAllTweets(){
        return new ResponseEntity<>(twitterApiService.getAllTweets(), HttpStatus.OK);
    }

    //for testing purposes only
    @GetMapping("test")
    ResponseEntity<Void> getInstagramPosts(){
        twitterApiService.getAllInstagramPosts();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
