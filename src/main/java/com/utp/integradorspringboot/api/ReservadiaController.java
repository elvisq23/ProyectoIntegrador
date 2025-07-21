package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Reservadia;
import com.utp.integradorspringboot.repositories.ReservadiaRepository;
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
public class ReservadiaController {

    @Autowired
    private ReservadiaRepository reservadiaRepository;

    @PostMapping("/reservas-dia")
    public ResponseEntity<Reservadia> createReserva(@RequestBody Reservadia reserva) {
        try {
            Reservadia _reserva = reservadiaRepository.save(new Reservadia(
                reserva.getNombres(),
                reserva.getApellidos(),
                reserva.getMarca(),
                reserva.getModelo(),
                reserva.getPlaca(),
                reserva.getSede(),
                reserva.getLlego() != null ? reserva.getLlego() : false
            ));
            return new ResponseEntity<>(_reserva, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al crear reserva: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reservas-dia")
    public ResponseEntity<List<Reservadia>> getAllReservas(@RequestParam(required = false) String search) {
        try {
            List<Reservadia> reservas = new ArrayList<>();

            if (search == null || search.isEmpty()) {
                reservadiaRepository.findAll().forEach(reservas::add);
            } else {
                // Si luego implementas un método personalizado como findByApellidosOrMarcaOrPlacaContaining, lo puedes usar aquí.
                reservadiaRepository.findAll().stream()
                    .filter(r ->
                        r.getApellidos().toLowerCase().contains(search.toLowerCase()) ||
                        r.getMarca().toLowerCase().contains(search.toLowerCase()) ||
                        r.getPlaca().toLowerCase().contains(search.toLowerCase())
                    ).forEach(reservas::add);
            }

            if (reservas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reservas, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al obtener reservas: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reservas-dia/{id}")
    public ResponseEntity<Reservadia> getReservaById(@PathVariable("id") Long id) {
        Optional<Reservadia> reservaData = reservadiaRepository.findById(id);

        if (reservaData.isPresent()) {
            return new ResponseEntity<>(reservaData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/reservas-dia/{id}")
    public ResponseEntity<Reservadia> updateReserva(@PathVariable("id") Long id, @RequestBody Reservadia reserva) {
        Optional<Reservadia> reservaData = reservadiaRepository.findById(id);

        if (reservaData.isPresent()) {
            Reservadia _reserva = reservaData.get();
            _reserva.setNombres(reserva.getNombres());
            _reserva.setApellidos(reserva.getApellidos());
            _reserva.setMarca(reserva.getMarca());
            _reserva.setModelo(reserva.getModelo());
            _reserva.setPlaca(reserva.getPlaca());
            _reserva.setSede(reserva.getSede());
            _reserva.setLlego(reserva.getLlego());
            return new ResponseEntity<>(reservadiaRepository.save(_reserva), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/reservas-dia/{id}")
    public ResponseEntity<HttpStatus> deleteReserva(@PathVariable("id") Long id) {
        try {
            reservadiaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error al eliminar reserva: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
