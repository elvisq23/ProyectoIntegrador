package com.utp.integradorspringboot; 

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/colaboradores") 
public class ColaboradorController {

    @GetMapping
    public String viewColaboradores(Model model) {
        model.addAttribute("pageTitle", "Gestión de Colaboradores");
        return "colaboradores"; 
    }
}