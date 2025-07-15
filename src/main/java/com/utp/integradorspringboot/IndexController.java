package com.utp.integradorspringboot; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam; 

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index") // Para la URL /index explícita
    public String showIndexPage() {
        return "index"; // Devuelve el nombre de tu archivo HTML (index.html)
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/verify-email")
    public String verifyEmailPage(@RequestParam(name = "email", required = false) String email, Model model) {
        model.addAttribute("email", email);
        return "verify_email";
    }
}