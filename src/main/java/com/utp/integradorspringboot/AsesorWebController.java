package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/asesor") // prefijo común si deseas agrupar
public class AsesorWebController {

    @GetMapping("/asesor_registrar_llegada")
    public String RegistroLLegadaPage() {
        return "asesor_registrar_llegada";
    }

    @GetMapping("/asesor_reservas_del_dia")
    public String ReservasPage() {
        return "asesor_reservas_del_dia";
    }
    
    @GetMapping("/asesor_vehiculos")
    public String vehiculosPage() {
        return "asesor_vehiculos";
    }
    @GetMapping("/asesor_conductores")
    public String ConductoresPage() {
        return "asesor_conductores";
    }

    // Aquí puedes seguir agregando más vistas del rol asesor
}
