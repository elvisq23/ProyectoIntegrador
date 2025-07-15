package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.VehiculoRequestDTO;
import com.utp.integradorspringboot.models.Cliente;
import com.utp.integradorspringboot.models.Vehiculo;
import com.utp.integradorspringboot.repositories.VehiculoRepository;
import com.utp.integradorspringboot.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<Vehiculo> getAllVehiculos(String searchQuery) {
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            return vehiculoRepository.findBySearchQuery(searchQuery.toLowerCase());
        }
        return vehiculoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Vehiculo> getVehiculoById(Long id) {
        return vehiculoRepository.findById(id);
    }

    @Transactional
    public Vehiculo saveVehiculo(VehiculoRequestDTO request) {
        if (request.getId() != null && request.getId() != 0) {
            throw new IllegalArgumentException("No se debe proporcionar ID para la creación de un nuevo vehículo.");
        }
        if (vehiculoRepository.findByPlaca(request.getPlaca()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un vehículo con esta placa: " + request.getPlaca());
        }

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + request.getClienteId()));

        Vehiculo nuevoVehiculo = new Vehiculo();
        nuevoVehiculo.setMarca(request.getMarca());
        nuevoVehiculo.setModelo(request.getModelo());
        nuevoVehiculo.setPlaca(request.getPlaca());
        nuevoVehiculo.setCliente(cliente);

        return vehiculoRepository.save(nuevoVehiculo);
    }

    @Transactional
    public Vehiculo updateVehiculo(Long id, VehiculoRequestDTO request) {
        Vehiculo existingVehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

        if (!existingVehiculo.getPlaca().equals(request.getPlaca())) {
            vehiculoRepository.findByPlaca(request.getPlaca())
                    .ifPresent(v -> {
                        if (!v.getId().equals(id)) {
                            throw new IllegalArgumentException("La placa ya está en uso por otro vehículo: " + request.getPlaca());
                        }
                    });
        }

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + request.getClienteId()));

        existingVehiculo.setMarca(request.getMarca());
        existingVehiculo.setModelo(request.getModelo());
        existingVehiculo.setPlaca(request.getPlaca());
        existingVehiculo.setCliente(cliente);

        return vehiculoRepository.save(existingVehiculo);
    }

    @Transactional
    public void deleteVehiculo(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new RuntimeException("Vehículo no encontrado con ID: " + id);
        }
        vehiculoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Vehiculo> getVehiculosByClienteId(Long clienteId) {
        return vehiculoRepository.findByClienteId(clienteId);
    }
}