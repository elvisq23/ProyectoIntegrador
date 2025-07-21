/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot;

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
    private VehiculoRepository repository;

    @GetMapping("/vehiculos")
    public ResponseEntity<List<Vehiculo>> getAllVehiculos() {
        try {
            List<Vehiculo> vehiculos = new ArrayList<>();
            repository.findAll().forEach(vehiculos::add);

            if (vehiculos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(vehiculos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vehiculos/{id}")
    public ResponseEntity<Vehiculo> getVehiculoById(@PathVariable("id") Integer id) {
        Optional<Vehiculo> vehiculoData = repository.findById(id);

        return vehiculoData
                .map(vehiculo -> new ResponseEntity<>(vehiculo, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/vehiculos")
    public ResponseEntity<Vehiculo> createVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo nuevoVehiculo = repository.save(vehiculo);
            return new ResponseEntity<>(nuevoVehiculo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/vehiculos/{id}")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable("id") Integer id, @RequestBody Vehiculo vehiculo) {
        Optional<Vehiculo> vehiculoData = repository.findById(id);

        if (vehiculoData.isPresent()) {
            Vehiculo _vehiculo = vehiculoData.get();
            _vehiculo.setMarca(vehiculo.getMarca());
            _vehiculo.setModelo(vehiculo.getModelo());
            _vehiculo.setPlaca(vehiculo.getPlaca());
            _vehiculo.setNombreConductor(vehiculo.getNombreConductor());
            return new ResponseEntity<>(repository.save(_vehiculo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/vehiculos/{id}")
    public ResponseEntity<HttpStatus> deleteVehiculo(@PathVariable("id") Integer id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

