package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.*;
import com.utp.integradorspringboot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mecanicos")
public class MecanicoApiController {

    @Autowired
    private OrdenServicioRepository ordenServicioRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioRolRepository usuarioRolRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;

    // Listar órdenes pendientes sin mecánico asignado
    @GetMapping("/pendientes")
    public List<OrdenPendienteDTO> listarOrdenesPendientesSinMecanico() {
        List<OrdenServicio> ordenes = ordenServicioRepository.findAll().stream()
                .filter(o -> "PENDIENTE".equalsIgnoreCase(o.getEstado()) && o.getIdUsuario() == null)
                .collect(Collectors.toList());

        List<OrdenPendienteDTO> dtos = new ArrayList<>();
        for (OrdenServicio o : ordenes) {
            String cliente = "";
            String vehiculo = "";
            // Busca el vehículo y el cliente real desde el vehículo
            if (o.getIdVehiculo() != null) {
                Optional<Vehiculo> v = vehiculoRepository.findById(o.getIdVehiculo());
                if (v.isPresent()) {
                    vehiculo = v.get().getMarca() + " " + v.get().getModelo();
                    if (v.get().getClienteId() != null) {
                        Optional<Usuario> user = usuarioRepository.findById(v.get().getClienteId());
                        if (user.isPresent()) {
                            cliente = user.get().getNombres() + " " + user.get().getApellidos();
                        }
                    }
                }
            }
            dtos.add(new OrdenPendienteDTO(o.getId(), cliente, vehiculo, o.getEstado()));
        }
        return dtos;
    }

    // Listar mecánicos disponibles
    @GetMapping("/disponibles")
    public List<Usuario> listarMecanicosDisponibles() {
        List<UsuarioRol> usuarioRoles = usuarioRolRepository.findByRolId(5); // 5 = mecánico
        List<Integer> idsMecanicos = usuarioRoles.stream().map(UsuarioRol::getUsuarioId).collect(Collectors.toList());
        if (idsMecanicos.isEmpty()) return new ArrayList<>();
        // Obtener mecánicos ocupados (asignados a orden pendiente)
        List<OrdenServicio> ordenesPendientes = ordenServicioRepository.findAll().stream()
            .filter(o -> "PENDIENTE".equalsIgnoreCase(o.getEstado()) && o.getIdUsuario() != null)
            .collect(Collectors.toList());
        Set<Integer> mecanicosOcupados = ordenesPendientes.stream()
            .map(OrdenServicio::getIdUsuario)
            .collect(Collectors.toSet());
        // Filtrar solo los mecánicos libres
        List<Integer> idsMecanicosLibres = idsMecanicos.stream()
            .filter(id -> !mecanicosOcupados.contains(id))
            .collect(Collectors.toList());
        if (idsMecanicosLibres.isEmpty()) return new ArrayList<>();
        return usuarioRepository.findAllById(idsMecanicosLibres);
    }

    // Asignar mecánico a una orden
    @PutMapping("/asignar/{ordenId}")
    public ResponseEntity<?> asignarMecanico(@PathVariable Integer ordenId, @RequestBody Map<String, Integer> body) {
        Integer idMecanico = body.get("idMecanico");
        Optional<OrdenServicio> optional = ordenServicioRepository.findById(ordenId);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        OrdenServicio orden = optional.get();
        orden.setIdUsuario(idMecanico);
        orden.setEstado("ACTIVO"); // Cambiar estado a ACTIVO al asignar
        ordenServicioRepository.save(orden);
        return ResponseEntity.ok().build();
    }
} 