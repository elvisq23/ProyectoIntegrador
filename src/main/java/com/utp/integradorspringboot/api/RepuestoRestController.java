package com.utp.integradorspringboot.api;
import com.utp.integradorspringboot.models.Proveedor;
import com.utp.integradorspringboot.models.Repuesto;
import com.utp.integradorspringboot.repositories.ProveedorRepository;
import com.utp.integradorspringboot.repositories.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/repuestos")
public class RepuestoRestController {

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @PostMapping
    public ResponseEntity<Repuesto> createRepuesto(@RequestBody Repuesto repuesto) {
        try {
            if (repuesto.getProveedor() != null && repuesto.getProveedor().getId() != null) {
                Proveedor proveedor = proveedorRepository.findById(repuesto.getProveedor().getId())
                        .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
                repuesto.setProveedor(proveedor);
            }

            Repuesto _repuesto = repuestoRepository.save(repuesto);
            return new ResponseEntity<>(_repuesto, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al crear repuesto: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Repuesto>> getAllRepuestos(@RequestParam(required = false) String nombre) {
        try {
            List<Repuesto> repuestos = new ArrayList<>();
            if (nombre == null || nombre.isEmpty()) {
                repuestoRepository.findAll().forEach(repuestos::add);
            } else {
                repuestoRepository.findByNombreContainingIgnoreCase(nombre).forEach(repuestos::add);
            }

            if (repuestos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(repuestos, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al obtener repuestos: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> getRepuestoById(@PathVariable("id") Long id) {
        Optional<Repuesto> repuesto = repuestoRepository.findById(id);
        return repuesto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repuesto> updateRepuesto(@PathVariable("id") Long id, @RequestBody Repuesto repuesto) {
        Optional<Repuesto> repuestoData = repuestoRepository.findById(id);

        if (repuestoData.isPresent()) {
            Repuesto _repuesto = repuestoData.get();
            _repuesto.setNombre(repuesto.getNombre());
            _repuesto.setDescripcion(repuesto.getDescripcion());
            _repuesto.setPrecioUnitario(repuesto.getPrecioUnitario());
            _repuesto.setStock(repuesto.getStock());

            if (repuesto.getProveedor() != null && repuesto.getProveedor().getId() != null) {
                Proveedor proveedor = proveedorRepository.findById(repuesto.getProveedor().getId())
                        .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
                _repuesto.setProveedor(proveedor);
            } else {
                _repuesto.setProveedor(null);
            }

            return new ResponseEntity<>(repuestoRepository.save(_repuesto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRepuesto(@PathVariable("id") Long id) {
        try {
            repuestoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error al eliminar repuesto: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}