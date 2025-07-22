package com.utp.integradorspringboot.dto;

import com.utp.integradorspringboot.models.Cliente;

public class ClienteResponseDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String ruc;

    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nombres = cliente.getNombres();
        this.apellidos = cliente.getApellidos();
        this.dni = cliente.getDni();
        this.telefono = cliente.getTelefono();
        this.ruc = cliente.getRuc();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
}