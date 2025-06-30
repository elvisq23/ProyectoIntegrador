
package com.utp.integradorspringboot.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reservas")
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_sede", nullable = false)
    private Sede sede;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    @Column(nullable = false)
    private Date fechaReserva;

    @Column(nullable = false)
    private Time horaReserva;

    @Column(length = 65535)
    private String comentario;

    // Constructor
    public Reserva() {
    }

    public Reserva(Vehiculo vehiculo, Sede sede, EstadoReserva estado, Date fechaReserva, Time horaReserva, String comentario) {
        this.vehiculo = vehiculo;
        this.sede = sede;
        this.estado = estado;
        this.fechaReserva = fechaReserva;
        this.horaReserva = horaReserva;
        this.comentario = comentario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Time getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(Time horaReserva) {
        this.horaReserva = horaReserva;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reserva)) {
            return false;
        }
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", estado=" + estado +
                ", fechaReserva=" + fechaReserva +
                ", horaReserva=" + horaReserva +
                '}';
    }
}
