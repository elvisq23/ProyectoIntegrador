package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mecanico") // prefijo común si deseas agrupar
public class MecanicoWebController {

    @GetMapping("/mecanico_diagnostico")
    public String diagnosticoPage() {
        return "mecanico_diagnostico";
    }

    @GetMapping("/mecanico_asignaciones")
    public String asignacionesPage() {
        return "mecanico_asignaciones";
    }

}
