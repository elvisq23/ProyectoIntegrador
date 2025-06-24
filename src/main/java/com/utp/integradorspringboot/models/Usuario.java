package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set; // Importar Set y HashSet

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String ruc;
    private String correo;

    @Column(name = "contrasenia")
    private String contrasenia;

    private LocalDateTime fechaRegistro;
    private Boolean estado;
    private String codigoVerificacion;
    private LocalDateTime fechaExpiracionCodigo;

    // --- CAMBIOS PARA ROLES ---
    // Eliminar: private String rol;

    // Nueva relación Many-to-Many con la entidad Rol
    @ManyToMany(fetch = FetchType.EAGER) // Carga los roles al cargar el usuario
    @JoinTable(
        name = "usuario_rol", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "usuario_id"), // Columna FK a 'usuarios'
        inverseJoinColumns = @JoinColumn(name = "rol_id") // Columna FK a 'roles'
    )
    private Set<Rol> roles = new HashSet<>(); // Un Set para almacenar los roles del usuario
    // --- FIN CAMBIOS PARA ROLES ---

    // --- Constructor por defecto ---
    public Usuario() {
    }

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    public LocalDateTime getFechaExpiracionCodigo() {
        return fechaExpiracionCodigo;
    }

    public void setFechaExpiracionCodigo(LocalDateTime fechaExpiracionCodigo) {
        this.fechaExpiracionCodigo = fechaExpiracionCodigo;
    }

    // --- Nuevos Getters y Setters para roles ---
    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    // Método de utilidad para añadir un rol (opcional pero útil)
    public void addRol(Rol rol) {
        this.roles.add(rol);
        rol.getUsuarios().add(this);
    }

    // Método de utilidad para remover un rol (opcional pero útil)
    public void removeRol(Rol rol) {
        this.roles.remove(rol);
        rol.getUsuarios().remove(this);
    }
}