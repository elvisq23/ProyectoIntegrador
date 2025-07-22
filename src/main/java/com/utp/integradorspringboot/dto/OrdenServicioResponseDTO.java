package com.utp.integradorspringboot.dto;

import com.utp.integradorspringboot.models.DetalleOrden;
import com.utp.integradorspringboot.models.OrdenServicio;
import com.utp.integradorspringboot.models.Vehiculo;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrdenServicioResponseDTO {

    private Long id;
    private String clienteNombres;
    private String clienteApellidos;
    private String vehiculoMarca;
    private String vehiculoModelo;
    private String vehiculoPlaca;
    private Double montoTotal;
    private Date fechaCreacion;
    private String estadoPago; // <-- CAMPO AÑADIDO
    private List<DetalleOrdenDTO> detalles;

    // Clase interna para los detalles (sin cambios)
    public static class DetalleOrdenDTO {
        private int cantidad;
        private String descripcion;
        private double precioUnitario;

        public DetalleOrdenDTO(DetalleOrden detalle) {
            this.cantidad = detalle.getCantidad();
            this.descripcion = detalle.getDescripcion();
            this.precioUnitario = detalle.getPrecioUnitario();
        }
        
        // Getters
        public int getCantidad() { return cantidad; }
        public String getDescripcion() { return descripcion; }
        public double getPrecioUnitario() { return precioUnitario; }
    }

    public OrdenServicioResponseDTO(OrdenServicio orden) {
        this.id = orden.getId();
        this.montoTotal = orden.getMontoTotal();
        this.fechaCreacion = orden.getFechaCreacion();
        this.estadoPago = orden.getEstadoPago(); // <-- SE ASIGNA EL VALOR

        Vehiculo vehiculo = orden.getVehiculo();
        if (vehiculo != null) {
            this.clienteNombres = vehiculo.getPropietarioNombres();
            this.clienteApellidos = vehiculo.getPropietarioApellidos();
            this.vehiculoMarca = vehiculo.getMarca();
            this.vehiculoModelo = vehiculo.getModelo();
            this.vehiculoPlaca = vehiculo.getPlaca();
        }

        if (orden.getDetalles() != null) {
            this.detalles = orden.getDetalles().stream()
                    .map(DetalleOrdenDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.detalles = Collections.emptyList();
        }
    }

    // --- Getters (con el nuevo campo) ---
    public Long getId() { return id; }
    public String getClienteNombres() { return clienteNombres; }
    public String getClienteApellidos() { return clienteApellidos; }
    public String getVehiculoMarca() { return vehiculoMarca; }
    public String getVehiculoModelo() { return vehiculoModelo; }
    public String getVehiculoPlaca() { return vehiculoPlaca; }
    public Double getMontoTotal() { return montoTotal; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public String getEstadoPago() { return estadoPago; } // <-- GETTER AÑADIDO
    public List<DetalleOrdenDTO> getDetalles() { return detalles; }
}