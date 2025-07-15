package com.utp.integradorspringboot.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Elimina estas líneas de System.out.println después de verificar que todo funciona
        System.out.println("DEBUG: Entrando a onAuthenticationSuccess");
        System.out.println("DEBUG: Roles del usuario autenticado:");
        for (GrantedAuthority authority : authorities) {
            System.out.println(" - " + authority.getAuthority());
        }

        // Iterar sobre las autoridades para encontrar el rol principal del usuario
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();

            // CAMBIO CRÍTICO AQUÍ: "ROLE_DUEÑO" a "ROLE_DUENO" (sin la Ñ)
            if (role.equals("ROLE_DUENO")) {
                response.sendRedirect("/dueno/dueno_dashboard");
                return;
            } else if (role.equals("ROLE_MECANICO")) {
                response.sendRedirect("/mecanico/mecanico_asignaciones");
                return;
            } else if (role.equals("ROLE_ASESOR")) {
                response.sendRedirect("/asesor/asesor_registrar_llegada");
                return;
            } else if (role.equals("ROLE_CONDUCTOR")) {
                response.sendRedirect("/conductor/conductor_vehiculo");
                return;
            } else if (role.equals("ROLE_GERENTE")) {
                response.sendRedirect("/gerente/gerente_usuarios");
                return;
            } else if (role.equals("ROLE_CAJERO")) {
                response.sendRedirect("/cajero/cajero_emitir_pagos");
                return;
            } else if (role.equals("ROLE_ALMACEN")) {
                response.sendRedirect("/almacen/almacen_entrada_repuestos");
                return;
            }
        }

        // Si el usuario llega aquí, significa que no se encontró un rol específico para redirigirlo.
        // Esto puede pasar si un usuario tiene un rol que no está listado en los 'if' anteriores.
        // Puedes redirigir a una página de error genérica o a una página por defecto para roles no mapeados.
        System.out.println("DEBUG: No se encontró una redirección específica para el rol del usuario. Redirigiendo a /error o a una página por defecto.");
        response.sendRedirect("/error"); // Puedes cambiar esto a una página más amigable si tienes una
    }
}
