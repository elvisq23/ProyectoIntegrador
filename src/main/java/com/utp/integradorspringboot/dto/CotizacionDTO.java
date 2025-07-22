package com.utp.integradorspringboot.models;

public class CotizacionDTO {
    private Integer id;
    private String cliente;
    private String vehiculo;
    private String estado;

    public CotizacionDTO() {}

    public CotizacionDTO(Integer id, String cliente, String vehiculo, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.estado = estado;
    }

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

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}