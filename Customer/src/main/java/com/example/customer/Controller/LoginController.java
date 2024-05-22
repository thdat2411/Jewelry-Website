package com.example.customer.Controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Customer;
import com.example.library.service.CustomerService;

@Controller
public class LoginController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    private String Login() {
        return "login";
    }

    @PostMapping("/do-login")
    private String doLogin(@RequestParam String username, @RequestParam String password, Model model,
            HttpSession session) {
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {
            model.addAttribute("error", "Invalid Username");
            return "login";
        } else if (!Objects.equals(password, customer.getCustomerPassword())) {
            model.addAttribute("error", "Wrong password");
            return "login";
        } else {
            session.setAttribute("customer", customer);
            return "redirect:/home";
        }
    }

    @GetMapping("/register")
    private String Register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("page", "Register");
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String registerCustomer(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
            BindingResult result,
            Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            String username = customerDto.getCustomerUserName();
            Customer customer = customerService.findByUsername(username);
            if (customer != null) {
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("error", "Email has been register!");
                return "register";
            } else {
                customerService.save(customerDto);
                model.addAttribute("success", "Register successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Server is error, try again later!");
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    private String logOut(HttpSession session) {
        session.removeAttribute("customer");
        return "redirect:/login";
    }
}
