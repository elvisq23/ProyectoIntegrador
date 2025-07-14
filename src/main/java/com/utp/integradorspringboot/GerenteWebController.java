package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gerente") // prefijo común si deseas agrupar
public class GerenteWebController {

    @GetMapping("/gerente_asignacion")
    public String AsignacionesDelGerentePage() {
        return "gerente_asignacion";
    }
    
    @GetMapping("/gerente_estado")
    public String EstadosGerentePage() {
        return "gerente_estado";
    }
    
    @GetMapping("/gerente_usuarios")
    public String GestionDeUsuariosPage() {
        return "gerente_usuarios";
    }
  
    // Aquí puedes seguir agregando más vistas del rol gerente
}
