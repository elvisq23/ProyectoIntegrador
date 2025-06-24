package com.utp.integradorspringboot.api; 

import com.utp.integradorspringboot.models.Rol;       
import com.utp.integradorspringboot.services.RolService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> listarTodosLosRoles() {
        List<Rol> roles = rolService.getAllRoles(); 
        return ResponseEntity.ok(roles);
    }
}