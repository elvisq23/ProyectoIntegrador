package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.VehiculoRequestDTO;
import com.utp.integradorspringboot.dto.VehiculoResponseDTO;
import com.utp.integradorspringboot.models.Vehiculo;
import com.utp.integradorspringboot.services.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoRestController {

    @Autowired
    private VehiculoService vehiculoService;

    /**
     * Gets a list of all vehicles, with an optional search filter.
     */
    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> getAllVehiculos(@RequestParam(required = false) String search) {
        List<Vehiculo> vehiculos = vehiculoService.getAllVehiculos(search);
        List<VehiculoResponseDTO> dtos = vehiculos.stream()
                .map(VehiculoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Gets a single vehicle by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> getVehiculoById(@PathVariable Long id) {
        return vehiculoService.getVehiculoById(id)
                .map(vehiculo -> ResponseEntity.ok(new VehiculoResponseDTO(vehiculo)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new vehicle.
     */
    @PostMapping
    public ResponseEntity<?> createVehiculo(@Valid @RequestBody VehiculoRequestDTO request) {
        try {
            Vehiculo newVehiculo = vehiculoService.saveVehiculo(request);
            return new ResponseEntity<>(new VehiculoResponseDTO(newVehiculo), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Catches errors like "plate already exists" or "client not found"
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates an existing vehicle.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehiculo(@PathVariable Long id, @Valid @RequestBody VehiculoRequestDTO request) {
        try {
            Vehiculo updatedVehiculo = vehiculoService.updateVehiculo(id, request);
            return ResponseEntity.ok(new VehiculoResponseDTO(updatedVehiculo));
        } catch (IllegalArgumentException e) {
            // Catches errors for invalid data
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            // Catches "vehicle not found" error from the service
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a vehicle by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable Long id) {
        try {
            vehiculoService.deleteVehiculo(id);
            return ResponseEntity.noContent().build(); // Standard response for successful deletion
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}