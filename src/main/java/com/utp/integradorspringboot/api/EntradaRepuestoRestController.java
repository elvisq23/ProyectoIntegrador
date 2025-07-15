package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.EntradaRepuestoRequestDTO;
import com.utp.integradorspringboot.dto.EntradaRepuestoResponseDTO;
import com.utp.integradorspringboot.models.EntradaRepuesto;
import com.utp.integradorspringboot.models.Repuesto;
import com.utp.integradorspringboot.services.EntradaRepuestoService;
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
@RequestMapping("/api/entradas-repuestos")
public class EntradaRepuestoRestController {

    @Autowired
    private EntradaRepuestoService entradaRepuestoService;

    @Autowired
    private RepuestoService repuestoService;

    @GetMapping
    public ResponseEntity<List<EntradaRepuestoResponseDTO>> getAllEntradas() {
        List<EntradaRepuesto> entradas = entradaRepuestoService.getAllEntradas();
        List<EntradaRepuestoResponseDTO> dtos = entradas.stream()
                                                    .map(EntradaRepuestoResponseDTO::new)
                                                    .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaRepuestoResponseDTO> getEntradaRepuestoById(@PathVariable Long id) {
        return entradaRepuestoService.getEntradaRepuestoById(id)
                .map(EntradaRepuestoResponseDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createEntradaRepuesto(@Valid @RequestBody EntradaRepuestoRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            EntradaRepuesto newEntrada = entradaRepuestoService.saveEntradaRepuesto(request);
            return new ResponseEntity<>(new EntradaRepuestoResponseDTO(newEntrada), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/repuestos-disponibles")
    public ResponseEntity<List<Repuesto>> getRepuestosDisponibles() {
        List<Repuesto> repuestos = repuestoService.getAllRepuestos(null);
        return ResponseEntity.ok(repuestos);
    }
}