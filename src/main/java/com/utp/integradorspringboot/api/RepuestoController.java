package com.utp.integradorspringboot;

import com.utp.integradorspringboot.models.Repuesto;
import com.utp.integradorspringboot.repositories.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {
    @Autowired
    private RepuestoRepository repuestoRepository;

    @GetMapping
    public List<Repuesto> listar() {
        return repuestoRepository.findAll();
    }
} 