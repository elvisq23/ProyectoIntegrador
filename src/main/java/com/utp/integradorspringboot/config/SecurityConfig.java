package com.utp.integradorspringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // ¡Esta anotación es CRUCIAL para que @PreAuthorize funcione en tus controladores!
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Considera habilitar esto en producción
            .authorizeHttpRequests(authorize -> authorize

                // Rutas accesibles para TODOS (incluso sin login)
                .requestMatchers(
                    new AntPathRequestMatcher("/"),
                    new AntPathRequestMatcher("/index"),
                    new AntPathRequestMatcher("/login"),
                    new AntPathRequestMatcher("/register"),
                    new AntPathRequestMatcher("/verify-email"),
                    new AntPathRequestMatcher("/api/auth/**"),
                    new AntPathRequestMatcher("/css/**"),
                    new AntPathRequestMatcher("/js/**"),
                    new AntPathRequestMatcher("/images/**"),
                    new AntPathRequestMatcher("/favicon.ico"),
                    new AntPathRequestMatcher("/error")
                ).permitAll()

                // ====================================================================
                // ¡¡¡ESTAS SON LAS REGLAS DE AUTORIZACIÓN BASADAS EN ROLES QUE FALTABAN!!!
                // ====================================================================
                .requestMatchers(new AntPathRequestMatcher("/dueno/**")).hasRole("DUENO")
                .requestMatchers(new AntPathRequestMatcher("/almacen/**")).hasRole("ALMACEN")
                .requestMatchers(new AntPathRequestMatcher("/asesor/**")).hasRole("ASESOR")
                .requestMatchers(new AntPathRequestMatcher("/cajero/**")).hasRole("CAJERO")
                .requestMatchers(new AntPathRequestMatcher("/conductor/**")).hasRole("CONDUCTOR")
                .requestMatchers(new AntPathRequestMatcher("/gerente/**")).hasRole("GERENTE")
                .requestMatchers(new AntPathRequestMatcher("/mecanico/**")).hasRole("MECANICO")

                // Rutas compartidas por múltiples roles
                .requestMatchers(new AntPathRequestMatcher("/colaboradores")).hasAnyRole("DUENO", "ASESOR", "GERENTE")
                .requestMatchers(new AntPathRequestMatcher("/conductores")).hasAnyRole("DUENO", "ASESOR", "GERENTE")
                // ====================================================================

                // Rutas que requieren autenticación pero no un rol específico (ej. /sedes)
                .requestMatchers(
                    new AntPathRequestMatcher("/sedes"),
                    new AntPathRequestMatcher("/api/sedes/**")
                ).authenticated()

                // Cualquier otra solicitud requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .successHandler(customAuthenticationSuccessHandler)
                .failureUrl("/login?error=true")
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
