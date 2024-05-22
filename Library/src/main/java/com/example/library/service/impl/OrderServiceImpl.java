package com.example.library.service.impl;

import com.example.library.model.*;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.OrderDetailRepository;
import com.example.library.repository.OrderRepository;
import com.example.library.service.OrderService;
import com.example.library.service.ShoppingCartSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ShoppingCartSevice shoppingCartSevice;
   @Autowired
    private OrderRepository orderRepository;
   @Autowired
   private CustomerRepository customerRepository;
    @Override
    @Transactional
    public Order save(ShoppingCart shoppingCart,Customer customer) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setCustomer(shoppingCart.getCustomer());
        order.setOrderPrice(shoppingCart.getCartTotalPrice());
        order.setAccept(false);
        order.setPaymentMethod("Cash");
        order.setOrderStatus("Pending");
        order.setOrderQuantity(shoppingCart.getCartTotalItems());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem item : shoppingCart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(item.getProduct());
            orderDetail.setOrderdetailQuantity(item.getCartItemQuantity());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
        }
        order.setOrderDetailList(orderDetailList);
        order.getCustomer().getOrders().add(order);
        shoppingCartSevice.deleteCartById(shoppingCart.getCartId());
        return orderRepository.save(order);
    }

    @Override
    public List<OrderDetail> findOrderDetailByOrderId(Long id) {
        return orderDetailRepository.getOrders(id);
    }

    @Override
    public List<Order> findAll(String username) {
        return null;
    }

    @Override
    public List<Order> findALlOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order acceptOrder(Long id) {
        Order order = orderRepository.getReferenceById(id);
        Date orderDate = order.getOrderDate();
        LocalDate localOrderDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate orderDeliveryDate = localOrderDate.plusDays(3);
        Date deliveryDate = Date.from(orderDeliveryDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        order.setAccept(true);
        order.setOrderDeliveryDate(deliveryDate);
        order.setOrderStatus("Shipping");

        for(OrderDetail orderDetail : order.getOrderDetailList())
        {
            if(orderDetail.getProduct().getProductQuantity()==0) {
                {
                    orderDetail.getProduct().setProductIsActivated(false);
                    orderDetail.getProduct().setProductIsDeleted(true);
                }
            }
        }
        return orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.getReferenceById(id);
        for(OrderDetail orderDetail : order.getOrderDetailList())
        {
            if(orderDetail.getProduct().getProductQuantity()==0) {
                {
                    orderDetail.getProduct().setProductIsActivated(true);
                    orderDetail.getProduct().setProductIsDeleted(false);
                }
            }
        }
        for(OrderDetail orderDetail : order.getOrderDetailList())
        {
            orderDetailRepository.DeleteByOrderDetailID(orderDetail.getOderdetailId());
            orderDetail.getProduct().setProductQuantity(orderDetail.getProduct().getProductQuantity()+orderDetail.getOrderdetailQuantity());
        }

        orderRepository.DeleteByOrderID(id);
        Customer customer = order.getCustomer();
        List<Order> orders = customer.getOrders();
        orders.remove(order);
    }


}
