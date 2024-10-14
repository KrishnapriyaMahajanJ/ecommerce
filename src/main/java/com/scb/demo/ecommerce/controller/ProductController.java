package com.scb.demo.ecommerce.controller;


import com.scb.demo.ecommerce.entities.Product;
import com.scb.demo.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    final ProductRepository productRepository;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/v1/add/product")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {

       Product productName =  productRepository.findByProductName(product.getProductName());
       if(productName != null) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Already Exist with same name");
       }
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.created(URI.create("http://localhost:8080/v1/product/" + savedProduct.getId())).build();
    }

    @GetMapping("/v1/product/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/v1/product")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }
}

