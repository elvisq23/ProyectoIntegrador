package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.VehiculoRequestDTO;
import com.utp.integradorspringboot.dto.VehiculoResponseDTO;
import com.utp.integradorspringboot.models.Vehiculo;
import com.utp.integradorspringboot.services.VehiculoService;
import com.utp.integradorspringboot.services.ClienteService;
import com.utp.integradorspringboot.models.Cliente;
import com.utp.integradorspringboot.dto.ClienteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoRestController {

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired(required = false)
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> getAllVehiculos(@RequestParam(required = false) String search) {
        List<Vehiculo> vehiculos = vehiculoService.getAllVehiculos(search);
        List<VehiculoResponseDTO> dtos = vehiculos.stream()
                                                .map(VehiculoResponseDTO::new)
                                                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> getVehiculoById(@PathVariable Long id) {
        return vehiculoService.getVehiculoById(id)
                .map(VehiculoResponseDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createVehiculo(@Valid @RequestBody VehiculoRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            Vehiculo newVehiculo = vehiculoService.saveVehiculo(request);
            return new ResponseEntity<>(new VehiculoResponseDTO(newVehiculo), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehiculo(@PathVariable Long id, @Valid @RequestBody VehiculoRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        try {
            if (request.getId() != null && !request.getId().equals(id)) {
                return ResponseEntity.badRequest().body("El ID del vehículo en la URL no coincide con el ID en el cuerpo de la solicitud.");
            }
            request.setId(id); // Asegura que el ID del DTO sea el del path

            Vehiculo updatedVehiculo = vehiculoService.updateVehiculo(id, request);
            return ResponseEntity.ok(new VehiculoResponseDTO(updatedVehiculo));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable Long id) {
        try {
            vehiculoService.deleteVehiculo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/clientes-disponibles")
    public ResponseEntity<List<ClienteResponseDTO>> getClientesDisponibles() {
        if (clienteService == null) {
             return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        List<Cliente> clientes = clienteService.getAllClientes(null);
        List<ClienteResponseDTO> dtos = clientes.stream()
                                                .map(ClienteResponseDTO::new)
                                                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}