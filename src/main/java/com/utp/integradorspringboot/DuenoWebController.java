package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dueno") // prefijo común si deseas agrupar
public class DuenoWebController {

    @GetMapping("/dueno_dashboard")
    public String dashboardDuenoPage() {
        return "dueno_dashboard";
    }

    @GetMapping("/colaboradores")
    public String colaboradoresPage() {
        return "colaboradores";
    }
    
    @GetMapping("/dueno_sedes") 
    public String DuenoSedespage() {
        return "dueno_sedes";
    }

    // Aquí puedes seguir agregando más vistas del rol dueño
}
