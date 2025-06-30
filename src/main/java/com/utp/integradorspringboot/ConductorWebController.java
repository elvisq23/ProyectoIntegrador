package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conductor") // prefijo común si deseas agrupar
public class ConductorWebController {

    @GetMapping("/conductor_vehiculo")
    public String VehiculosDelConductorPage() {
        return "conductor_vehiculo";
    }

    @GetMapping("/conductor_reserva")
    public String ReservasDelConductorPage() {
        return "conductor_reserva";
    }

    // Aquí puedes seguir agregando más vistas del rol conductor
}
