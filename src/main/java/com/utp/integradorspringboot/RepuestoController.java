package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RepuestoController {

    @GetMapping("/repuestos")
    public String page() {
        return "repuestos";
    }
}