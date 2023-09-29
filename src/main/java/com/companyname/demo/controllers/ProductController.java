package com.companyname.demo.controllers;

import com.companyname.demo.dto.ProductDTO;
import com.companyname.demo.search.ProductSearchFilter;
import com.companyname.demo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> filter(ProductSearchFilter filter, Pageable pageable) {
        return ResponseEntity.ok(productService.filter(filter, pageable));
    }
}