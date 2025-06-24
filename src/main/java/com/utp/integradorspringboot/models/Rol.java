package com.utp.integradorspringboot.models;

import com.fasterxml.jackson.annotation.JsonIgnore; // <-- ¡Asegúrate de importar esto!
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre; // Ej: ROLE_CONDUCTOR, ROLE_DUEÑO, ROLE_GERENTE

    private String descripcion; // Opcional: Para una descripción más detallada del rol

    // Relación Many-to-Many con Usuario
    // mappedBy indica que la relación es "dueña" por el lado de Usuario
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore // <-- ¡Esta es la línea clave que debes añadir!
    private Set<Usuario> usuarios = new HashSet<>();

    // Constructor por defecto
    public Rol() {
    }

    // Constructor con nombre (útil para inicializar)
    public Rol(String nombre) {
        this.nombre = nombre;
    }

    // Constructor con nombre y descripción
    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rol rol = (Rol) o;
        return nombre.equals(rol.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}