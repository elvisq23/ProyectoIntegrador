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
    
    @GetMapping("/asesor_vehiculo")
    public String VehiculoPage() {
        return "asesor_vehiculo";
    }
}
