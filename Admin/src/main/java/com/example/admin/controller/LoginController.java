package com.example.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/do-login")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        if (("admin".equals(username)) && ("1111".equals(password))) {
            session.setAttribute("admin", "Valid");
            return "redirect:/home";
        } else {
            return "login";
        }
    }

    @GetMapping(value = { "/home", "/" })
    public String home(HttpSession session) {
        String valid = (String) session.getAttribute("admin");
        if (valid == null) {
            return "login";
        } else {
            return "index";
        }
    }
}
