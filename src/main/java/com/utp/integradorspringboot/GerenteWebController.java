package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gerente") // prefijo común si deseas agrupar
public class GerenteWebController {

    @GetMapping("/asignacion")
    public String AsignacionesDelGerentePage() {
        return "gerente_asignacion";
    }
    
    @GetMapping("/estado")
    public String EstadosGerentePage() {
        return "gerente_estado";
    }

    @GetMapping("/colaboradores")
    public String colaboradoresPage() {
        return "gerente_colaboradores";
    }

    @GetMapping("/conductores")
    public String GestionDeConductoresPage() {
        return "gerente_conductores";
    }

    @GetMapping("/sedes")
    public String Sedespage() {
        return "gerente_sedes";
    }

    @GetMapping("/proveedores")
    public String Proveedorespage() {
        return "gerente_proveedores";
    }
}
