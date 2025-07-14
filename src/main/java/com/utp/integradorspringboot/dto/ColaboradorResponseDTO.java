package com.utp.integradorspringboot.dto;

import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.models.Rol; 
import java.util.stream.Collectors; 
public class ColaboradorResponseDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String correo;
    private Boolean estado;
    private Long sedeId;
    private String sedeNombre; 
    private Long rolId;
    private String rolNombre; 

    public ColaboradorResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();
        this.dni = usuario.getDni();
        this.telefono = usuario.getTelefono();
        this.correo = usuario.getCorreo();
        this.estado = usuario.getEstado();

        if (usuario.getSede() != null) {
            this.sedeId = usuario.getSede().getId();
            this.sedeNombre = usuario.getSede().getNombreSede(); 
        }

        if (usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
            this.rolNombre = usuario.getRoles().stream()
                                .map(Rol::getNombre) 
                                .collect(Collectors.joining(", ")); 
            this.rolId = usuario.getRoles().iterator().next().getId();
        }
    }

   
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
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
    public Long getSedeId() { return sedeId; }
    public void setSedeId(Long sedeId) { this.sedeId = sedeId; }
    public String getSedeNombre() { return sedeNombre; }
    public void setSedeNombre(String sedeNombre) { this.sedeNombre = sedeNombre; }
    public Long getRolId() { return rolId; }
    public void setRolId(Long rolId) { this.rolId = rolId; }
    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }
}