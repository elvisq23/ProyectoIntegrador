/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();

            if (role.equals("ROLE_DUEÑO")) {
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

        // Redirección por defecto si el rol no coincide
        response.sendRedirect("/PaginaRolNoCoincide");
        
        System.out.println("DEBUG: Entrando a onAuthenticationSuccess");
System.out.println("DEBUG: Roles del usuario autenticado:");

for (GrantedAuthority authority : authorities) {
    System.out.println(" - " + authority.getAuthority());
}
    }
    
    
}
