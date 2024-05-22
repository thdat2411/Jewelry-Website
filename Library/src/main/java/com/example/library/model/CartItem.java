package com.example.library.model;

import javax.persistence.*;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cartId")
    private ShoppingCart shoppingCart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product;
    private int cartItemQuantity;
    private double cartItemUnitPrice;
    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + cartItemId +
                ", cart=" + shoppingCart.getCartId() +
                ", product=" + product.getProductName() +
                ", quantity=" + cartItemQuantity +
                ", unitPrice=" + cartItemUnitPrice +
                ", totalPrice=" +
                '}';
    }
}
