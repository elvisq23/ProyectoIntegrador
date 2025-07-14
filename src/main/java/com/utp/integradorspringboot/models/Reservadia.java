package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservadia")
public class Reservadia implements Serializable {

    private static final long serialVersionUID = 1L;

    // ─────────────────────────────
    // ID autogenerado
    // ─────────────────────────────
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ─────────────────────────────
    // Campos del formulario
    // ─────────────────────────────
    private String vehiculo;

    private String modelo;

    private String placa;

    private String taller;

    private String estado;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    // ─────────────────────────────
    // Constructores
    // ─────────────────────────────
    public Reservadia() {
    }

    public Reservadia(Integer id, String vehiculo, String modelo, String placa, String taller, String estado, LocalDateTime fechaHora) {
        this.id = id;
        this.vehiculo = vehiculo;
        this.modelo = modelo;
        this.placa = placa;
        this.taller = taller;
        this.estado = estado;
        this.fechaHora = fechaHora;
    }

    // ─────────────────────────────
    // Getters y Setters
    // ─────────────────────────────

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
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

    public String getTaller() {
        return taller;
    }

    public void setTaller(String taller) {
        this.taller = taller;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    // ─────────────────────────────
    // hashCode y equals
    // ─────────────────────────────

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Reservadia)) return false;
        Reservadia other = (Reservadia) obj;
        return this.id != null && this.id.equals(other.id);
    }

    // ─────────────────────────────
    // toString
    // ─────────────────────────────

    @Override
    public String toString() {
        return "Reservadia{" +
                "id=" + id +
                ", vehiculo='" + vehiculo + '\'' +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", taller='" + taller + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
