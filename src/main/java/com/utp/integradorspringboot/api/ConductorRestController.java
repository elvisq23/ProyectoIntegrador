package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.ConductorRequest;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.services.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conductores")
public class ConductorRestController {

    @Autowired
    private ConductorService conductorService;

     @GetMapping
    public ResponseEntity<List<Usuario>> getAllConductores(@RequestParam(required = false) String search) {
        List<Usuario> conductores = conductorService.getAllConductores(search);
        conductores.forEach(u -> u.setContrasenia(null));
        return ResponseEntity.ok(conductores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getConductorById(@PathVariable Long id) {
        return conductorService.getConductorById(id)
                .map(conductor -> {
                    conductor.setContrasenia(null); 
                    return ResponseEntity.ok(conductor);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createConductor(@Valid @RequestBody ConductorRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            Usuario newConductor = conductorService.saveConductor(request);
            newConductor.setContrasenia(null); 
            return new ResponseEntity<>(newConductor, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateConductor(@PathVariable Long id, @Valid @RequestBody ConductorRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            Usuario updatedConductor = conductorService.updateConductor(id, request);
            updatedConductor.setContrasenia(null); 
            return ResponseEntity.ok(updatedConductor);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateConductor(@PathVariable Long id) {
        try {
            conductorService.deactivateConductor(id); 
            return ResponseEntity.noContent().build(); 
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }
}