package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long cartItemId;

    private ShoppingCartDto cart;

    private ProductDto product;

    private int cartItemQuantity;

    private double cartItemUnitPrice;
}
