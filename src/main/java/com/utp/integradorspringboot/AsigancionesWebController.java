
package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AsigancionesWebController {

    @RequestMapping("/asignaciones")
    public String page() {
        return "asignaciones"; 
    }
}
