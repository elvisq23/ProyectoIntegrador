package com.utp.integradorspringboot.services; // Asegúrate que el paquete sea 'services' o 'service'

import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.RolRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import com.utp.integradorspringboot.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // Importar Value
import org.springframework.mail.javamail.JavaMailSender; // Importar JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper; // Importar MimeMessageHelper
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar Transactional
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Importar

import jakarta.mail.MessagingException; // Para Spring Boot 3+ (o javax.mail para SB 2.x)
import jakarta.mail.internet.MimeMessage; // Para Spring Boot 3+ (o javax.mail para SB 2.x)

import java.time.LocalDateTime;
import java.security.SecureRandom; // Para generar códigos numéricos aleatorios
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors; // Para collect del stream
import java.util.UUID; // Para el token de recuperación de contraseña (si quieres usar UUID)

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender; // <-- Inyectar JavaMailSender

    @Value("${app.frontend.url}") // <-- Inyectar la URL del frontend
    private String frontendUrl;

    @Transactional // <--- Añadir @Transactional para asegurar la consistencia de la DB
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
        nuevoUsuario.setEstado(false); // <--- El usuario inicia como NO verificado

        Rol rolConductor = rolRepository.findByNombre("ROLE_CONDUCTOR")
                                        .orElseThrow(() -> new RuntimeException("Rol CONDUCTOR no encontrado. ¡Debe existir en la BD!"));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolConductor);
        nuevoUsuario.setRoles(roles);

        // --- ¡CAMBIOS AQUÍ! Generar código dinámico y enviarlo ---
        String verificationCode = generateNumericCode(6); // Genera un código numérico de 6 dígitos
        nuevoUsuario.setCodigoVerificacion(verificationCode);
        nuevoUsuario.setFechaExpiracionCodigo(LocalDateTime.now().plusMinutes(15)); // Código válido por 15 minutos

        usuarioRepository.save(nuevoUsuario);

        // Enviar el correo de verificación
        sendVerificationEmail(nuevoUsuario.getCorreo(), verificationCode); // <-- Llamada al nuevo método

        return errors; // Si no hay errores, se devuelve un mapa vacío
    }

    // --- Método auxiliar para generar código numérico ---
    private String generateNumericCode(int length) {
        SecureRandom random = new SecureRandom();
        return random.ints(length, 0, 10) // Genera 'length' números aleatorios entre 0 y 9
                     .mapToObj(String::valueOf) // Convierte cada número a String
                     .collect(Collectors.joining()); // Une los Strings para formar el código
    }

    // --- Método para enviar el correo de verificación de registro ---
    private void sendVerificationEmail(String recipientEmail, String verificationCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // true para HTML, UTF-8 para caracteres especiales

            helper.setFrom("elvisquilla@villacontrolperu.com"); // ¡TU CORREO CORPORATIVO AQUÍ!
            helper.setTo(recipientEmail);
            helper.setSubject("Verificación de Cuenta - Metal Cars");

            String emailContent = "<html><body>"
                                + "Estimado usuario,<br><br>"
                                + "Gracias por registrarte en Metal Cars. Para activar tu cuenta, por favor utiliza el siguiente código de verificación:<br><br>"
                                + "<h2 style='color: #007bff;'>" + verificationCode + "</h2><br>" // Estilo para destacar el código
                                + "Este código expirará en 15 minutos.<br>"
                                + "Si no te registraste en Metal Cars, por favor, ignora este correo.<br><br>"
                                + "Saludos cordiales,<br>El equipo de Metal Cars"
                                + "</body></html>";
            helper.setText(emailContent, true); // true indica que el contenido es HTML

            mailSender.send(message);
            System.out.println("DEBUG (AuthService): Correo de verificación enviado a: " + recipientEmail);
        } catch (MessagingException e) {
            System.err.println("ERROR (AuthService): Fallo al enviar el correo de verificación a " + recipientEmail + ": " + e.getMessage());
            // Considera lanzar una excepción específica o manejarla según la política de tu aplicación
            throw new RuntimeException("No se pudo enviar el correo de verificación. Por favor, inténtalo de nuevo más tarde.");
        }
    }

    // --- Tu método verifyEmail existente (CON PEQUEÑOS AJUSTES PARA ROBUSTEZ Y LOGS) ---
    @Transactional // <--- Asegurar que la transacción abarque la actualización del usuario
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

            if (isCodeMatch && isNotExpired) {
                usuario.setEstado(true); // Activa el usuario
                usuario.setCodigoVerificacion(null); // Limpia el código usado
                usuario.setFechaExpiracionCodigo(null); // Limpia la fecha de expiración
                usuarioRepository.save(usuario);
                System.out.println("DEBUG (AuthService): Usuario VERIFICADO con éxito.");
                return true;
            } else {
                if (!isCodeMatch) {
                    System.out.println("DEBUG (AuthService): Código NO coincide para usuario " + email);
                }
                if (!isNotExpired) {
                    System.out.println("DEBUG (AuthService): Código EXPIRADO o fecha de expiración es NULL para usuario " + email);
                }
                // Si el código expira, también deberías limpiarlo
                if (usuario.getFechaExpiracionCodigo() != null && usuario.getFechaExpiracionCodigo().isBefore(LocalDateTime.now())) {
                    usuario.setCodigoVerificacion(null);
                    usuario.setFechaExpiracionCodigo(null);
                    usuarioRepository.save(usuario);
                    System.out.println("DEBUG (AuthService): Código expirado limpiado para usuario " + email);
                }
                System.out.println("DEBUG (AuthService): Verificación FALLIDA para usuario " + email);
                return false;
            }
        } else {
            System.out.println("DEBUG (AuthService): Usuario no encontrado para el correo: " + email);
        }
        return false;
    }

    // --- Métodos de Recuperación de Contraseña (ya los teníamos, se mantienen) ---
    // Simplemente asegúrate de que los imports de jakarta.mail estén correctos si usas Spring Boot 3+

    @Transactional
    public void generatePasswordResetToken(String email) {
        Usuario user = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + email));

        // Para recuperación de contraseña, es común usar un UUID completo para el token
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30);

        user.setCodigoVerificacion(token); // Reutilizamos el campo para el token de recuperación
        user.setFechaExpiracionCodigo(expiryDate);
        usuarioRepository.save(user);

        String resetLink = frontendUrl + "/reset-password?token=" + token; // La URL en tu frontend
        sendPasswordResetEmail(user.getCorreo(), resetLink);
    }

    private void sendPasswordResetEmail(String recipientEmail, String resetLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("elvisquilla@villacontrolperu.com"); // ¡Tu correo corporativo!
            helper.setTo(recipientEmail);
            helper.setSubject("Restablecimiento de Contraseña - Metal Cars");

            String emailContent = "<html><body>"
                                + "Estimado usuario,<br><br>"
                                + "Ha solicitado restablecer la contraseña para su cuenta de Metal Cars.<br>"
                                + "Para proceder, haga clic en el siguiente enlace: <a href=\"" + resetLink + "\">" + resetLink + "</a><br><br>"
                                + "Este enlace es válido por 30 minutos.<br>"
                                + "Si usted no solicitó este restablecimiento, por favor, ignore este correo.<br><br>"
                                + "Saludos cordiales,<br>El equipo de Metal Cars"
                                + "</body></html>";
            helper.setText(emailContent, true);

            mailSender.send(message);
            System.out.println("DEBUG (AuthService): Correo de restablecimiento enviado a: " + recipientEmail);
        } catch (MessagingException e) {
            System.err.println("ERROR (AuthService): Fallo al enviar el correo de restablecimiento a " + recipientEmail + ": " + e.getMessage());
            throw new RuntimeException("No se pudo enviar el correo de restablecimiento. Por favor, inténtelo de nuevo más tarde.");
        }
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        Usuario user = usuarioRepository.findByCodigoVerificacion(token)
                .orElseThrow(() -> new IllegalArgumentException("Código de verificación inválido o no encontrado."));

        if (user.getFechaExpiracionCodigo() == null || user.getFechaExpiracionCodigo().isBefore(LocalDateTime.now())) {
            user.setCodigoVerificacion(null);
            user.setFechaExpiracionCodigo(null);
            usuarioRepository.save(user);
            throw new IllegalArgumentException("El código de verificación ha expirado. Por favor, solicita uno nuevo.");
        }

        user.setContrasenia(passwordEncoder.encode(newPassword));
        user.setCodigoVerificacion(null);
        user.setFechaExpiracionCodigo(null);
        usuarioRepository.save(user);
    }
}