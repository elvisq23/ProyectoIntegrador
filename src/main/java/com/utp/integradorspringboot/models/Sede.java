package com.utp.integradorspringboot.models; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable; 

@Entity 
@Table(name = "sedes") 
public class Sede implements Serializable { 

    private static final long serialVersionUID = 1L; 

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 

    @Column(name = "nombre_sede", length = 50)
    private String nombreSede;

    @Column(name = "direccion", length = 255) 
    private String direccion;

    @Column(name = "telefono_contacto", length = 20) 
    private String telefonoContacto;

    @Column(name = "capacidad_maxima")
    private Integer capacidadMaxima;

    @Column(name = "autos_ocupados") 
    private Integer autosOcupados;

    public Sede() {
    }

    // Constructor existente (para crear nuevas sedes sin ID)
    public Sede(String nombreSede, String direccion, String telefonoContacto, Integer capacidadMaxima, Integer autosOcupados) {
        this.nombreSede = nombreSede;
        this.direccion = direccion;
        this.telefonoContacto = telefonoContacto;
        this.capacidadMaxima = capacidadMaxima;
        this.autosOcupados = autosOcupados;
    }

    // *** NUEVO CONSTRUCTOR PARA PRUEBAS (CON ID) ***
    public Sede(Long id, String nombreSede, String direccion, String telefonoContacto, Integer capacidadMaxima, Integer autosOcupados) {
        this.id = id; // Asigna el ID que se pasa
        this.nombreSede = nombreSede;
        this.direccion = direccion;
        this.telefonoContacto = telefonoContacto;
        this.capacidadMaxima = capacidadMaxima;
        this.autosOcupados = autosOcupados;
    }


    public Long getId() { 
        return id;
    }

    public void setId(Long id) { 
        this.id = id;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Integer getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(Integer capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public Integer getAutosOcupados() {
        return autosOcupados;
    }

    public void setAutosOcupados(Integer autosOcupados) {
        this.autosOcupados = autosOcupados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sede)) {
            return false;
        }
        Sede other = (Sede) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sede{" +
               "id=" + id +
               ", nombreSede='" + nombreSede + '\'' +
               ", direccion='" + direccion + '\'' +
               ", telefonoContacto='" + telefonoContacto + '\'' +
               ", capacidadMaxima=" + capacidadMaxima +
               ", autosOcupados=" + autosOcupados +
               '}';
    }
}
