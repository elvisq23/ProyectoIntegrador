package com.utp.integradorspringboot;

import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.services.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/conductores") 
public class ConductorController {

    @Autowired
    private ConductorService conductorService;

    @GetMapping
    public String listConductores(Model model) {
        List<Usuario> conductores = conductorService.getAllConductores(null);
        model.addAttribute("conductores", conductores); 
        return "conductores"; 
    }
   
}