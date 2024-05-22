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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Customer;
import com.example.library.service.CustomerService;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer-profile")
    public String profile(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Profile");
        model.addAttribute("page", "Profile");
        return "customer-information";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@Valid @ModelAttribute("customer") CustomerDto customerDto,
            BindingResult result,
            RedirectAttributes attributes,
            Model model,
            HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (result.hasErrors()) {
            return "customer-information";
        }
        customerService.update(customerDto);
        CustomerDto customerUpdate = customerService.getCustomer(customer.getCustomerUserName());
        attributes.addFlashAttribute("success", "Update successfully!");
        session.setAttribute("customer", customerService.findByUsername(customerUpdate.getCustomerUserName()));
        model.addAttribute("customer", customerUpdate);
        return "redirect:/customer-profile";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model) {
        model.addAttribute("title", "Change password");
        model.addAttribute("page", "Change password");
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePass(@RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("repeatedNewPassword") String repeatPassword,
            RedirectAttributes attributes,
            Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        CustomerDto customerDto = customerService.getCustomer(customer.getCustomerUserName());
        if (Objects.equals(oldPassword, customerDto.getCustomerPassword())
                && !Objects.equals(newPassword, customerDto.getCustomerPassword())
                && !Objects.equals(newPassword, oldPassword)
                && repeatPassword.equals(newPassword)) {
            customerDto.setCustomerPassword(newPassword);
            customerService.changePass(customerDto);
            attributes.addFlashAttribute("success", "Your password has been changed successfully!");
            return "redirect:/customer-profile";
        } else {
            model.addAttribute("message", "Your password is wrong");
            return "change-password";
        }
    }
}
