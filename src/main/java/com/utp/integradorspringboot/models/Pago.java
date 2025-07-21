package com.utp.integradorspringboot.models;

import com.utp.integradorspringboot.models.enums.MetodoPago;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal montoTotal;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    private LocalDateTime fechaPago;

    @ManyToOne
    private AtencionVehiculo atencion;

    @ManyToOne
    private Usuario cajero;

    public Pago() {
    }

    public Pago(Long id, BigDecimal montoTotal, MetodoPago metodoPago, LocalDateTime fechaPago, AtencionVehiculo atencion, Usuario cajero) {
        this.id = id;
        this.montoTotal = montoTotal;
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
        this.atencion = atencion;
        this.cajero = cajero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public AtencionVehiculo getAtencion() {
        return atencion;
    }

    public void setAtencion(AtencionVehiculo atencion) {
        this.atencion = atencion;
    }

    public Usuario getCajero() {
        return cajero;
    }

    public void setCajero(Usuario cajero) {
        this.cajero = cajero;
    }
}
