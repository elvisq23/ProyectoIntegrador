package com.utp.integradorspringboot.dto;

import com.utp.integradorspringboot.models.Vehiculo;

public class VehiculoResponseDTO {
    private Long id;
    private String marca;
    private String modelo;
    private String placa;
    private Long clienteId;
    private String clienteNombres;
    private String clienteApellidos;
    private String clienteDni;

    public VehiculoResponseDTO(Vehiculo vehiculo) {
        this.id = vehiculo.getId();
        this.marca = vehiculo.getMarca();
        this.modelo = vehiculo.getModelo();
        this.placa = vehiculo.getPlaca();
        if (vehiculo.getCliente() != null) {
            this.clienteId = vehiculo.getCliente().getId();
            this.clienteNombres = vehiculo.getCliente().getNombres();
            this.clienteApellidos = vehiculo.getCliente().getApellidos();
            this.clienteDni = vehiculo.getCliente().getDni();
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getClienteNombres() { return clienteNombres; }
    public void setClienteNombres(String clienteNombres) { this.clienteNombres = clienteNombres; }
    public String getClienteApellidos() { return clienteApellidos; }
    public void setClienteApellidos(String clienteApellidos) { this.clienteApellidos = clienteApellidos; }
    public String getClienteDni() { return clienteDni; }
    public void setClienteDni(String clienteDni) { this.clienteDni = clienteDni; }
}