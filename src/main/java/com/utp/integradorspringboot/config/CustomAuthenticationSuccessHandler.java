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

            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin");
                return;
            } else if (role.equals("ROLE_MECANICO")) {
                response.sendRedirect("/asignaciones");
                return;
            } else if (role.equals("ROLE_RECEPCIONISTA")) {
                response.sendRedirect("/recepcion");
                return;
            }
        }

        // Redirección por defecto si el rol no coincide
        response.sendRedirect("/default");
        
        System.out.println("DEBUG: Entrando a onAuthenticationSuccess");
System.out.println("DEBUG: Roles del usuario autenticado:");

for (GrantedAuthority authority : authorities) {
    System.out.println(" - " + authority.getAuthority());
}
    }
    
    
}
