package com.example.library.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Disposal")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Date orderDate;
    private Date orderDeliveryDate;
    private String orderStatus;
    private double orderPrice;
    private int orderQuantity;
    private String paymentMethod;
    private boolean isAccept;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private Customer customer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order",fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetailList;
}
