package com.utp.integradorspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler; // NECESARIO
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler; // NECESARIO
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/sedes"); // URL a la que redirigir después de un login exitoso
        handler.setAlwaysUseDefaultTargetUrl(true); // Siempre redirige a /sedes
        return handler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    new AntPathRequestMatcher("/"),
                    new AntPathRequestMatcher("/index"),
                    new AntPathRequestMatcher("/login"),
                    new AntPathRequestMatcher("/register"),
                    new AntPathRequestMatcher("/verify-email"),
                    new AntPathRequestMatcher("/api/auth/**"), // Permite acceso a los POST de registro/verificación
                    new AntPathRequestMatcher("/css/**"),
                    new AntPathRequestMatcher("/js/**"),
                    new AntPathRequestMatcher("/images/**"),
                    new AntPathRequestMatcher("/favicon.ico"),
                    new AntPathRequestMatcher("/error")
                ).permitAll()
                .requestMatchers(
                    new AntPathRequestMatcher("/sedes"), // <--- Requiere autenticación
                    new AntPathRequestMatcher("/api/sedes/**") // <--- Requiere autenticación
                ).authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/authenticate") // La URL a la que el formulario de login enviará las credenciales
                .successHandler(authenticationSuccessHandler()) // Usa el manejador de éxito
                .failureUrl("/login?error=true") // Redirige con error si el login falla
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}