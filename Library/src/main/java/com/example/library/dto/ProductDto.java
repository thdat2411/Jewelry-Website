package com.example.library.dto;

import com.example.library.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String productName;
    private String productDesc;
    private int productQuantity;
    private double productPrice;
    private String productImage;
    private Category category;
    private String productType;
    private boolean productActivated;
    private boolean productDeleted;
    private String currentPage;
}
