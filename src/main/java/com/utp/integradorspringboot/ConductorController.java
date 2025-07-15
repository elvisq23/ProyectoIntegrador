// src/main/java/com/utp/integradorspringboot/controllers/ConductorController.java
// (Asegúrate de que el paquete sea el correcto, diferente de 'api')
package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Indispensable para controladores web que devuelven vistas
// No uses @RequestMapping a nivel de clase si quieres que la URL sea /conductores
public class ConductorController {

    @GetMapping("/conductores") // Esta es la ruta web para la vista
    public String viewConductores(Model model) {
        model.addAttribute("pageTitle", "Gestión de Conductores");
        // Asegúrate de que tienes un archivo Thymeleaf llamado 'conductores.html'
        // en src/main/resources/templates/
        return "conductores"; // Esto devuelve el nombre de la plantilla HTML
    }
}