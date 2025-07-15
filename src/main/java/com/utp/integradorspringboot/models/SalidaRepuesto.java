package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "salidas_repuestos")
public class SalidaRepuesto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Relación con Repuesto
    @JoinColumn(name = "repuesto_id", nullable = false)
    private Repuesto repuesto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDateTime fechaSalida;

    @ManyToOne(fetch = FetchType.LAZY) // Relación con el Mecánico (Usuario con rol CONDUCTOR o similar)
    @JoinColumn(name = "mecanico_id", nullable = true) // Podría ser null si el servicio lo consume
    private Usuario mecanico; // El mecánico que solicita el repuesto

    // Para asociar con un servicio específico, si tienes una entidad Servicio o un ID de servicio
    // Por ahora, asumimos que 'servicioId' es el ID de un servicio externo si no hay una entidad Servicio detallada.
    // Si tu servicio es la entidad 'Servicio' que definimos antes, entonces aquí iría:
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "servicio_asociado_id", nullable = true)
    // private Servicio servicioAsociado;
    // Por simplicidad y basándonos en tu descripción "el ID del servicio el cual utilizará los repuestos",
    // lo manejaremos como un String o Long simple por ahora, o si la entidad Servicio ya está creada, la usamos.
    // Dado que el usuario pidió "Gestión de servicios (Todo eso es Almacen)" y aún no la hemos creado, usaremos un Long.
    @Column(name = "servicio_id_asociado")
    private Long servicioIdAsociado; // ID del servicio asociado (e.g., Orden de trabajo, o id de la entidad Servicio si ya existe)


    // Constructor por defecto
    public SalidaRepuesto() {
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

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Usuario getMecanico() {
        return mecanico;
    }

    public void setMecanico(Usuario mecanico) {
        this.mecanico = mecanico;
    }

    public Long getServicioIdAsociado() {
        return servicioIdAsociado;
    }

    public void setServicioIdAsociado(Long servicioIdAsociado) {
        this.servicioIdAsociado = servicioIdAsociado;
    }

    @Override
    public String toString() {
        return "SalidaRepuesto{" +
               "id=" + id +
               ", repuesto=" + (repuesto != null ? repuesto.getNombre() : "null") +
               ", cantidad=" + cantidad +
               ", fechaSalida=" + fechaSalida +
               ", mecanico=" + (mecanico != null ? mecanico.getNombres() : "null") +
               ", servicioIdAsociado=" + servicioIdAsociado +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalidaRepuesto that = (SalidaRepuesto) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}