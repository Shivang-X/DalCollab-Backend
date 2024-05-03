package com.HomEasy.controllers;

import com.HomEasy.payloads.ProductDTO;
import com.HomEasy.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(products);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO product){

        ProductDTO savedProduct = productService.addProduct(product);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(savedProduct);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProductByCategory(@PathVariable Long productId){
        return null;
    }
}
