package com.companyname.demo.controllers;

import com.companyname.demo.dto.CreatePostDTO;
import com.companyname.demo.dto.PostDTO;
import com.companyname.demo.services.ExternalAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/external-test")
public class ExternalAPIController {

    private final ExternalAPIService externalAPIService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return new ResponseEntity<>(externalAPIService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDTO> getAllPosts(@PathVariable Integer id) {
        return new ResponseEntity<>(externalAPIService.getPostById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@RequestBody CreatePostDTO post) {
        return new ResponseEntity<>(externalAPIService.create(post), HttpStatus.CREATED);
    }
}