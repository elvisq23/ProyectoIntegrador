package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Vehiculo;
import com.utp.integradorspringboot.services.VehiculoService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class VehiculoRestController {

    @Autowired
    VehiculoService vehiculoService;

    @PostMapping("/vehiculos")
    public ResponseEntity<Vehiculo> createVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo _vehiculo = vehiculoService.createVehiculo(vehiculo);
            return new ResponseEntity<>(_vehiculo, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al crear vehículo: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vehiculos")
    public ResponseEntity<List<Vehiculo>> getAllVehiculos(@RequestParam(required = false) String placa,
                                                          @RequestParam(required = false) String marca) {
        try {
            List<Vehiculo> vehiculos = new ArrayList<>();

            if (placa != null) {
                vehiculos = vehiculoService.getAllVehiculos().stream()
                        .filter(v -> v.getPlaca().contains(placa))
                        .toList();
            } else if (marca != null) {
                vehiculos = vehiculoService.getAllVehiculos().stream()
                        .filter(v -> v.getMarca().contains(marca))
                        .toList();
            } else {
                vehiculos = vehiculoService.getAllVehiculos();
            }

            if (vehiculos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(vehiculos, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al obtener vehículos: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vehiculos/{id}")
    public ResponseEntity<Vehiculo> getVehiculoById(@PathVariable("id") Long id) {
        Optional<Vehiculo> vehiculoData = vehiculoService.getVehiculoById(id);

        if (vehiculoData.isPresent()) {
            return new ResponseEntity<>(vehiculoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/vehiculos/{id}")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable("id") Long id, @RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo updatedVehiculo = vehiculoService.updateVehiculo(id, vehiculo);
            return new ResponseEntity<>(updatedVehiculo, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al actualizar vehículo: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/vehiculos/{id}")
    public ResponseEntity<HttpStatus> deleteVehiculo(@PathVariable("id") Long id) {
        try {
            vehiculoService.deleteVehiculo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
