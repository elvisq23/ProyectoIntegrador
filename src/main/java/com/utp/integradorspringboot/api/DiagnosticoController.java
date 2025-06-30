/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Diagnostico;
import com.utp.integradorspringboot.repositories.DiagnosticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class DiagnosticoController {

    @Autowired
    private DiagnosticoRepository repository;

    @GetMapping("/diagnosticos")
    public ResponseEntity<List<Diagnostico>> getAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/diagnosticos/{id}")
    public ResponseEntity<Diagnostico> getById(@PathVariable Integer id) {
        Optional<Diagnostico> data = repository.findById(id);
        return data.map(d -> new ResponseEntity<>(d, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/diagnosticos")
public ResponseEntity<Diagnostico> create(@RequestBody Diagnostico diagnostico) {
    try {
        // Establece la relación bidireccional
        if (diagnostico.getOrdenServicio() != null) {
            diagnostico.getOrdenServicio().setDiagnostico(diagnostico);
        }

        Diagnostico nuevo = repository.save(diagnostico);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}
