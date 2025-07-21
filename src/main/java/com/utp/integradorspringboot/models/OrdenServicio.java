/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orden_servicio")
public class OrdenServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20)
    private EstadoOrden estado;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @OneToOne(mappedBy = "ordenServicio", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Diagnostico diagnostico;

    // ----- Constructores -----

    public OrdenServicio() {
    }

    public OrdenServicio(Integer id, EstadoOrden estado, Vehiculo vehiculo) {
        this.id = id;
        this.estado = estado;
        this.vehiculo = vehiculo;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
        if (diagnostico != null) {
            diagnostico.setOrdenServicio(this); 
        }
    }



    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrdenServicio)) return false;
        OrdenServicio other = (OrdenServicio) obj;
        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "OrdenServicio{" +
                "id=" + id +
                ", estado=" + (estado != null ? estado.name() : null) +
                ", vehiculo=" + (vehiculo != null ? vehiculo.getPlaca() : null) +
                '}';
    }
}
