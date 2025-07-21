package com.utp.integradorspringboot.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_servicio_atencion")
public class DetalleServicioAtencion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AtencionVehiculo atencion;

    @ManyToOne
    private Servicio servicio;

    private Integer cantidad;

    private BigDecimal precioUnitario;

    public DetalleServicioAtencion() {
    }

    public DetalleServicioAtencion(Long id, AtencionVehiculo atencion, Servicio servicio, Integer cantidad, BigDecimal precioUnitario) {
        this.id = id;
        this.atencion = atencion;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AtencionVehiculo getAtencion() {
        return atencion;
    }

    public void setAtencion(AtencionVehiculo atencion) {
        this.atencion = atencion;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}

