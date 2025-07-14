package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Reservadia;
import com.utp.integradorspringboot.repositories.ReservadiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class Reserva_diaController {

    @Autowired
    private ReservadiaRepository repository;

    /* ─────────────────────────────
       LISTAR TODAS LAS RESERVAS
       GET /api/reservas-dia
    ───────────────────────────── */
    @GetMapping("/reservas-dia")
    public ResponseEntity<List<Reservadia>> getAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    /* ─────────────────────────────
       OBTENER UNA RESERVA POR ID
       GET /api/reservas-dia/{id}
    ───────────────────────────── */
    @GetMapping("/reservas-dia/{id}")
    public ResponseEntity<Reservadia> getById(@PathVariable Integer id) {
        Optional<Reservadia> data = repository.findById(id);
        return data.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /* ─────────────────────────────
       CREAR UNA NUEVA RESERVA
       POST /api/reservas-dia
    ───────────────────────────── */
    @PostMapping("/reservas-dia")
    public ResponseEntity<Reservadia> create(@RequestBody Reservadia reserva) {
        try {
            Reservadia nuevo = repository.save(reserva);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* ─────────────────────────────
       ACTUALIZAR UNA RESERVA EXISTENTE
       PUT /api/reservas-dia/{id}
    ───────────────────────────── */
    @PutMapping("/reservas-dia/{id}")
    public ResponseEntity<Reservadia> update(@PathVariable Integer id,
                                             @RequestBody Reservadia reservaReq) {
        Optional<Reservadia> data = repository.findById(id);
        if (data.isPresent()) {
            Reservadia r = data.get();
            // Actualizar los campos
            r.setVehiculo(reservaReq.getVehiculo());
            r.setModelo(reservaReq.getModelo());
            r.setPlaca(reservaReq.getPlaca());
            r.setTaller(reservaReq.getTaller());
            r.setEstado(reservaReq.getEstado());
            r.setFechaHora(reservaReq.getFechaHora());
            return new ResponseEntity<>(repository.save(r), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /* ─────────────────────────────
       ELIMINAR UNA RESERVA POR ID
       DELETE /api/reservas-dia/{id}
    ───────────────────────────── */
    @DeleteMapping("/reservas-dia/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
