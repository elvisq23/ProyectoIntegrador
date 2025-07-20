package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conductor")
public class ConductorWebController {

    @GetMapping("/vehiculo")
    public String VehiculosDelConductorPage() {
        return "conductor_vehiculo";
    }

    @GetMapping("/reserva")
    public String ReservasDelConductorPage() {
        return "conductor_reserva";
    }

    @GetMapping("/estado_reparaciones")
    public String EstadoDeReparacionesDelConductorPage() {
        return "conductor_estado_reparaciones";
    }

}
