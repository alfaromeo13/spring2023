package com.companyname.demo.controllers;

import com.companyname.demo.dto.ProductDTO;
import com.companyname.demo.search.ProductSearchFilter;
import com.companyname.demo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody ProductDTO product) {
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> transactionTest(@PathVariable Integer id) {
        productService.findWithTransaction(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}