package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dueno")
public class DuenoWebController {

    @GetMapping("/dashboard")
    public String dashboardDuenoPage() {
        return "dueno_dashboard";
    }

}
