// src/main/java/com/utp/integradorspringboot/dto/ConductorResponseDTO.java
package com.utp.integradorspringboot.dto;

import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.models.Rol;
import java.util.Optional;

public class ConductorResponseDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;       // <--- ¡AGREGAR ESTE!
    private String telefono;  // <--- ¡AGREGAR ESTE!
    private String correo;
    private String rol;
    private String nombreSede;
    private boolean estado;   // <--- ¡AGREGAR ESTE!

    public ConductorResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();
        this.dni = usuario.getDni(); // Asumiendo que Usuario tiene getDni()
        this.telefono = usuario.getTelefono(); // Asumiendo que Usuario tiene getTelefono()
        this.correo = usuario.getCorreo();

        this.rol = usuario.getRoles().stream()
                            .filter(r -> "CONDUCTOR".equals(r.getNombre()))
                            .findFirst()
                            .map(Rol::getNombre)
                            .orElse("N/A");

        this.nombreSede = Optional.ofNullable(usuario.getSede())
                                  .map(sede -> sede.getNombreSede()) // Usa getNombreSede() si es lo que descubriste
                                  .orElse("Sin Sede Asignada");

        this.estado = usuario.getEstado(); // Asumiendo que Usuario tiene isEstado() o getEstado()
    }

    // Getters y Setters (asegúrate de generarlos para dni, telefono y estado también)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getDni() { return dni; } // Getter para DNI
    public void setDni(String dni) { this.dni = dni; }
    public String getTelefono() { return telefono; } // Getter para Teléfono
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getNombreSede() { return nombreSede; }
    public void setNombreSede(String nombreSede) { this.nombreSede = nombreSede; }
    public boolean isEstado() { return estado; } // Getter para estado (si es boolean, suele ser isEstado)
    public void setEstado(boolean estado) { this.estado = estado; }
}