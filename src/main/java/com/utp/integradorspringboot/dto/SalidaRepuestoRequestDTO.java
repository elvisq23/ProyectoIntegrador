package com.utp.integradorspringboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class SalidaRepuestoRequestDTO {
    private Long id; // Para posibles actualizaciones, aunque menos común en salidas de stock

    @NotNull(message = "El ID del repuesto es obligatorio")
    private Long repuestoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotNull(message = "El ID del mecánico es obligatorio")
    private Long mecanicoId;

    @NotNull(message = "El ID del servicio asociado es obligatorio")
    private Long servicioIdAsociado; // ID del servicio (orden de trabajo, etc.)

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRepuestoId() {
        return repuestoId;
    }

    public void setRepuestoId(Long repuestoId) {
        this.repuestoId = repuestoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getMecanicoId() {
        return mecanicoId;
    }

    public void setMecanicoId(Long mecanicoId) {
        this.mecanicoId = mecanicoId;
    }

    public Long getServicioIdAsociado() {
        return servicioIdAsociado;
    }

    public void setServicioIdAsociado(Long servicioIdAsociado) {
        this.servicioIdAsociado = servicioIdAsociado;
    }
}