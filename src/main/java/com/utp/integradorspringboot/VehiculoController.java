package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class VehiculoController {

    @GetMapping("/vehiculos")
    public String page() {
        return "vehiculos";
    }
}