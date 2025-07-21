package com.utp.integradorspringboot.models;

import com.utp.integradorspringboot.models.enums.EstadoAtencion;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "atenciones")
public class AtencionVehiculo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EstadoAtencion estado;

    private LocalDateTime fechaRegistro;

    @ManyToOne
    private Usuario conductor;

    @ManyToOne
    private Vehiculo vehiculo;

    @ManyToOne
    private Usuario recepcionista;

    @ManyToOne
    private Sede sede;

    public AtencionVehiculo() {
    }

    public AtencionVehiculo(Long id, EstadoAtencion estado, LocalDateTime fechaRegistro, Usuario conductor, Vehiculo vehiculo, Usuario recepcionista, Sede sede) {
        this.id = id;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.conductor = conductor;
        this.vehiculo = vehiculo;
        this.recepcionista = recepcionista;
        this.sede = sede;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoAtencion getEstado() {
        return estado;
    }

    public void setEstado(EstadoAtencion estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Usuario getConductor() {
        return conductor;
    }

    public void setConductor(Usuario conductor) {
        this.conductor = conductor;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Usuario getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(Usuario recepcionista) {
        this.recepcionista = recepcionista;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }
}
