package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.RolRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import com.utp.integradorspringboot.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections; 
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet; 

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, String> register(RegisterRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            errors.put("confirmPassword", "Las contraseñas no coinciden.");
        }
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            errors.put("correo", "Ya existe una cuenta con este correo.");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombres(request.getNombres());
        nuevoUsuario.setApellidos(request.getApellidos());
        nuevoUsuario.setDni(request.getDni());
        nuevoUsuario.setTelefono(request.getTelefono());
        nuevoUsuario.setRuc(request.getRuc());
        nuevoUsuario.setCorreo(request.getCorreo());
        nuevoUsuario.setContrasenia(passwordEncoder.encode(request.getPassword()));
        nuevoUsuario.setFechaRegistro(LocalDateTime.now());
        nuevoUsuario.setEstado(false);

        Rol rolConductor = rolRepository.findByNombre("ROLE_DUENO")
                                    .orElseThrow(() -> new RuntimeException("Rol CONDUCTOR no encontrado. ¡Debe existir en la BD!"));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolConductor);
        nuevoUsuario.setRoles(roles); 

        String verificationCode = "123456";
        nuevoUsuario.setCodigoVerificacion(verificationCode);
        nuevoUsuario.setFechaExpiracionCodigo(LocalDateTime.now().plusMinutes(15));

        usuarioRepository.save(nuevoUsuario);

        return errors;
    }

    public boolean verifyEmail(String email, String code) {
        Optional<Usuario> optionalUser = usuarioRepository.findByCorreo(email);
        if (optionalUser.isPresent()) {
            Usuario usuario = optionalUser.get();

            System.out.println("DEBUG (AuthService): Usuario encontrado: " + usuario.getCorreo());
            System.out.println("DEBUG (AuthService): Código guardado en DB: '" + usuario.getCodigoVerificacion() + "'");
            System.out.println("DEBUG (AuthService): Código recibido del formulario: '" + code + "'");
            System.out.println("DEBUG (AuthService): Fecha expiración en DB: " + usuario.getFechaExpiracionCodigo());
            System.out.println("DEBUG (AuthService): Fecha actual (LocalDateTime.now()): " + LocalDateTime.now());

            boolean isCodeMatch = (usuario.getCodigoVerificacion() != null && usuario.getCodigoVerificacion().equals(code));
            boolean isNotExpired = (usuario.getFechaExpiracionCodigo() != null && usuario.getFechaExpiracionCodigo().isAfter(LocalDateTime.now()));

            boolean isVerified = false;

            if (isCodeMatch && isNotExpired) {
                usuario.setEstado(true); 
                usuario.setCodigoVerificacion(null); 
                usuario.setFechaExpiracionCodigo(null); 
                usuarioRepository.save(usuario);
                isVerified = true;
                System.out.println("DEBUG (AuthService): Usuario VERIFICADO con éxito.");
            } else {
                if (!isCodeMatch) {
                    System.out.println("DEBUG (AuthService): Código NO coincide.");
                }
                if (!isNotExpired) {
                    System.out.println("DEBUG (AuthService): Código EXPIRADO o fecha de expiración es NULL.");
                }
                System.out.println("DEBUG (AuthService): Verificación FALLIDA.");
            }
            return isVerified;

        } else {
            System.out.println("DEBUG (AuthService): Usuario no encontrado para el correo: " + email);
        }
        return false;
    }
}