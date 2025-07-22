package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.RepuestoRequestDTO;
import com.utp.integradorspringboot.models.Repuesto;
import com.utp.integradorspringboot.repositories.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RepuestoService {

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Transactional(readOnly = true)
    public List<Repuesto> getAllRepuestos(String searchQuery) {
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            return repuestoRepository.findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(searchQuery.toLowerCase());
        }
        return repuestoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Repuesto> getRepuestoById(Long id) {
        return repuestoRepository.findById(id);
    }

    @Transactional
    public Repuesto saveRepuesto(RepuestoRequestDTO request) {
        if (request.getId() != null && request.getId() != 0) {
            throw new IllegalArgumentException("No se debe proporcionar ID para la creación de un nuevo repuesto.");
        }
        // Validar si ya existe un repuesto con el mismo nombre (ejemplo de validación de negocio)
        // Puedes añadir una búsqueda por nombre para evitar duplicados si lo necesitas
        // if (repuestoRepository.findByNombre(request.getNombre()).isPresent()) {
        //     throw new IllegalArgumentException("Ya existe un repuesto con este nombre.");
        // }

        Repuesto nuevoRepuesto = new Repuesto();
        nuevoRepuesto.setNombre(request.getNombre());
        nuevoRepuesto.setDescripcion(request.getDescripcion());
        nuevoRepuesto.setPrecioUnitario(request.getPrecioUnitario());
        nuevoRepuesto.setStock(request.getStock());

        return repuestoRepository.save(nuevoRepuesto);
    }

    @Transactional
    public Repuesto updateRepuesto(Long id, RepuestoRequestDTO request) {
        Repuesto existingRepuesto = repuestoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repuesto no encontrado con ID: " + id));

        // Puedes añadir validación para duplicados aquí también si el nombre es único
        // repuestoRepository.findByNombre(request.getNombre())
        //         .ifPresent(r -> {
        //             if (!r.getId().equals(id)) {
        //                 throw new IllegalArgumentException("El nombre del repuesto ya está en uso.");
        //             }
        //         });

        existingRepuesto.setNombre(request.getNombre());
        existingRepuesto.setDescripcion(request.getDescripcion());
        existingRepuesto.setPrecioUnitario(request.getPrecioUnitario());
        existingRepuesto.setStock(request.getStock());

        return repuestoRepository.save(existingRepuesto);
    }

    @Transactional
    public void deleteRepuesto(Long id) {
        if (!repuestoRepository.existsById(id)) {
            throw new RuntimeException("Repuesto no encontrado con ID: " + id);
        }
        repuestoRepository.deleteById(id);
    }

    // Método para actualizar stock (útil para futuras integraciones con servicios/pedidos)
    @Transactional
    public Repuesto updateStock(Long id, Integer quantityChange) {
        Repuesto repuesto = repuestoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repuesto no encontrado con ID: " + id));
        int newStock = repuesto.getStock() + quantityChange;
        if (newStock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        repuesto.setStock(newStock);
        return repuestoRepository.save(repuesto);
    }
}