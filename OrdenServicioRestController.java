package com.utp.integradorspringboot.rest;

import com.utp.integradorspringboot.dto.OrdenRequestDTO;
import com.utp.integradorspringboot.dto.OrdenServicioResponseDTO;
import com.utp.integradorspringboot.models.OrdenServicio;
import com.utp.integradorspringboot.services.OrdenServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ordenes-servicio")
public class OrdenServicioRestController {

    @Autowired
    private OrdenServicioService ordenServicioService;

    // Endpoint para listar TODAS las órdenes (usado por la tabla principal)
    @GetMapping
    public ResponseEntity<List<OrdenServicioResponseDTO>> listarTodasLasOrdenes() {
        List<OrdenServicioResponseDTO> ordenes = ordenServicioService.getAllOrdenesServicio()
                .stream()
                .map(OrdenServicioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ordenes);
    }

    // --- ENDPOINT CLAVE PARA "VER DETALLES" ---
    // Devuelve los datos de UNA SOLA orden por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<OrdenServicioResponseDTO> obtenerOrdenPorId(@PathVariable Long id) {
        return ordenServicioService.getOrdenServicioById(id)
                .map(orden -> ResponseEntity.ok(new OrdenServicioResponseDTO(orden)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para CREAR una nueva orden (usado por el formulario)
    @PostMapping
    public ResponseEntity<OrdenServicio> crearNuevaOrden(@RequestBody OrdenRequestDTO requestDTO) {
        try {
            OrdenServicio nuevaOrden = ordenServicioService.crearOrdenServicio(requestDTO);
            return ResponseEntity.ok(nuevaOrden);
        } catch (Exception e) {
            // Es una buena práctica devolver un error claro si algo falla
            return ResponseEntity.badRequest().build();
        }
    }
}