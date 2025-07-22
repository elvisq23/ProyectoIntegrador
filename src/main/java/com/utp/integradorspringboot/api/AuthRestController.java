package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.RegisterRequest;
import com.utp.integradorspringboot.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping; // Añadir para los formularios de reset
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Añadir esta importación

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/api/auth")
public class AuthRestController { // Si solo manejas vistas, considera renombrarlo a AuthController.

    @Autowired
    private AuthService authService;

    // --- Endpoint para Registro de Usuario (EXISTENTE, SIN CAMBIOS) ---
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegisterRequest registerRequest,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            model.addAttribute("errors", errors);
            model.addAttribute("usuario", registerRequest);
            return "register";
        }

        Map<String, String> serviceErrors = authService.register(registerRequest);
        if (!serviceErrors.isEmpty()) {
            model.addAttribute("errors", serviceErrors);
            model.addAttribute("usuario", registerRequest);
            return "register";
        }

        redirectAttributes.addAttribute("email", registerRequest.getCorreo());
        redirectAttributes.addFlashAttribute("successMessage", "¡Tu cuenta ha sido creada! Por favor, verifica tu correo.");
        System.out.println("DEBUG (AuthRestController): Usuario registrado y redirigiendo a /verify-email.");
        return "redirect:/verify-email";
    }


    // --- Endpoint para Verificar el Código de Registro (EXISTENTE, CON MEJORA EN ERROR HANDLING) ---
    @PostMapping("/verify")
    public String verifyEmail(@RequestParam("email") String email,
                              @RequestParam("code") String code,
                              RedirectAttributes redirectAttributes,
                              Model model) { // Model no se usa aquí directamente para añadir atributos a la vista de redirección

        System.out.println("DEBUG (AuthRestController): Recibida petición de verificación para email: " + email + ", código: " + code);

        try {
            boolean verified = authService.verifyEmail(email, code);

            if (verified) {
                System.out.println("DEBUG (AuthRestController): Verificación exitosa para: " + email);
                redirectAttributes.addFlashAttribute("successMessage", "¡Tu correo ha sido verificado! Ahora puedes iniciar sesión.");
                System.out.println("DEBUG (AuthRestController): Verificación exitosa. Redirigiendo a /login.");
                return "redirect:/login";

            } else {
                System.out.println("DEBUG (AuthRestController): Verificación fallida para: " + email);
                redirectAttributes.addAttribute("email", email);
                redirectAttributes.addFlashAttribute("errorMessage", "Código de verificación inválido o expirado. Inténtalo de nuevo.");
                return "redirect:/verify-email";
            }
        } catch (UsernameNotFoundException e) { // Capturar si el usuario no existe en DB
            System.err.println("DEBUG (AuthRestController): Usuario no encontrado durante la verificación: " + email + " - " + e.getMessage());
            redirectAttributes.addAttribute("email", email);
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Usuario no encontrado para verificación.");
            return "redirect:/verify-email";
        }
        catch (RuntimeException e) { // Capturar cualquier otro error inesperado del servicio
            System.err.println("DEBUG (AuthRestController): Error inesperado durante la verificación: " + e.getMessage());
            redirectAttributes.addAttribute("email", email);
            redirectAttributes.addFlashAttribute("errorMessage", "Error al verificar el correo: " + e.getMessage());
            return "redirect:/verify-email";
        }
    }


    // --- NUEVOS ENDPOINTS PARA RECUPERACIÓN DE CONTRASEÑA (ADAPTADO A TU PATRÓN DE THYMELEAF) ---

    // Este endpoint maneja la solicitud de restablecimiento (envío de correo)
    // El frontend POSTeará el email a este endpoint.
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email,
                                        RedirectAttributes redirectAttributes) {
        if (email == null || email.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "El correo electrónico es obligatorio.");
            // Redirige a la URL que sirve el formulario de forgot-password
            return "redirect:/forgot-password";
        }

        try {
            authService.generatePasswordResetToken(email);
            // Mensaje genérico por seguridad, para no revelar si el correo existe o no
            redirectAttributes.addFlashAttribute("successMessage", "Si el correo electrónico está registrado, se ha enviado un enlace de restablecimiento de contraseña.");
            return "redirect:/login"; // Redirige al login después de enviar el correo
        } catch (UsernameNotFoundException e) {
            // Manejar como éxito para no dar pistas a atacantes
            redirectAttributes.addFlashAttribute("successMessage", "Si el correo electrónico está registrado, se ha enviado un enlace de restablecimiento de contraseña.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            System.err.println("Error en /forgot-password para " + email + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al procesar la solicitud de restablecimiento. Inténtalo de nuevo más tarde.");
            return "redirect:/forgot-password"; // Vuelve al formulario con error
        }
    }

    // Este endpoint maneja el envío de la nueva contraseña con el token
    // El frontend POSTeará el token y las nuevas contraseñas a este endpoint.
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmNewPassword") String confirmNewPassword,
                                       RedirectAttributes redirectAttributes) {
        if (newPassword == null || newPassword.isEmpty() || confirmNewPassword == null || confirmNewPassword.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Las contraseñas no pueden estar vacías.");
            redirectAttributes.addAttribute("token", token); // Pasar el token para que la URL sea correcta al redirigir
            return "redirect:/reset-password";
        }
        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Las contraseñas no coinciden.");
            redirectAttributes.addAttribute("token", token);
            return "redirect:/reset-password";
        }
        if (newPassword.length() < 6) { // Ejemplo de validación mínima
            redirectAttributes.addFlashAttribute("errorMessage", "La contraseña debe tener al menos 6 caracteres.");
            redirectAttributes.addAttribute("token", token);
            return "redirect:/reset-password";
        }

        try {
            authService.resetPassword(token, newPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Contraseña restablecida exitosamente. Ahora puedes iniciar sesión.");
            return "redirect:/login"; // Redirige al login después del éxito
        } catch (IllegalArgumentException e) {
            System.err.println("Error al restablecer contraseña: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addAttribute("token", token);
            return "redirect:/reset-password"; // Vuelve al formulario con error
        } catch (RuntimeException e) {
            System.err.println("Error inesperado en /reset-password: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al restablecer la contraseña. Inténtalo de nuevo más tarde.");
            redirectAttributes.addAttribute("token", token);
            return "redirect:/reset-password";
        }
    }
}