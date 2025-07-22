package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Proveedor;
import com.utp.integradorspringboot.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional(readOnly = true)
    public List<Proveedor> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Proveedor> getProveedorById(Long id) {
        return proveedorRepository.findById(id);
    }

    @Transactional
    public Proveedor saveProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Transactional
    public void deleteProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    public long count() {
        return proveedorRepository.count();
    }
}