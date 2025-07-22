package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.VehiculoRequestDTO;
import com.utp.integradorspringboot.models.Vehiculo;
import com.utp.integradorspringboot.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    // Se elimina la dependencia a ClienteRepository

    @Override
    @Transactional(readOnly = true)
    public List<Vehiculo> getAllVehiculos(String searchQuery) {
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            return vehiculoRepository.findBySearchQuery(searchQuery.toLowerCase());
        }
        return vehiculoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vehiculo> getVehiculoById(Long id) {
        return vehiculoRepository.findById(id);
    }

    @Override
    @Transactional
    public Vehiculo saveVehiculo(VehiculoRequestDTO request) {
        vehiculoRepository.findByPlaca(request.getPlaca()).ifPresent(v -> {
            throw new IllegalArgumentException("Ya existe un vehículo con esta placa: " + request.getPlaca());
        });

        // Ya no se busca un cliente, se asignan los datos directamente.
        Vehiculo nuevoVehiculo = new Vehiculo();
        nuevoVehiculo.setMarca(request.getMarca());
        nuevoVehiculo.setModelo(request.getModelo());
        nuevoVehiculo.setPlaca(request.getPlaca());
        nuevoVehiculo.setAnio(request.getAnio());
        // Se guardan los datos del propietario
        nuevoVehiculo.setPropietarioNombres(request.getPropietarioNombres());
        nuevoVehiculo.setPropietarioApellidos(request.getPropietarioApellidos());
        nuevoVehiculo.setPropietarioDni(request.getPropietarioDni());

        return vehiculoRepository.save(nuevoVehiculo);
    }

    @Override
    @Transactional
    public Vehiculo updateVehiculo(Long id, VehiculoRequestDTO request) {
        Vehiculo existingVehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

        // ... (lógica de validación de placa)

        // Se actualizan los datos del vehículo y del propietario
        existingVehiculo.setMarca(request.getMarca());
        existingVehiculo.setModelo(request.getModelo());
        existingVehiculo.setPlaca(request.getPlaca());
        existingVehiculo.setAnio(request.getAnio());
        existingVehiculo.setPropietarioNombres(request.getPropietarioNombres());
        existingVehiculo.setPropietarioApellidos(request.getPropietarioApellidos());
        existingVehiculo.setPropietarioDni(request.getPropietarioDni());

        return vehiculoRepository.save(existingVehiculo);
    }

    @Override
    @Transactional
    public void deleteVehiculo(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new RuntimeException("Vehículo no encontrado con ID: " + id);
        }
        vehiculoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehiculo> getVehiculosByClienteId(Long clienteId) {
        // Este método ya no tiene sentido, pero para no romper la interfaz lo dejamos vacío
        // Lo ideal sería eliminarlo de la interfaz VehiculoService también.
        return List.of();
    }
}