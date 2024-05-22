package com.example.customer.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.library.model.Customer;
import com.example.library.model.Order;
import com.example.library.model.ShoppingCart;
import com.example.library.service.CustomerService;
import com.example.library.service.OrderService;

@Controller
public class CheckoutController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/check-out")
    public String checkOut(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer.getCustomerAddress() == null || customer.getCustomerPhoneNumber() == null) {
            model.addAttribute("information", "You need update your information before check out");
            model.addAttribute("customer", customer);
            model.addAttribute("title", "Profile");
            model.addAttribute("page", "Profile");
            return "redirect:/customer-profile";
        } else {
            ShoppingCart cart = customer.getCart();
            model.addAttribute("customer", customer);
            model.addAttribute("title", "Check-Out");
            model.addAttribute("page", "Check-Out");
            model.addAttribute("shoppingCart", cart);
            model.addAttribute("grandTotal", cart.getCartItems());
            return "check-out";
        }
    }

    @RequestMapping(value = "/add-order", method = { RequestMethod.POST })
    public String createOrder(Model model,
            HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        ShoppingCart cart = customer.getCart();
        Order order = orderService.save(cart, customer);
        session.removeAttribute("totalItems");
        session.setAttribute("customer", order.getCustomer());
        model.addAttribute("order", order);
        model.addAttribute("title", "Order Detail");
        model.addAttribute("page", "Order Detail");
        model.addAttribute("success", "Add order successfully");
        return "redirect:/order";
    }
}
