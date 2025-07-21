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
    private String descripcionFallas;

    @Column(length = 500)
    private String serviciosRecomendados;

    @OneToOne
    @JoinColumn(name = "atencion_id")
    private AtencionVehiculo atencion;

    @ManyToOne
    private Usuario mecanico;

    public Diagnostico() {
    }

    public Diagnostico(Integer id, String descripcionFallas, String serviciosRecomendados, AtencionVehiculo atencion, Usuario mecanico) {
        this.id = id;
        this.descripcionFallas = descripcionFallas;
        this.serviciosRecomendados = serviciosRecomendados;
        this.atencion = atencion;
        this.mecanico = mecanico;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcionFallas() {
        return descripcionFallas;
    }

    public void setDescripcionFallas(String descripcionFallas) {
        this.descripcionFallas = descripcionFallas;
    }

    public String getServiciosRecomendados() {
        return serviciosRecomendados;
    }

    public void setServiciosRecomendados(String serviciosRecomendados) {
        this.serviciosRecomendados = serviciosRecomendados;
    }

    public AtencionVehiculo getAtencion() {
        return atencion;
    }

    public void setAtencion(AtencionVehiculo atencion) {
        this.atencion = atencion;
    }

    public Usuario getMecanico() {
        return mecanico;
    }

    public void setMecanico(Usuario mecanico) {
        this.mecanico = mecanico;
    }
}
