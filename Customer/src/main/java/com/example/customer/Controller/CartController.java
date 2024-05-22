package com.example.customer.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.dto.ProductDto;
import com.example.library.model.Customer;
import com.example.library.model.ShoppingCart;
import com.example.library.service.ProductService;
import com.example.library.service.ShoppingCartSevice;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartSevice shoppingCartSevice;

    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        } else {
            ShoppingCart cart = customer.getCart();
            if (cart == null || cart.getCartTotalItems() == 0) {
                model.addAttribute("check", 1);
            } else {
                model.addAttribute("grandTotal", cart.getCartTotalPrice());
            }
            model.addAttribute("shoppingCart", cart);
            model.addAttribute("title", "Cart");
            model.addAttribute("page", "Cart");
            return "cart";
        }
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long id,
            @RequestParam("quantity") int quantity,
            HttpServletRequest request,
            Model model,
            HttpSession session) {
        ProductDto productDto = productService.getById(id);
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        String username = customer.getCustomerUserName();
        ShoppingCart shoppingCart = shoppingCartSevice.addItemToCart(productDto, quantity, username);
        session.setAttribute("totalItems", shoppingCart.getCartTotalItems());
        session.setAttribute("customer", shoppingCart.getCustomer());
        model.addAttribute("shoppingCart", shoppingCart);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Long id,
            @RequestParam("quantity") int quantity,
            Model model,
            HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        ProductDto productDto = productService.getById(id);
        String username = customer.getCustomerUserName();
        ShoppingCart shoppingCart = shoppingCartSevice.updateCart(productDto, quantity, username);
        model.addAttribute("shoppingCart", shoppingCart);
        session.setAttribute("totalItems", shoppingCart.getCartTotalItems());
        session.removeAttribute("customer");
        session.setAttribute("customer", shoppingCart.getCustomer());
        return "redirect:/cart";

    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") Long id,
            Model model,
            HttpSession session,
            HttpServletRequest request) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        } else {
            ProductDto productDto = productService.getById(id);
            String username = customer.getCustomerUserName();
            ShoppingCart shoppingCart = shoppingCartSevice.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", shoppingCart);
            session.setAttribute("totalItems", shoppingCart.getCartTotalItems());
            session.removeAttribute("customer");
            session.setAttribute("customer", shoppingCart.getCustomer());
            return "redirect:/cart";
        }
    }
}
