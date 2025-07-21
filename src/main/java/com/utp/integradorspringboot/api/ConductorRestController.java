package com.utp.integradorspringboot.api;
/*

import com.utp.integradorspringboot.dto.ConductorRequest;
import com.utp.integradorspringboot.dto.ConductorResponseDTO;
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

    // --- MODIFICADO: Ahora devuelve List<ConductorResponseDTO> ---
    @GetMapping
    public ResponseEntity<List<ConductorResponseDTO>> getAllConductores(@RequestParam(required = false) String search) {
        List<Usuario> conductores = conductorService.getAllConductores(search);

        List<ConductorResponseDTO> conductorDTOs = conductores.stream()
                .map(ConductorResponseDTO::new) // Convierte cada Usuario a ConductorResponseDTO
                .collect(Collectors.toList());

        return ResponseEntity.ok(conductorDTOs);
    }

    // --- MODIFICADO: Ahora devuelve ConductorResponseDTO para el GET por ID ---
    @GetMapping("/{id}")
    public ResponseEntity<ConductorResponseDTO> getConductorById(@PathVariable Long id) {
        return conductorService.getConductorById(id)
                .map(usuario -> new ConductorResponseDTO(usuario)) // Convierte Usuario a DTO
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createConductor(@Valid @RequestBody ConductorRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            Usuario newConductor = conductorService.saveConductor(request);
            // Si también quieres devolver un DTO en la creación, hazlo aquí:
            // return new ResponseEntity<>(new ConductorResponseDTO(newConductor), HttpStatus.CREATED);
            newConductor.setContrasenia(null); // Esto es redundante si devuelves un DTO, pero no daña.
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
            // Si también quieres devolver un DTO en la actualización:
            // return ResponseEntity.ok(new ConductorResponseDTO(updatedConductor));
            updatedConductor.setContrasenia(null); // Esto es redundante si devuelves un DTO, pero no daña.
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
}*/
