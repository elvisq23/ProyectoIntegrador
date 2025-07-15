package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cajero") // prefijo común si deseas agrupar
public class CajeroWebController {

    @GetMapping("/cajero_emitir_pagos")
    public String EmitirPagosPage() {
        return "cajero_emitir_pagos";
    }

    @GetMapping("/cajero_pagos_historial")
    public String PagosHistorialPage() {
        return "cajero_pagos_historial";
    }

}
