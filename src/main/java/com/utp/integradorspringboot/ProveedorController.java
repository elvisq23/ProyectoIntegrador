package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProveedorController {

    @GetMapping("/proveedores")
    public String page() {
        return "proveedores"; // Archivo proveedores.html en /templates
    }
}