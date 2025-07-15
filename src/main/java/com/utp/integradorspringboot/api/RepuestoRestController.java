package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.RepuestoRequestDTO;
import com.utp.integradorspringboot.models.Repuesto;
import com.utp.integradorspringboot.services.RepuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoRestController {

    @Autowired
    private RepuestoService repuestoService;

    @GetMapping
    public ResponseEntity<List<Repuesto>> getAllRepuestos(@RequestParam(required = false) String search) {
        List<Repuesto> repuestos = repuestoService.getAllRepuestos(search);
        return ResponseEntity.ok(repuestos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> getRepuestoById(@PathVariable Long id) {
        return repuestoService.getRepuestoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createRepuesto(@Valid @RequestBody RepuestoRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            Repuesto newRepuesto = repuestoService.saveRepuesto(request);
            return new ResponseEntity<>(newRepuesto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRepuesto(@PathVariable Long id, @Valid @RequestBody RepuestoRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            if (request.getId() != null && !request.getId().equals(id)) {
                return ResponseEntity.badRequest().body("El ID del repuesto en la URL no coincide con el ID en el cuerpo de la solicitud.");
            }
            request.setId(id); // Asegura que el ID del DTO sea el del path

            Repuesto updatedRepuesto = repuestoService.updateRepuesto(id, request);
            return ResponseEntity.ok(updatedRepuesto);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepuesto(@PathVariable Long id) {
        try {
            repuestoService.deleteRepuesto(id);
            return ResponseEntity.noContent().build(); // 204 No Content para eliminación exitosa
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no lo encuentra, devuelve 404
        }
    }
}