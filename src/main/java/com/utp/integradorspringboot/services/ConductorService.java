package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.ConductorRequest;
import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.RolRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ConductorService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllConductores(String searchQuery) {
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            return usuarioRepository.findActiveConductoresBySearchQuery("CONDUCTOR", searchQuery);
        }
        return usuarioRepository.findByRoles_NombreAndEstadoTrue("CONDUCTOR");
    }

    public Optional<Usuario> getConductorById(Long id) {
        return usuarioRepository.findById(id)
                .filter(usuario -> usuario.getRoles().stream().anyMatch(rol -> "CONDUCTOR".equals(rol.getNombre())));
    }

    @Transactional
    public Usuario saveConductor(ConductorRequest request) {
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con este correo.");
        }
        if (usuarioRepository.findByDni(request.getDni()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con este DNI.");
        }
        if (request.getContrasenia() == null || request.getContrasenia().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria para un nuevo conductor.");
        }

        Usuario nuevoConductor = new Usuario();
        nuevoConductor.setNombres(request.getNombres());
        nuevoConductor.setApellidos(request.getApellidos());
        nuevoConductor.setDni(request.getDni());
        nuevoConductor.setTelefono(request.getTelefono());
        nuevoConductor.setCorreo(request.getCorreo());
        nuevoConductor.setContrasenia(passwordEncoder.encode(request.getContrasenia()));
        nuevoConductor.setFechaRegistro(LocalDateTime.now());
        nuevoConductor.setEstado(request.getEstado() != null ? request.getEstado() : true);

        Rol rolConductor = rolRepository.findByNombre("CONDUCTOR")
                                    .orElseThrow(() -> new RuntimeException("Rol CONDUCTOR no encontrado. ¡Debe existir en la BD!"));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolConductor);
        nuevoConductor.setRoles(roles);

        return usuarioRepository.save(nuevoConductor);
    }

    @Transactional
    public Usuario updateConductor(Long id, ConductorRequest request) {
        Usuario existingConductor = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));

        usuarioRepository.findByCorreo(request.getCorreo())
            .ifPresent(u -> {
                if (!u.getId().equals(id)) {
                    throw new IllegalArgumentException("El correo ya está en uso por otro usuario.");
                }
            });
        usuarioRepository.findByDni(request.getDni())
            .ifPresent(u -> {
                if (!u.getId().equals(id)) {
                    throw new IllegalArgumentException("El DNI ya está en uso por otro usuario.");
                }
            });

        existingConductor.setNombres(request.getNombres());
        existingConductor.setApellidos(request.getApellidos());
        existingConductor.setDni(request.getDni());
        existingConductor.setTelefono(request.getTelefono());
        existingConductor.setCorreo(request.getCorreo());

        if (request.getContrasenia() != null && !request.getContrasenia().isEmpty()) {
            existingConductor.setContrasenia(passwordEncoder.encode(request.getContrasenia()));
        }

        existingConductor.setEstado(request.getEstado() != null ? request.getEstado() : existingConductor.getEstado());

        Rol rolConductor = rolRepository.findByNombre("CONDUCTOR")
                                    .orElseThrow(() -> new RuntimeException("Rol CONDUCTOR no encontrado. ¡Debe existir en la BD!"));
        if (!existingConductor.getRoles().contains(rolConductor)) {
            existingConductor.addRol(rolConductor);
        }

        return usuarioRepository.save(existingConductor);
    }

    @Transactional
    public void deactivateConductor(Long id) {
        Usuario conductor = usuarioRepository.findById(id)
            .filter(usuario -> usuario.getRoles().stream().anyMatch(rol -> "CONDUCTOR".equals(rol.getNombre())))
            .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id + " o no tiene el rol de CONDUCTOR."));

        conductor.setEstado(false);
        usuarioRepository.save(conductor);
    }

    public long count() {
        return usuarioRepository.count();
    }
}