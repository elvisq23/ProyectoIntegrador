package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "repuestos")
public class Repuesto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 65535)
    private String descripcion;

    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precioUnitario;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = true)
    private Proveedor proveedor;

    // Constructores
    public Repuesto() {}

    public Repuesto(String nombre, String descripcion, BigDecimal precioUnitario, Integer stock, Proveedor proveedor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.stock = stock;
        this.proveedor = proveedor;
    }

    // Getters y Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }

    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public Integer getStock() { return stock; }

    public void setStock(Integer stock) { this.stock = stock; }

    public Proveedor getProveedor() { return proveedor; }

    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
}
