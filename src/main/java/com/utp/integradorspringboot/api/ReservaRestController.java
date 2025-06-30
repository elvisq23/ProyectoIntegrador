package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.EstadoReserva;
import com.utp.integradorspringboot.models.Reserva;
import com.utp.integradorspringboot.services.ReservaService;
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
public class ReservaRestController {

    @Autowired
    ReservaService reservaService;

    @PostMapping("/reservas")
    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) {
        try {
            Reserva _reserva = reservaService.createReserva(reserva);
            return new ResponseEntity<>(_reserva, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al crear reserva: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reservas")
    public ResponseEntity<List<Reserva>> getAllReservas(@RequestParam(required = false) EstadoReserva estado) {
        try {
            List<Reserva> reservas = new ArrayList<>();

            if (estado == null) {
                reservas = reservaService.getAllReservas();
            } else {
                reservas = reservaService.getAllReservas().stream()
                        .filter(r -> r.getEstado().equals(estado))
                        .toList();
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

    @GetMapping("/reservas/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable("id") Long id) {
        Optional<Reserva> reservaData = reservaService.getReservaById(id);

        if (reservaData.isPresent()) {
            return new ResponseEntity<>(reservaData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/reservas/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable("id") Long id, @RequestBody Reserva reserva) {
        try {
            Reserva updatedReserva = reservaService.updateReserva(id, reserva);
            return new ResponseEntity<>(updatedReserva, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al actualizar reserva: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/reservas/{id}")
    public ResponseEntity<HttpStatus> deleteReserva(@PathVariable("id") Long id) {
        try {
            reservaService.deleteReserva(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error al eliminar reserva: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
