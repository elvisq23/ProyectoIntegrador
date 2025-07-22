package com.utp.integradorspringboot;

import com.utp.integradorspringboot.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dueno")// prefijo común si deseas agrupar
public class DuenoWebController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/estado")
    public String EstadosPage() {
        return "dueno_estado";
    }

    @GetMapping("/proveedores")
    public String Proveedorespage() {
        return "dueno_proveedores";
    }

    @GetMapping("/servicios")
    public String Serviciospage() {
        return "dueno_servicios";
    }

    @GetMapping("/inventario")
    public String InventarioPage() {
        return "dueno_inventario";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalColaboradores", dashboardService.contarColaboradores());
        model.addAttribute("totalConductores", dashboardService.contarConductores());
        model.addAttribute("totalSedes", dashboardService.contarSedes());
        model.addAttribute("totalServicios", dashboardService.contarServicios());
        model.addAttribute("totalProveedores", dashboardService.contarProveedores());
        return "dueno_dashboard";
    }
}
