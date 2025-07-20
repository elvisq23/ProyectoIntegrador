package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recepcion")
public class RecepcionWebController {

    @GetMapping("/registrar_llegada")
    public String RegistroLLegadaPage() {
        return "recepcion_registrar_llegada";
    }

    @GetMapping("/reservas_del_dia")
    public String ReservasPage() {
        return "recepcion_reservas_del_dia";
    }
    
    @GetMapping("/conductores")
    public String ConductoresPage() {
        return "recepcion_conductores";
    }

}
