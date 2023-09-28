package com.companyname.demo.controllers;


import com.companyname.demo.filters.ProductSearchFilter;
import com.companyname.demo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Void> searchByFilter(ProductSearchFilter filter) {
        productService.filter(filter);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}