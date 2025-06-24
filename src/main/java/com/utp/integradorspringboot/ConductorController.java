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
@RequestMapping("/conductores") // Todas las rutas aquí empezarán con /conductores
public class ConductorController {

    @Autowired
    private ConductorService conductorService;

    @GetMapping
    public String listConductores(Model model) {
        // Inicialmente no se pasa un query de búsqueda, Vue.js lo manejará
        List<Usuario> conductores = conductorService.getAllConductores(null);
        model.addAttribute("conductores", conductores); // Vue.js no usará directamente este modelAttribute
                                                        // pero es buena práctica tenerlo si algo fallara con Vue
        return "conductores"; // Retorna el nombre del archivo HTML sin la extensión y sin subcarpeta
    }

    // Los métodos para "new", "edit", "delete" vía Thymeleaf ya no son necesarios
    // porque Vue.js los manejará a través de la API REST.
}