package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.repositories.ProveedorRepository;
import com.utp.integradorspringboot.repositories.SedeRepository;
import com.utp.integradorspringboot.repositories.ServicioRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SedeRepository sedeRepository;
    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;

    public long contarColaboradores() {
        return usuarioRepository.findAllColaboradoresActivosExcluyendoDuenoYConductor().size();
    }

    public long contarConductores() {
        return usuarioRepository.findAllConductoresActivos().size();
    }

    public long contarSedes() {
        return sedeRepository.count();
    }

    public long contarServicios() {
        return servicioRepository.count();
    }

    public long contarProveedores() {
        return proveedorRepository.count();
    }
}
