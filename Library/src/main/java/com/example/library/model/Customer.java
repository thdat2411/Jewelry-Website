package com.example.library.model;

import javax.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "customerUsername"))
public class Customer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long customerId;
        private String customerFirstName;
        private String customerLastName;
        private String customerUserName;
        private String customerPassword;
        private String customerPhoneNumber;
        private String customerAddress;
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private ShoppingCart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Order> orders;

    public Customer() {
            this.cart = new ShoppingCart();
            this.orders = new ArrayList<>();
        }
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + customerId +
                ", firstName='" + customerFirstName + '\'' +
                ", lastName='" + customerLastName + '\'' +
                ", username='" + customerUserName + '\'' +
                ", password='" + customerPassword+ '\'' +
                ", phoneNumber='" + customerPhoneNumber + '\'' +
                ", address='" +customerAddress + '\'' +
                ", cart=" + cart.getCartId() +
                ", orders=" + orders.size() +
                '}';
    }
}
