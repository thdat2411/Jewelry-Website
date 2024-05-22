package com.example.customer.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.library.model.Customer;
import com.example.library.model.Order;
import com.example.library.model.OrderDetail;
import com.example.library.repository.OrderRepository;
import com.example.library.service.OrderService;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/order")
    private String getOrder(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        List<Order> orders = customer.getOrders();
        if (customer == null) {
            return "redirect:/login";
        } else {
            if (orders == null) {
                model.addAttribute("check");
            } else {
                model.addAttribute("valid", 1);
                model.addAttribute("orders", orders);
                model.addAttribute("tittle", "Order");
                model.addAttribute("page", "Order");
            }
            return "order";
        }
    }

    @RequestMapping(value = "/cancel-order", method = { RequestMethod.PUT, RequestMethod.GET })
    public String cancelOrder(Long id, RedirectAttributes attributes, HttpSession session) {
        Order order = orderRepository.getReferenceById(id);
        orderService.cancelOrder(id);
        session.setAttribute("customer", order.getCustomer());
        attributes.addFlashAttribute("success", "Cancel order successfully!");
        return "redirect:/order";
    }

    @GetMapping(value = "/order-view/{id}")
    public String viewOrder(@PathVariable("id") Long id, Model model) {
        Order order = orderRepository.getReferenceById(id);
        List<OrderDetail> orderDetailList = orderService.findOrderDetailByOrderId(id);
        model.addAttribute("title", "View Order");
        model.addAttribute("page", "View Order");
        model.addAttribute("orderDetailList", orderDetailList);
        model.addAttribute("order", order);
        return "view-order";
    }
}
