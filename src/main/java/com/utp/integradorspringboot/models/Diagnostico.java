/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "diagnostico")
public class Diagnostico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1000)
    private String descripcion;

    @OneToOne
    @JoinColumn(name = "orden_servicio_id", unique = true)
    @JsonBackReference
    private OrdenServicio ordenServicio;

    // ----- Constructores -----

    public Diagnostico() {
    }

    public Diagnostico(Integer id, String descripcion, OrdenServicio ordenServicio) {
        this.id = id;
        this.descripcion = descripcion;
        this.ordenServicio = ordenServicio;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public OrdenServicio getOrdenServicio() {
        return ordenServicio;
    }

    public void setOrdenServicio(OrdenServicio ordenServicio) {
        this.ordenServicio = ordenServicio;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Diagnostico)) return false;
        Diagnostico other = (Diagnostico) obj;
        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Diagnostico{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", ordenServicio=" + (ordenServicio != null ? ordenServicio.getId() : null) +
                '}';
    }
}
