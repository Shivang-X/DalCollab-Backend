package com.HomEasy.services;

import com.HomEasy.entities.Product;
import com.HomEasy.payloads.ProductDTO;
import com.HomEasy.repositories.ProductRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {

        System.out.println(productDTO);

        Product product = modelMapper.map(productDTO, Product.class);

        System.out.println(product);

        double specialPrice = product.getPrice() - ((product.getDicount()*0.01)*product.getPrice());
        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepo.save(product);

        return productDTO;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepo.findAll();

        List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());

        return productDTOs;
    }
}
