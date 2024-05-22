package com.example.library.dto;

import com.example.library.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {
    private Long cartId;

    private Customer customer;

    private double cartTotalPrice;

    private int cartTotalItems;

    private Set<CartItemDto> cartItems;
}
