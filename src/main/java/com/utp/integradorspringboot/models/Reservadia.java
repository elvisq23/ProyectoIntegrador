package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reservas_dia")
public class Reservadia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nombres;

    @Column(length = 100)
    private String apellidos;

    @Column(length = 50)
    private String marca;

    @Column(length = 50)
    private String modelo;

    @Column(length = 20)
    private String placa;

    @Column(length = 100)
    private String sede;

    @Column(name = "llego")
    private Boolean llego;

    public Reservadia() {
    }

    public Reservadia(String nombres, String apellidos, String marca, String modelo, String placa, String sede, Boolean llego) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.sede = sede;
        this.llego = llego;
    }

    public Reservadia(Long id, String nombres, String apellidos, String marca, String modelo, String placa, String sede, Boolean llego) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.sede = sede;
        this.llego = llego;
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

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public Boolean getLlego() {
        return llego;
    }

    public void setLlego(Boolean llego) {
        this.llego = llego;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Reservadia)) {
            return false;
        }
        Reservadia other = (Reservadia) obj;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "Reservadia{" +
               "id=" + id +
               ", nombres='" + nombres + '\'' +
               ", apellidos='" + apellidos + '\'' +
               ", marca='" + marca + '\'' +
               ", modelo='" + modelo + '\'' +
               ", placa='" + placa + '\'' +
               ", sede='" + sede + '\'' +
               ", llego=" + llego +
               '}';
    }
}
