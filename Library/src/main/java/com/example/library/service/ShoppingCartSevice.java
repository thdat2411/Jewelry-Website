package com.example.library.service;

import com.example.library.dto.CustomerDto;
import com.example.library.dto.ProductDto;
import com.example.library.model.ShoppingCart;

import javax.transaction.Transactional;

public interface ShoppingCartSevice {
    ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username);

    ShoppingCart updateCart(ProductDto productDto, int quantity, String username);

    ShoppingCart removeItemFromCart(ProductDto productDto, String username);

    void deleteCartById(Long id);
}
