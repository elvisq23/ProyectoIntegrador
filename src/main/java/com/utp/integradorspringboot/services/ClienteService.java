package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.ClienteRequestDTO;
import com.utp.integradorspringboot.models.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el servicio de Clientes.
 * Define las operaciones de negocio que se pueden realizar con los clientes.
 */
public interface ClienteService {

    List<Cliente> getAllClientes(String searchQuery);

    Optional<Cliente> getClienteById(Long id);

    Cliente saveCliente(ClienteRequestDTO request);

    Cliente updateCliente(Long id, ClienteRequestDTO request);

    void deleteCliente(Long id);
}