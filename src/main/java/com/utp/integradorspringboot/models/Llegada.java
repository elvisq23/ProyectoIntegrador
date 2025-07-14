/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "llegada")
public class Llegada implements Serializable {

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
    private String cliente;

    private String marca;

    private String modelo;

    private String placa;

    private String color;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    // ─────────────────────────────
    // Constructores
    // ─────────────────────────────
    public Llegada() {
    }

    public Llegada(Integer id, String cliente, String marca, String modelo, String placa, String color, LocalDateTime fechaHora) {
        this.id = id;
        this.cliente = cliente;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.color = color;
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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
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

    // ─────────────────────────────
    // hashCode y equals
    // ─────────────────────────────

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Llegada)) return false;
        Llegada other = (Llegada) obj;
        return this.id != null && this.id.equals(other.id);
    }

    // ─────────────────────────────
    // toString
    // ─────────────────────────────

    @Override
    public String toString() {
        return "Llegada{" +
                "id=" + id +
                ", cliente='" + cliente + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", color='" + color + '\'' +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
