package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.RegistroUsuarioDTO;
import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.RolRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepo;

    @Autowired
    private PasswordEncoder encoder;

    public void registrarConductor(RegistroUsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setNombres(dto.getNombres());
        u.setApellidos(dto.getApellidos());
        u.setCorreo(dto.getCorreo());
        u.setTelefono(dto.getTelefono());
        u.setDni(dto.getDni());
        u.setContrasenia(encoder.encode(dto.getContrasenia()));
        u.setFechaRegistro(LocalDateTime.now());
        u.setEstado(true);

        Rol rol = rolRepo.findByNombre("CONDUCTOR")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        u.getRoles().add(rol);

        usuarioRepository.save(u);
    }

    public Usuario obtenerUsuarioLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        String username = auth.getName();
        return usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
