package com.mr.deanshop.controller;

import com.mr.deanshop.dto.ProductDto;
import com.mr.deanshop.entity.Product;
import com.mr.deanshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) UUID categoryId, @RequestParam(required = false) UUID categoryTypeId) {
        List<Product> products = productService.getAllProducts(categoryId, categoryTypeId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product product = productService.addProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

}
