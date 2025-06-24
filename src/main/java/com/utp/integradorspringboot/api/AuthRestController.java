package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.RegisterRequest;
import com.utp.integradorspringboot.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Asegúrate de que sea @Controller
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

// Importaciones adicionales que podrían ser necesarias si no las tienes (aunque ya están en el AuthRestController completo)
// import org.springframework.web.bind.annotation.GetMapping; // Si decides mover el GET /verify-email aquí
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Si autenticas programáticamente
// import org.springframework.security.core.context.SecurityContextHolder; // Si autenticas programáticamente
// import org.springframework.security.core.userdetails.UserDetails; // Si autenticas programáticamente
// import org.springframework.security.core.userdetails.UserDetailsService; // Si autenticas programáticamente


@Controller
@RequestMapping("/api/auth") // Las acciones de tu formulario POSTearán a /api/auth/register y /api/auth/verify
public class AuthRestController {

    @Autowired
    private AuthService authService;

    // Si estás inyectando UserDetailsService para autenticación programática, necesitarías algo así:
    // @Autowired
    // private UserDetailsService userDetailsService;


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

        // CAMBIO AQUÍ: Volvemos a redirigir a /verify-email después de un registro exitoso
        redirectAttributes.addAttribute("email", registerRequest.getCorreo()); // Para que la página de verificación sepa el email
        redirectAttributes.addFlashAttribute("successMessage", "¡Tu cuenta ha sido creada! Por favor, verifica tu correo.");
        System.out.println("DEBUG (AuthRestController): Usuario registrado y redirigiendo a /verify-email.");
        return "redirect:/verify-email"; // <--- ESTE ES EL CAMBIO CLAVE
    }


    @PostMapping("/verify")
    public String verifyEmail(@RequestParam("email") String email,
                              @RequestParam("code") String code,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        System.out.println("DEBUG (AuthRestController): Recibida petición de verificación para email: " + email + ", código: " + code);

        try {
            boolean verified = authService.verifyEmail(email, code);

            if (verified) {
                System.out.println("DEBUG (AuthRestController): Verificación exitosa para: " + email);
                redirectAttributes.addFlashAttribute("successMessage", "¡Tu correo ha sido verificado! Ahora puedes iniciar sesión.");
                System.out.println("DEBUG (AuthRestController): Verificación exitosa. Redirigiendo a /login.");
                return "redirect:/login"; // MANTENEMOS ESTO: Redirige a /login después de una verificación exitosa

            } else {
                System.out.println("DEBUG (AuthRestController): Verificación fallida para: " + email);
                redirectAttributes.addAttribute("email", email);
                redirectAttributes.addFlashAttribute("errorMessage", "Código de verificación inválido o expirado. Inténtalo de nuevo.");
                return "redirect:/verify-email";
            }
        } catch (RuntimeException e) {
            System.err.println("DEBUG (AuthRestController): Error inesperado durante la verificación: " + e.getMessage());
            redirectAttributes.addAttribute("email", email);
            redirectAttributes.addFlashAttribute("errorMessage", "Error al verificar el correo: " + e.getMessage());
            return "redirect:/verify-email";
        }
    }
}