package com.HomEasy.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotBlank
    @Size(min = 3, message = "Product name must contain at least 3 characters")
    private String productName;

    private String image;

    @NotBlank
    @Size(min = 5, message = "Product description must contain at least 5 characters")
    private String description;

    private Integer quantity;

    @NotNull
    private double price;

    private double dicount;

    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;


}
