package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Vehiculo;
import com.utp.integradorspringboot.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Transactional(readOnly = true)
    public List<Vehiculo> getAllVehiculos() {
        return vehiculoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Vehiculo> getVehiculoById(Long id) {
        return vehiculoRepository.findById(id);
    }

    @Transactional
    public Vehiculo createVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    @Transactional
    public Vehiculo updateVehiculo(Long id, Vehiculo vehiculo) {
        Vehiculo existingVehiculo = getVehiculoById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        existingVehiculo.setPlaca(vehiculo.getPlaca());
        existingVehiculo.setMarca(vehiculo.getMarca());
        existingVehiculo.setModelo(vehiculo.getModelo());
        existingVehiculo.setAnio(vehiculo.getAnio());
        existingVehiculo.setColor(vehiculo.getColor());

        return vehiculoRepository.save(existingVehiculo);
    }

    @Transactional
    public void deleteVehiculo(Long id) {
        vehiculoRepository.deleteById(id);
    }
}
