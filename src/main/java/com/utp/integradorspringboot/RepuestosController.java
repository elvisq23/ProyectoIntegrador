package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/repuestos")
public class RepuestosController {

    @GetMapping
    public String viewRepuestos() {
        return "repuestos";
    }
}