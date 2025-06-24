package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping; // ¡Importación importante!

@Controller
public class SedesController {

    @GetMapping("/sedes") // Usamos @GetMapping para indicar que solo maneja peticiones GET
    public String page() {
        return "sedes";
    }
}