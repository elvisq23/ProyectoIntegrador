package com.utp.integradorspringboot.dto;

import com.utp.integradorspringboot.models.SalidaRepuesto;
import java.time.LocalDateTime;

public class SalidaRepuestoResponseDTO {
    private Long id;
    private Long repuestoId;
    private String repuestoNombre;
    private Integer cantidad;
    private LocalDateTime fechaSalida;
    private Long mecanicoId;
    private String mecanicoNombres;
    private String mecanicoApellidos;
    private Long servicioIdAsociado;

    public SalidaRepuestoResponseDTO(SalidaRepuesto salidaRepuesto) {
        this.id = salidaRepuesto.getId();
        this.cantidad = salidaRepuesto.getCantidad();
        this.fechaSalida = salidaRepuesto.getFechaSalida();
        this.servicioIdAsociado = salidaRepuesto.getServicioIdAsociado();
        if (salidaRepuesto.getRepuesto() != null) {
            this.repuestoId = salidaRepuesto.getRepuesto().getId();
            this.repuestoNombre = salidaRepuesto.getRepuesto().getNombre();
        }
        if (salidaRepuesto.getMecanico() != null) {
            this.mecanicoId = salidaRepuesto.getMecanico().getId();
            this.mecanicoNombres = salidaRepuesto.getMecanico().getNombres();
            this.mecanicoApellidos = salidaRepuesto.getMecanico().getApellidos();
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
    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDateTime fechaSalida) { this.fechaSalida = fechaSalida; }
    public Long getMecanicoId() { return mecanicoId; }
    public void setMecanicoId(Long mecanicoId) { this.mecanicoId = mecanicoId; }
    public String getMecanicoNombres() { return mecanicoNombres; }
    public void setMecanicoNombres(String mecanicoNombres) { this.mecanicoNombres = mecanicoNombres; }
    public String getMecanicoApellidos() { return mecanicoApellidos; }
    public void setMecanicoApellidos(String mecanicoApellidos) { this.mecanicoApellidos = mecanicoApellidos; }
    public Long getServicioIdAsociado() { return servicioIdAsociado; }
    public void setServicioIdAsociado(Long servicioIdAsociado) { this.servicioIdAsociado = servicioIdAsociado; }
}