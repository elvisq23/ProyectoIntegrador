package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/encargado")
public class EncargadoWebController {

    @GetMapping("/registrar_llegada")
    public String RegistroLLegadaPage() {
        return "encargado_registrar_llegada";
    }

    @GetMapping("/reservas_del_dia")
    public String ReservasPage() {
        return "encargado_reservas_del_dia";
    }
    
    @GetMapping("/conductores")
    public String ConductoresPage() {
        return "encargado_conductores";
    }

}
