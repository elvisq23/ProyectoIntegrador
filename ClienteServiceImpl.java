package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.ClienteRequestDTO;
import com.utp.integradorspringboot.models.Cliente;
import com.utp.integradorspringboot.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getAllClientes(String searchQuery) {
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            return clienteRepository.findBySearchQuery(searchQuery.toLowerCase());
        }
        return clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    @Transactional
    public Cliente saveCliente(ClienteRequestDTO request) {
        // Validaciones para DNI y RUC
        clienteRepository.findByDni(request.getDni()).ifPresent(c -> {
            throw new IllegalArgumentException("Ya existe un cliente con este DNI: " + request.getDni());
        });
        if (request.getRuc() != null && !request.getRuc().trim().isEmpty()) {
            clienteRepository.findByRuc(request.getRuc()).ifPresent(c -> {
                throw new IllegalArgumentException("Ya existe un cliente con este RUC: " + request.getRuc());
            });
        }

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombres(request.getNombres());
        nuevoCliente.setApellidos(request.getApellidos());
        nuevoCliente.setDni(request.getDni());
        nuevoCliente.setTelefono(request.getTelefono());
        nuevoCliente.setRuc(request.getRuc());

        return clienteRepository.save(nuevoCliente);
    }

    @Override
    @Transactional
    public Cliente updateCliente(Long id, ClienteRequestDTO request) {
        Cliente existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        // Lógica de validación para DNI y RUC al actualizar
        if (!existingCliente.getDni().equals(request.getDni())) {
            clienteRepository.findByDni(request.getDni()).ifPresent(c -> {
                if (!c.getId().equals(id)) {
                    throw new IllegalArgumentException("El DNI ya está en uso por otro cliente.");
                }
            });
        }
        if (request.getRuc() != null && !request.getRuc().trim().isEmpty() && !request.getRuc().equals(existingCliente.getRuc())) {
             clienteRepository.findByRuc(request.getRuc()).ifPresent(c -> {
                if (!c.getId().equals(id)) {
                    throw new IllegalArgumentException("El RUC ya está en uso por otro cliente.");
                }
            });
        }

        existingCliente.setNombres(request.getNombres());
        existingCliente.setApellidos(request.getApellidos());
        existingCliente.setDni(request.getDni());
        existingCliente.setTelefono(request.getTelefono());
        existingCliente.setRuc(request.getRuc());

        return clienteRepository.save(existingCliente);
    }

    @Override
    @Transactional
    public void deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}