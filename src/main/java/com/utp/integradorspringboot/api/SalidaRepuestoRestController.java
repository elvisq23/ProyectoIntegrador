package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.MecanicoDTO; // <-- Importamos el nuevo DTO
import com.utp.integradorspringboot.dto.SalidaRepuestoRequestDTO;
import com.utp.integradorspringboot.dto.SalidaRepuestoResponseDTO;
import com.utp.integradorspringboot.models.Repuesto;
import com.utp.integradorspringboot.models.SalidaRepuesto;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import com.utp.integradorspringboot.services.RepuestoService;
import com.utp.integradorspringboot.services.SalidaRepuestoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salidas-repuestos")
public class SalidaRepuestoRestController {

    @Autowired
    private SalidaRepuestoService salidaRepuestoService;

    @Autowired
    private RepuestoService repuestoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<SalidaRepuestoResponseDTO>> getAllSalidas() {
        List<SalidaRepuesto> salidas = salidaRepuestoService.getAllSalidas();
        List<SalidaRepuestoResponseDTO> dtos = salidas.stream()
                .map(SalidaRepuestoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<?> createSalidaRepuesto(@Valid @RequestBody SalidaRepuestoRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            SalidaRepuesto newSalida = salidaRepuestoService.saveSalidaRepuesto(request);
            return new ResponseEntity<>(new SalidaRepuestoResponseDTO(newSalida), HttpStatus.CREATED);
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

    // --- MÉTODO CORREGIDO ---
    @GetMapping("/mecanicos-disponibles")
    public ResponseEntity<List<MecanicoDTO>> getMecanicosDisponibles() {
        // La consulta a la base de datos no cambia
        List<Usuario> mecanicos = usuarioRepository.findByRoles_NombreAndEstadoTrue("CONDUCTOR");

        // Convertimos la lista de Usuario a una lista de MecanicoDTO
        List<MecanicoDTO> dtos = mecanicos.stream()
                .map(MecanicoDTO::new)
                .collect(Collectors.toList());
                
        // Devolvemos la lista de DTOs, que es más segura
        return ResponseEntity.ok(dtos);
    }
}