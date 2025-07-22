package com.utp.integradorspringboot;

import com.utp.integradorspringboot.repositories.ProveedorRepository;
import com.utp.integradorspringboot.repositories.SedeRepository;
import com.utp.integradorspringboot.repositories.ServicioRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import com.utp.integradorspringboot.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gerente") // prefijo común si deseas agrupar
public class GerenteWebController {

    @Autowired
    private DashboardService dashboardService;

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

    @GetMapping("/servicios")
    public String Serviciospage() {
        return "gerente_servicios";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalColaboradores", dashboardService.contarColaboradores());
        model.addAttribute("totalConductores", dashboardService.contarConductores());
        model.addAttribute("totalSedes", dashboardService.contarSedes());
        model.addAttribute("totalServicios", dashboardService.contarServicios());
        model.addAttribute("totalProveedores", dashboardService.contarProveedores());
        return "gerente_dashboard";
    }
}
