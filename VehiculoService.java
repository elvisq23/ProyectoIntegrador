package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.VehiculoRequestDTO;
import com.utp.integradorspringboot.models.Vehiculo;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el servicio de Vehículos.
 * Define las operaciones de negocio que se pueden realizar.
 */
public interface VehiculoService {

    List<Vehiculo> getAllVehiculos(String searchQuery);

    Optional<Vehiculo> getVehiculoById(Long id);

    Vehiculo saveVehiculo(VehiculoRequestDTO request);

    Vehiculo updateVehiculo(Long id, VehiculoRequestDTO request);

    void deleteVehiculo(Long id);

    List<Vehiculo> getVehiculosByClienteId(Long clienteId);
}