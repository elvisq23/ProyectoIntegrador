package com.utp.integradorspringboot.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_repuesto_atencion")
public class DetalleRepuestoAtencion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AtencionVehiculo atencion;

    @ManyToOne
    private Repuesto repuesto;

    private Integer cantidad;

    private BigDecimal precioUnitario;

    public DetalleRepuestoAtencion() {
    }

    public DetalleRepuestoAtencion(Long id, AtencionVehiculo atencion, Repuesto repuesto, Integer cantidad, BigDecimal precioUnitario) {
        this.id = id;
        this.atencion = atencion;
        this.repuesto = repuesto;
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

    public Repuesto getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(Repuesto repuesto) {
        this.repuesto = repuesto;
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
