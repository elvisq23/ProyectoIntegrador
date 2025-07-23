package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Llegada;
import com.utp.integradorspringboot.repositories.LlegadaRepository;
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
public class LlegadaController {

    @Autowired
    LlegadaRepository llegadaRepository;

    @PostMapping("/llegadas")
    public ResponseEntity<Llegada> createLlegada(@RequestBody Llegada llegada) {
        try {
            Llegada nueva = new Llegada(
                llegada.getNombres(),
                llegada.getApellidos(),
                llegada.getMarca(),
                llegada.getModelo(),
                llegada.getPlaca(),
                llegada.getColor()
            );
            Llegada _llegada = llegadaRepository.save(nueva);
            return new ResponseEntity<>(_llegada, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al crear llegada: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/llegadas")
    public ResponseEntity<List<Llegada>> getAllLlegadas(@RequestParam(required = false) String search) {
        try {
            List<Llegada> llegadas = new ArrayList<>();

            if (search == null || search.isEmpty()) {
                llegadaRepository.findAll().forEach(llegadas::add);
            } else {
                llegadaRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCaseOrMarcaContainingIgnoreCaseOrPlacaContainingIgnoreCase(
                    search, search, search, search
                ).forEach(llegadas::add);
            }

            if (llegadas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(llegadas, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al obtener llegadas: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/llegadas/{id}")
    public ResponseEntity<Llegada> getLlegadaById(@PathVariable("id") Long id) {
        Optional<Llegada> llegadaData = llegadaRepository.findById(id);

        if (llegadaData.isPresent()) {
            return new ResponseEntity<>(llegadaData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/llegadas/{id}")
    public ResponseEntity<HttpStatus> deleteLlegada(@PathVariable("id") Long id) {
        try {
            llegadaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error al eliminar llegada: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
