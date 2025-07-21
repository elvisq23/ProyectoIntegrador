package com.utp.integradorspringboot.controller;

import com.utp.integradorspringboot.dto.RegistroUsuarioDTO;
import com.utp.integradorspringboot.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LandingController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/")
    public String landingPage() {
        return "auth/landing"; // templates/auth/landing.html
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registro")
    public String registroPage(Model model) {
        model.addAttribute("usuario", new RegistroUsuarioDTO());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registrar(@ModelAttribute("usuario") RegistroUsuarioDTO dto) {
        usuarioService.registrarConductor(dto);
        return "redirect:/login?registroExitoso";
    }
}
