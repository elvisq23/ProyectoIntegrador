package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Servicio;
import com.utp.integradorspringboot.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/servicios")
public class ServicioRestController {

    @Autowired
    ServicioRepository servicioRepository;

    @PostMapping
    public ResponseEntity<Servicio> createServicio(@RequestBody Servicio servicio) {
        try {
            Servicio nuevo = new Servicio(
                    servicio.getNombre(),
                    servicio.getDescripcion(),
                    servicio.getPrecio()
            );
            Servicio saved = servicioRepository.save(nuevo);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al crear servicio: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Servicio>> getAllServicios(@RequestParam(required = false) String nombre) {
        try {
            List<Servicio> servicios = new ArrayList<>();

            if (nombre == null || nombre.isEmpty()) {
                servicioRepository.findAll().forEach(servicios::add);
            } else {
                servicioRepository.findByNombreContaining(nombre).forEach(servicios::add);
            }

            if (servicios.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(servicios, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al obtener servicios: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable("id") Long id) {
        Optional<Servicio> data = servicioRepository.findById(id);

        return data.map(servicio -> new ResponseEntity<>(servicio, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable("id") Long id, @RequestBody Servicio servicio) {
        Optional<Servicio> data = servicioRepository.findById(id);

        if (data.isPresent()) {
            Servicio existente = data.get();
            existente.setNombre(servicio.getNombre());
            existente.setDescripcion(servicio.getDescripcion());
            existente.setPrecio(servicio.getPrecio());
            return new ResponseEntity<>(servicioRepository.save(existente), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteServicio(@PathVariable("id") Long id) {
        try {
            servicioRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error al eliminar servicio: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}