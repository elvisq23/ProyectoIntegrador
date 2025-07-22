package com.utp.integradorspringboot.dto;

import com.utp.integradorspringboot.models.Vehiculo;

public class VehiculoResponseDTO {
    private Long id;
    private String marca;
    private String modelo;
    private String placa;
    private Integer anio;
    private String propietarioNombreCompleto; // <- CAMBIO: Un solo campo para el nombre

    public VehiculoResponseDTO(Vehiculo vehiculo) {
        this.id = vehiculo.getId();
        this.marca = vehiculo.getMarca();
        this.modelo = vehiculo.getModelo();
        this.placa = vehiculo.getPlaca();
        this.anio = vehiculo.getAnio();
        
        // Se combinan los nombres del propietario desde la entidad Vehiculo
        if (vehiculo.getPropietarioNombres() != null && vehiculo.getPropietarioApellidos() != null) {
            this.propietarioNombreCompleto = vehiculo.getPropietarioNombres() + " " + vehiculo.getPropietarioApellidos();
        }
    }
    
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
    public String getPropietarioNombreCompleto() { return propietarioNombreCompleto; }
    public void setPropietarioNombreCompleto(String nombre) { this.propietarioNombreCompleto = nombre; }
}