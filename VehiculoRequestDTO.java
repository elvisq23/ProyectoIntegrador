package com.utp.integradorspringboot.dto;

public class VehiculoRequestDTO {
    private String marca;
    private String modelo;
    private String placa;
    private Integer anio;
    
    // --- CAMPOS AÑADIDOS ---
    // Ahora se reciben los datos del propietario directamente aquí
    private String propietarioNombres;
    private String propietarioApellidos;
    private String propietarioDni;

    // --- Getters y Setters ---
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    
    // Getters y Setters para los nuevos campos
    public String getPropietarioNombres() { return propietarioNombres; }
    public void setPropietarioNombres(String propietarioNombres) { this.propietarioNombres = propietarioNombres; }
    public String getPropietarioApellidos() { return propietarioApellidos; }
    public void setPropietarioApellidos(String propietarioApellidos) { this.propietarioApellidos = propietarioApellidos; }
    public String getPropietarioDni() { return propietarioDni; }
    public void setPropietarioDni(String propietarioDni) { this.propietarioDni = propietarioDni; }
}