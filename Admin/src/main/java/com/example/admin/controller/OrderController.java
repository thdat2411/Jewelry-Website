package com.example.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.library.model.Order;
import com.example.library.service.OrderService;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String getAll(Model model) {
        List<Order> orderList = orderService.findALlOrders();
        model.addAttribute("orders", orderList);
        return "orders";

    }

    @RequestMapping(value = "/accept-order", method = { RequestMethod.PUT, RequestMethod.GET })
    public String acceptOrder(Long id, RedirectAttributes attributes) {
        orderService.acceptOrder(id);
        attributes.addFlashAttribute("success", "Order Accepted");
        return "redirect:/orders";
    }

    @RequestMapping(value = "/cancel-order", method = { RequestMethod.PUT, RequestMethod.GET })
    public String cancelOrder(Long id) {
        orderService.cancelOrder(id);
        return "redirect:/orders";
    }
}
