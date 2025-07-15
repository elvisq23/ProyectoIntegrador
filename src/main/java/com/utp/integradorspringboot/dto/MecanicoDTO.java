package com.utp.integradorspringboot.dto;

import com.utp.integradorspringboot.models.Usuario;

public class MecanicoDTO {
    private Long id;
    private String nombres;
    private String apellidos;

    // Constructor que convierte un Usuario a este DTO simple
    public MecanicoDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }
}