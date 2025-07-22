package com.utp.integradorspringboot.models;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles_orden")
public class DetalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cantidad;
    private String descripcion;
    @Column(name = "precio_unitario")
    private Double precioUnitario;
    @Column(name = "orden_servicio_id")
    private Integer ordenServicioId;

    // getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    public Integer getOrdenServicioId() { return ordenServicioId; }
    public void setOrdenServicioId(Integer ordenServicioId) { this.ordenServicioId = ordenServicioId; }
} 