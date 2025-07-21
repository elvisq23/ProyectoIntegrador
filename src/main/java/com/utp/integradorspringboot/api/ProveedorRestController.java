package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Proveedor;
import com.utp.integradorspringboot.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/proveedores")
public class ProveedorRestController {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @PostMapping
    public ResponseEntity<Proveedor> createProveedor(@RequestBody Proveedor proveedor) {
        try {
            Proveedor nuevo = proveedorRepository.save(new Proveedor(
                    proveedor.getRuc(),
                    proveedor.getRazonSocial(),
                    proveedor.getTelefono(),
                    proveedor.getCorreo(),
                    proveedor.getDireccion()
            ));
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al crear proveedor: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> getAllProveedores(@RequestParam(required = false) String razonSocial) {
        try {
            List<Proveedor> proveedores = new ArrayList<>();

            if (razonSocial == null || razonSocial.isEmpty()) {
                proveedorRepository.findAll().forEach(proveedores::add);
            } else {
                proveedorRepository.findByRazonSocialContainingIgnoreCase(razonSocial).forEach(proveedores::add);
            }

            if (proveedores.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(proveedores, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al obtener proveedores: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getProveedorById(@PathVariable Long id) {
        Optional<Proveedor> data = proveedorRepository.findById(id);
        return data.map(proveedor -> new ResponseEntity<>(proveedor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        Optional<Proveedor> proveedorData = proveedorRepository.findById(id);

        if (proveedorData.isPresent()) {
            Proveedor _proveedor = proveedorData.get();
            _proveedor.setRuc(proveedor.getRuc());
            _proveedor.setRazonSocial(proveedor.getRazonSocial());
            _proveedor.setTelefono(proveedor.getTelefono());
            _proveedor.setCorreo(proveedor.getCorreo());
            _proveedor.setDireccion(proveedor.getDireccion());
            return new ResponseEntity<>(proveedorRepository.save(_proveedor), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProveedor(@PathVariable Long id) {
        try {
            proveedorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}