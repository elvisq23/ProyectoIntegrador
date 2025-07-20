package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/almacen")
public class AlmacenWebController {

    @GetMapping("/entrada_repuestos")
    public String EntradaRepuestosPage() {
        return "almacen_entrada_repuestos";
    }

    @GetMapping("/almacen_stock")
    public String StockPage() {
        return "almacen_stock";
    }

    // Aquí puedes seguir agregando más vistas del rol almacen
}
