package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Vehiculo;
import com.utp.integradorspringboot.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class VehiculoController {

    @Autowired
    VehiculoRepository vehiculoRepository;

    @PostMapping("/vehiculos")
    public ResponseEntity<Vehiculo> createVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo nuevoVehiculo = vehiculoRepository.save(new Vehiculo(
                vehiculo.getNombres(),
                vehiculo.getApellidos(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getPlaca(),
                vehiculo.getAnio(),
                vehiculo.getColor()
            ));
            return new ResponseEntity<>(nuevoVehiculo, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al crear vehículo: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vehiculos")
    public ResponseEntity<List<Vehiculo>> getAllVehiculos(@RequestParam(required = false) String search) {
        try {
            List<Vehiculo> vehiculos = new ArrayList<>();

            if (search == null || search.isEmpty()) {
                vehiculoRepository.findAll().forEach(vehiculos::add);
            } else {
                vehiculoRepository.findByPlacaContainingIgnoreCaseOrMarcaContainingIgnoreCaseOrApellidosContainingIgnoreCase(
                    search, search, search).forEach(vehiculos::add);
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
        Integer idInt = id != null ? id.intValue() : null;
        Optional<Vehiculo> vehiculoData = vehiculoRepository.findById(idInt);

        if (vehiculoData.isPresent()) {
            return new ResponseEntity<>(vehiculoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/vehiculos/{id}")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable("id") Long id, @RequestBody Vehiculo vehiculo) {
        Integer idInt = id != null ? id.intValue() : null;
        Optional<Vehiculo> vehiculoData = vehiculoRepository.findById(idInt);

        if (vehiculoData.isPresent()) {
            Vehiculo _vehiculo = vehiculoData.get();
            _vehiculo.setNombres(vehiculo.getNombres());
            _vehiculo.setApellidos(vehiculo.getApellidos());
            _vehiculo.setMarca(vehiculo.getMarca());
            _vehiculo.setModelo(vehiculo.getModelo());
            _vehiculo.setPlaca(vehiculo.getPlaca());
            _vehiculo.setAnio(vehiculo.getAnio());
            _vehiculo.setColor(vehiculo.getColor());
            return new ResponseEntity<>(vehiculoRepository.save(_vehiculo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/vehiculos/{id}")
    public ResponseEntity<HttpStatus> deleteVehiculo(@PathVariable("id") Long id) {
        try {
            Integer idInt = id != null ? id.intValue() : null;
            vehiculoRepository.deleteById(idInt);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
