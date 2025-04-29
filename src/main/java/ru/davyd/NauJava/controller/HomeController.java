package ru.davyd.NauJava.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHomePage(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "home";
    }
}