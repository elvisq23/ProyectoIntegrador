package com.utp.integradorspringboot.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "llegadas")
public class Llegada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres", length = 100)
    private String nombres;

    @Column(name = "apellidos", length = 100)
    private String apellidos;

    @Column(name = "marca", length = 50)
    private String marca;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "placa", length = 20)
    private String placa;

    @Column(name = "color", length = 30)
    private String color;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    public Llegada() {
    }

    public Llegada(String nombres, String apellidos, String marca, String modelo, String placa, String color) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.color = color;
        this.fechaHora = LocalDateTime.now();
    }

    public Llegada(Long id, String nombres, String apellidos, String marca, String modelo, String placa, String color, LocalDateTime fechaHora) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.color = color;
        this.fechaHora = fechaHora;
    }

    @PrePersist
    protected void onCreate() {
        this.fechaHora = LocalDateTime.now();
    }

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Llegada)) {
            return false;
        }
        Llegada other = (Llegada) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "Llegada{" +
               "id=" + id +
               ", nombres='" + nombres + '\'' +
               ", apellidos='" + apellidos + '\'' +
               ", marca='" + marca + '\'' +
               ", modelo='" + modelo + '\'' +
               ", placa='" + placa + '\'' +
               ", color='" + color + '\'' +
               ", fechaHora=" + fechaHora +
               '}';
    }
}
