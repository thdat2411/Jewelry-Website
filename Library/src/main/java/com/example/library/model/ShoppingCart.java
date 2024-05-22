package com.example.library.model;

import javax.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private Customer customer;

    private double cartTotalPrice;

    private int cartTotalItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCart", fetch = FetchType.EAGER)
    private Set<CartItem> cartItems;

    public ShoppingCart() {
        this.cartItems = new HashSet<>();
        this.cartTotalItems = 0;
        this.cartTotalPrice = 0.0;
    }
    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + cartId +
                ", customer=" + customer.getCustomerUserName() +
                ", totalPrice=" + cartTotalPrice +
                ", totalItems=" + cartTotalItems +
                ", cartItems=" + cartItems.size() +
                '}';
    }
}
