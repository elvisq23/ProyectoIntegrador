package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mecanico")
public class MecanicoWebController {

    @GetMapping("/diagnostico")
    public String diagnosticoPage() {
        return "mecanico_diagnostico";
    }

    @GetMapping("/asignaciones")
    public String asignacionesPage() {
        return "mecanico_asignaciones";
    }

}
