package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Sede;
import com.utp.integradorspringboot.repositories.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SedeRestController {

    @Autowired
    SedeRepository sedeRepository;

    @PostMapping("/sedes")
    public ResponseEntity<Sede> createSede(@RequestBody Sede sede) {
        try {
            Sede _sede = sedeRepository.save(new Sede(
                sede.getNombreSede(),
                sede.getDireccion(),
                sede.getTelefonoContacto(),
                sede.getCapacidadMaxima(),
                sede.getAutosOcupados() != null ? sede.getAutosOcupados() : 0
            ));
            return new ResponseEntity<>(_sede, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al crear sede: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sedes")
    public ResponseEntity<List<Sede>> getAllSedes(@RequestParam(required = false) String nombreSede) {
        try {
            List<Sede> sedes = new ArrayList<>();

            if (nombreSede == null || nombreSede.isEmpty()) {
                sedeRepository.findAll().forEach(sedes::add);
            } else {
                sedeRepository.findByNombreSedeContaining(nombreSede).forEach(sedes::add);
            }

            if (sedes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(sedes, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al obtener sedes: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sedes/{id}")
    public ResponseEntity<Sede> getSedeById(@PathVariable("id") Long id) { 
        Optional<Sede> sedeData = sedeRepository.findById(id);

        if (sedeData.isPresent()) {
            return new ResponseEntity<>(sedeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/sedes/{id}")
    public ResponseEntity<Sede> updateSede(@PathVariable("id") Long id, @RequestBody Sede sede) { 
        Optional<Sede> sedeData = sedeRepository.findById(id);

        if (sedeData.isPresent()) {
            Sede _sede = sedeData.get();
            _sede.setNombreSede(sede.getNombreSede());
            _sede.setDireccion(sede.getDireccion());
            _sede.setTelefonoContacto(sede.getTelefonoContacto());
            _sede.setCapacidadMaxima(sede.getCapacidadMaxima());
            return new ResponseEntity<>(sedeRepository.save(_sede), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/sedes/{id}")
    public ResponseEntity<HttpStatus> deleteSede(@PathVariable("id") Long id) { 
        try {
            sedeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error al eliminar sede: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}