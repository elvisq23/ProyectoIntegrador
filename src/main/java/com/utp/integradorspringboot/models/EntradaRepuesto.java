package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "entradas_repuestos")
public class EntradaRepuesto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Relación con Repuesto
    @JoinColumn(name = "repuesto_id", nullable = false)
    private Repuesto repuesto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDateTime fechaEntrada;

    @Column(name = "proveedor", length = 100)
    private String proveedor; // Podría ser una entidad Proveedor si es más complejo

    // Constructor por defecto
    public EntradaRepuesto() {
    }

    // Constructor para creación
    public EntradaRepuesto(Repuesto repuesto, Integer cantidad, LocalDateTime fechaEntrada, String proveedor) {
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.fechaEntrada = fechaEntrada;
        this.proveedor = proveedor;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public String toString() {
        return "EntradaRepuesto{" +
               "id=" + id +
               ", repuesto=" + (repuesto != null ? repuesto.getNombre() : "null") +
               ", cantidad=" + cantidad +
               ", fechaEntrada=" + fechaEntrada +
               ", proveedor='" + proveedor + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntradaRepuesto that = (EntradaRepuesto) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}