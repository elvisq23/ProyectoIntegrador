package com.utp.integradorspringboot.dto;

import com.utp.integradorspringboot.models.Cliente;
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
    private String clienteRuc; // Añadido para el PDF
    private String vehiculoMarca;
    private String vehiculoModelo;
    private String vehiculoPlaca;
    private Double montoTotal;
    private String estadoPago;
    private Date fechaCreacion;
    private List<DetalleOrdenDTO> detalles;

    // Clase interna para los detalles
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

    // Constructor corregido y más seguro
    public OrdenServicioResponseDTO(OrdenServicio orden) {
        this.id = orden.getId();
        this.montoTotal = orden.getMontoTotal();
        this.estadoPago = orden.getEstadoPago();
        this.fechaCreacion = orden.getFechaCreacion();

        // --- VERIFICACIÓN DE CLIENTE ---
        Cliente cliente = orden.getCliente();
        if (cliente != null) {
            this.clienteNombres = cliente.getNombres();
            this.clienteApellidos = cliente.getApellidos();
            this.clienteRuc = cliente.getRuc();
        } else {
            this.clienteNombres = "Cliente no encontrado";
            this.clienteApellidos = "";
            this.clienteRuc = "N/A";
        }

        // --- VERIFICACIÓN DE VEHÍCULO ---
        Vehiculo vehiculo = orden.getVehiculo();
        if (vehiculo != null) {
            this.vehiculoMarca = vehiculo.getMarca();
            this.vehiculoModelo = vehiculo.getModelo();
            this.vehiculoPlaca = vehiculo.getPlaca();
        } else {
            this.vehiculoMarca = "Vehículo no encontrado";
            this.vehiculoModelo = "";
            this.vehiculoPlaca = "N/A";
        }

        // --- VERIFICACIÓN DE DETALLES ---
        if (orden.getDetalles() != null) {
            this.detalles = orden.getDetalles().stream()
                    .map(DetalleOrdenDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.detalles = Collections.emptyList();
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getClienteNombres() { return clienteNombres; }
    public String getClienteApellidos() { return clienteApellidos; }
    public String getClienteRuc() { return clienteRuc; }
    public String getVehiculoMarca() { return vehiculoMarca; }
    public String getVehiculoModelo() { return vehiculoModelo; }
    public String getVehiculoPlaca() { return vehiculoPlaca; }
    public Double getMontoTotal() { return montoTotal; }
    public String getEstadoPago() { return estadoPago; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public List<DetalleOrdenDTO> getDetalles() { return detalles; }
}