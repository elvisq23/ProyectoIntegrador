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

    @GetMapping("/index")
    public String showIndexPage() {
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
        model.addAttribute("email", email);
        return "verify_email";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot_password"; // Este nombre debe coincidir con tu archivo HTML (e.g., forgot_password.html)
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam(name = "token", required = false) String token, Model model) {
        model.addAttribute("token", token); // Pasa el token a la vista para que el formulario POST lo envíe
        return "reset_password"; // Este nombre debe coincidir con tu archivo HTML (e.g., reset_password.html)
    }
}