package com.utp.integradorspringboot.dto;

import java.util.List;

public class OrdenRequestDTO {

    private ClienteDTO cliente;
    private VehiculoDTO vehiculo;
    private Long usuarioId;
    private Double montoTotal;
    private List<DetalleRequestDTO> detalles;
    private String estado; // --- CAMPO AÑADIDO ---

    // --- Subclase para los datos del Cliente (tu código ya tenía ruc) ---
    public static class ClienteDTO {
        private String nombres;
        private String apellidos;
        private String dni;
        private String ruc;
        private String telefono;

        // Getters y Setters
        public String getNombres() { return nombres; }
        public void setNombres(String nombres) { this.nombres = nombres; }
        public String getApellidos() { return apellidos; }
        public void setApellidos(String apellidos) { this.apellidos = apellidos; }
        public String getDni() { return dni; }
        public void setDni(String dni) { this.dni = dni; }
        public String getRuc() { return ruc; }
        public void setRuc(String ruc) { this.ruc = ruc; }
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
    }

    // --- Subclase para los datos del Vehículo ---
    public static class VehiculoDTO {
        private String placa;
        private String marca;
        private String modelo;
        private Integer anio;

        // Getters y Setters
        public String getPlaca() { return placa; }
        public void setPlaca(String placa) { this.placa = placa; }
        public String getMarca() { return marca; }
        public void setMarca(String marca) { this.marca = marca; }
        public String getModelo() { return modelo; }
        public void setModelo(String modelo) { this.modelo = modelo; }
        public Integer getAnio() { return anio; }
        public void setAnio(Integer anio) { this.anio = anio; }
    }
    
    // --- Subclase para los detalles ---
    public static class DetalleRequestDTO {
        private String descripcion;
        private Integer cantidad;
        private Double precioUnitario;
        // Getters y Setters...
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
        public Double getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    }
    
    // --- Getters y Setters principales ---
    public ClienteDTO getCliente() { return cliente; }
    public void setCliente(ClienteDTO cliente) { this.cliente = cliente; }
    public VehiculoDTO getVehiculo() { return vehiculo; }
    public void setVehiculo(VehiculoDTO vehiculo) { this.vehiculo = vehiculo; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }
    public List<DetalleRequestDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleRequestDTO> detalles) { this.detalles = detalles; }

    // --- GETTER Y SETTER AÑADIDOS ---
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}