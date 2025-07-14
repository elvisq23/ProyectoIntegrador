package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping; 

@Controller
public class SedesController {

    @GetMapping("/sedes") 
    public String page() {
        return "sedes";
    }
}