package com.utp.integradorspringboot.api;
/*

import com.utp.integradorspringboot.dto.ColaboradorRequestDTO;
import com.utp.integradorspringboot.dto.ColaboradorResponseDTO;
import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.models.Sede;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.services.ColaboradorService;
import com.utp.integradorspringboot.services.RolService;
import com.utp.integradorspringboot.services.SedeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/colaboradores")
public class ColaboradorRestController {

    @Autowired
    private ColaboradorService colaboradorService;
    @Autowired
    private SedeService sedeService;
    @Autowired
    private RolService rolService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @GetMapping
    public ResponseEntity<List<ColaboradorResponseDTO>> getAllColaboradores(@RequestParam(required = false) String search) {
        List<Usuario> colaboradores = colaboradorService.getAllColaboradores(search);
        List<ColaboradorResponseDTO> dtos = colaboradores.stream()
                .map(ColaboradorResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ColaboradorResponseDTO> getColaboradorById(@PathVariable Long id) {
        return colaboradorService.getColaboradorById(id)
                .map(ColaboradorResponseDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<?> createColaborador(@Valid @RequestBody ColaboradorRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            String rawPassword = request.getContrasenia();

            System.out.println("DEBUG: DNI recibido en Controller (POST): " + request.getDni());
            System.out.println("DEBUG: Contraseña original recibida del DTO (POST): " + (rawPassword != null ? rawPassword : "null"));

            if (rawPassword == null || rawPassword.trim().isEmpty()) {
                rawPassword = request.getDni();
                System.out.println("DEBUG: Contraseña ajustada a DNI (fallback - POST): " + rawPassword);
            }

            request.setContrasenia(passwordEncoder.encode(rawPassword));
            System.out.println("DEBUG: Contraseña hasheada (lista para servicio - POST): " + request.getContrasenia());

            Usuario newColaborador = colaboradorService.saveColaborador(request);

            return new ResponseEntity<>(new ColaboradorResponseDTO(newColaborador), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateColaborador(@PathVariable Long id, @Valid @RequestBody ColaboradorRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            if (request.getId() != null && !request.getId().equals(id)) {
                return ResponseEntity.badRequest().body("El ID del colaborador en la URL no coincide con el ID en el cuerpo de la solicitud.");
            }
            request.setId(id);

            if (request.getContrasenia() != null && !request.getContrasenia().trim().isEmpty()) {
                request.setContrasenia(passwordEncoder.encode(request.getContrasenia()));
                System.out.println("DEBUG: Contraseña hasheada para actualización (PUT): " + request.getContrasenia());
            } else {
                request.setContrasenia(null);
            }

            Usuario updatedColaborador = colaboradorService.updateColaborador(id, request);
            return ResponseEntity.ok(new ColaboradorResponseDTO(updatedColaborador));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<String> deactivateColaborador(@PathVariable Long id) {
        try {
            colaboradorService.deactivateColaborador(id);
            return ResponseEntity.ok("Colaborador desactivado exitosamente.");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sedes")
    public ResponseEntity<List<Sede>> getAllSedes() {
        List<Sede> sedes = sedeService.getAllSedes();
        return ResponseEntity.ok(sedes);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> getColaboradorRoles() {
        List<Rol> roles = rolService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}*/
