package com.example.library.service;

import com.example.library.model.*;

import java.util.List;

public interface OrderService {
    Order save(ShoppingCart shoppingCart,Customer customer);

    List<OrderDetail> findOrderDetailByOrderId(Long id);
    List<Order> findAll(String username);

    List<Order> findALlOrders();

    Order acceptOrder(Long id);

    void cancelOrder(Long id);

}
