package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "vehiculos")
public class Vehiculo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private String placa;
    private Integer anio;
    private String color;

    @Column(name = "propietario_nombres")
    private String propietarioNombres;

    @Column(name = "propietario_apellidos")
    private String propietarioApellidos;

    @Column(name = "propietario_dni")
    private String propietarioDni;
    
    // --- CAMPO AÑADIDO ---
    @Column(name = "propietario_ruc")
    private String propietarioRuc; // Para guardar el RUC

    // --- Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getPropietarioNombres() { return propietarioNombres; }
    public void setPropietarioNombres(String propietarioNombres) { this.propietarioNombres = propietarioNombres; }
    public String getPropietarioApellidos() { return propietarioApellidos; }
    public void setPropietarioApellidos(String propietarioApellidos) { this.propietarioApellidos = propietarioApellidos; }
    public String getPropietarioDni() { return propietarioDni; }
    public void setPropietarioDni(String propietarioDni) { this.propietarioDni = propietarioDni; }

    // --- GETTER Y SETTER AÑADIDOS ---
    public String getPropietarioRuc() { return propietarioRuc; }
    public void setPropietarioRuc(String propietarioRuc) { this.propietarioRuc = propietarioRuc; }
}