package com.HomEasy.services;

import com.HomEasy.payloads.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO addProduct(ProductDTO product);

    List<ProductDTO> getAllProducts();
}
