package com.utp.integradorspringboot.dto;

import com.utp.integradorspringboot.models.EntradaRepuesto;
import java.time.LocalDateTime;

public class EntradaRepuestoResponseDTO {
    private Long id;
    private Long repuestoId;
    private String repuestoNombre;
    private Integer cantidad;
    private LocalDateTime fechaEntrada;
    private String proveedor;

    public EntradaRepuestoResponseDTO(EntradaRepuesto entradaRepuesto) {
        this.id = entradaRepuesto.getId();
        this.cantidad = entradaRepuesto.getCantidad();
        this.fechaEntrada = entradaRepuesto.getFechaEntrada();
        this.proveedor = entradaRepuesto.getProveedor();
        if (entradaRepuesto.getRepuesto() != null) {
            this.repuestoId = entradaRepuesto.getRepuesto().getId();
            this.repuestoNombre = entradaRepuesto.getRepuesto().getNombre();
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRepuestoId() { return repuestoId; }
    public void setRepuestoId(Long repuestoId) { this.repuestoId = repuestoId; }
    public String getRepuestoNombre() { return repuestoNombre; }
    public void setRepuestoNombre(String repuestoNombre) { this.repuestoNombre = repuestoNombre; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public LocalDateTime getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDateTime fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
}