package com.utp.integradorspringboot; // Como me indicaste, está en el paquete principal

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model; // ¡Importante! Necesitas importar Model
import org.springframework.web.bind.annotation.RequestParam; // ¡Importante! Necesitas importar RequestParam

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
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
        // Añadir el email del parámetro de la URL al modelo de Thymeleaf
        model.addAttribute("email", email);
        return "verify_email";
    }
}