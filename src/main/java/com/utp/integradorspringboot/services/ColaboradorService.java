package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.ColaboradorRequestDTO;
import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.models.Sede;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import com.utp.integradorspringboot.repositories.RolRepository;
import com.utp.integradorspringboot.repositories.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ColaboradorService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private SedeRepository sedeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; 

    @Transactional(readOnly = true)
    private List<String> getValidColaboratorRoleNames() {
        List<String> validRoles = rolRepository.findAll().stream()
                .peek(rol -> System.out.println("DEBUG - Processing role: " + rol.getNombre() + " (ID: " + rol.getId() + ")"))
                .filter(rol -> rol.getNombre() != null && !rol.getNombre().trim().isEmpty() && !rol.getNombre().equalsIgnoreCase("CONDUCTOR"))
                .map(Rol::getNombre)
                .collect(Collectors.toList());
        System.out.println("DEBUG - Valid Colaborator Role Names for query: " + validRoles);
        return validRoles;
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAllColaboradores(String searchQuery) {
        List<String> rolesFiltrados = getValidColaboratorRoleNames();
        List<Usuario> colaboradores;
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            colaboradores = usuarioRepository.findActiveColaboradoresBySearchQuery(rolesFiltrados, "%" + searchQuery.toLowerCase() + "%");
        } else {
            colaboradores = usuarioRepository.findByRoles_NombreInAndEstadoTrue(rolesFiltrados);
        }
   
        return colaboradores;
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> getColaboradorById(Long id) {
        List<String> rolesFiltrados = getValidColaboratorRoleNames();
   
        return usuarioRepository.findByIdAndRoles_NombreIn(id, rolesFiltrados);
    }

    @Transactional
    public Usuario saveColaborador(ColaboradorRequestDTO request) {
        if (request.getId() != null && request.getId() != 0) {
            throw new IllegalArgumentException("No se debe proporcionar ID para la creación de un nuevo colaborador.");
        }
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con este correo.");
        }
        if (usuarioRepository.findByDni(request.getDni()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con este DNI.");
        }
      
        if (request.getContrasenia() == null || request.getContrasenia().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria para un nuevo colaborador.");
        }

        Usuario nuevoColaborador = new Usuario();
        nuevoColaborador.setNombres(request.getNombres());
        nuevoColaborador.setApellidos(request.getApellidos());
        nuevoColaborador.setDni(request.getDni());
        nuevoColaborador.setTelefono(request.getTelefono());
        nuevoColaborador.setRuc(request.getRuc());
        nuevoColaborador.setCorreo(request.getCorreo());
        
        nuevoColaborador.setContrasenia(request.getContrasenia()); 

        nuevoColaborador.setFechaRegistro(LocalDateTime.now());
        nuevoColaborador.setEstado(request.getEstado() != null ? request.getEstado() : true);

        Sede sede = sedeRepository.findById(request.getSedeId())
                                   .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada con ID: " + request.getSedeId()));
        nuevoColaborador.setSede(sede);

        Rol rol = rolRepository.findById(request.getRolId())
                               .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + request.getRolId()));

        if (rol.getNombre().equalsIgnoreCase("CONDUCTOR")) {
            throw new IllegalArgumentException("El rol 'CONDUCTOR' no es un rol válido para colaboradores.");
        }

        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        nuevoColaborador.setRoles(roles);

        Usuario savedColaborador = usuarioRepository.save(nuevoColaborador);

    
        return usuarioRepository.findByIdWithDetails(savedColaborador.getId()) // Asumo que tienes este método en tu repo
                .orElse(savedColaborador); // Fallback si no se encuentra (no debería ocurrir)
    }

    @Transactional
    public Usuario updateColaborador(Long id, ColaboradorRequestDTO request) {
        List<String> rolesFiltrados = getValidColaboratorRoleNames();
        Usuario existingColaborador = usuarioRepository.findByIdAndRoles_NombreIn(id, rolesFiltrados)
                .orElseThrow(() -> new RuntimeException("Colaborador no encontrado con ID: " + id + " o no tiene un rol de COLABORADOR."));

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

        existingColaborador.setNombres(request.getNombres());
        existingColaborador.setApellidos(request.getApellidos());
        existingColaborador.setDni(request.getDni());
        existingColaborador.setTelefono(request.getTelefono());
        existingColaborador.setRuc(request.getRuc());
        existingColaborador.setCorreo(request.getCorreo());

       
        if (request.getContrasenia() != null && !request.getContrasenia().isEmpty()) {
            existingColaborador.setContrasenia(request.getContrasenia()); 
        }
       

        existingColaborador.setEstado(request.getEstado() != null ? request.getEstado() : existingColaborador.getEstado());

        Sede sede = sedeRepository.findById(request.getSedeId())
                                   .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada con ID: " + request.getSedeId()));
        existingColaborador.setSede(sede);

        Rol rol = rolRepository.findById(request.getRolId())
                               .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + request.getRolId()));

        if (rol.getNombre().equalsIgnoreCase("CONDUCTOR")) {
            throw new IllegalArgumentException("El rol 'CONDUCTOR' no es un rol válido para colaboradores.");
        }

        existingColaborador.getRoles().clear();
        existingColaborador.addRol(rol);

        Usuario updatedColaborador = usuarioRepository.save(existingColaborador);
        
        return usuarioRepository.findByIdWithDetails(updatedColaborador.getId())
                .orElse(updatedColaborador);
    }

    @Transactional
    public void deactivateColaborador(Long id) {
        List<String> rolesFiltrados = getValidColaboratorRoleNames();
        Usuario colaborador = usuarioRepository.findByIdAndRoles_NombreIn(id, rolesFiltrados)
                .orElseThrow(() -> new RuntimeException("Colaborador no encontrado con ID: " + id + " o no tiene un rol de COLABORADOR."));

        colaborador.setEstado(false);
        usuarioRepository.save(colaborador);
    }

    @Transactional(readOnly = true)
    public List<Rol> getColaboradorRoles() {
        return rolRepository.findAll().stream()
                .filter(rol -> rol.getNombre() != null && !rol.getNombre().trim().isEmpty() && !rol.getNombre().equalsIgnoreCase("CONDUCTOR"))
                .collect(Collectors.toList());
    }
}