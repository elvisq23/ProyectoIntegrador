package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.OrdenRequestDTO;
import com.utp.integradorspringboot.models.*;
import com.utp.integradorspringboot.repositories.OrdenServicioRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import com.utp.integradorspringboot.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdenServicioServiceImpl implements OrdenServicioService {

    @Autowired
    private OrdenServicioRepository ordenServicioRepository;
    
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrdenServicio> getAllOrdenesServicio() {
        return ordenServicioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenServicio> getOrdenServicioById(Long id) {
        return ordenServicioRepository.findById(id);
    }

    @Override
    @Transactional
    public OrdenServicio crearOrdenServicio(OrdenRequestDTO requestDTO) {
        
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(requestDTO.getVehiculo().getPlaca()).orElseGet(Vehiculo::new);

        vehiculo.setPlaca(requestDTO.getVehiculo().getPlaca());
        vehiculo.setMarca(requestDTO.getVehiculo().getMarca());
        vehiculo.setModelo(requestDTO.getVehiculo().getModelo());
        vehiculo.setPropietarioNombres(requestDTO.getCliente().getNombres());
        vehiculo.setPropietarioApellidos(requestDTO.getCliente().getApellidos());
        vehiculo.setPropietarioDni(requestDTO.getCliente().getDni());
        vehiculo.setPropietarioRuc(requestDTO.getCliente().getRuc()); // --- LÍNEA AÑADIDA ---
        
        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);

        Usuario usuario = usuarioRepository.findById(requestDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
                
        OrdenServicio nuevaOrden = new OrdenServicio();
        nuevaOrden.setVehiculo(vehiculoGuardado);
        nuevaOrden.setUsuario(usuario);
        nuevaOrden.setMontoTotal(requestDTO.getMontoTotal());
        nuevaOrden.setEstado(requestDTO.getEstado()); // --- LÍNEA AÑADIDA ---

        List<DetalleOrden> detalles = requestDTO.getDetalles().stream().map(dto -> {
            DetalleOrden detalle = new DetalleOrden();
            detalle.setDescripcion(dto.getDescripcion());
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());
            detalle.setOrdenServicio(nuevaOrden);
            return detalle;
        }).collect(Collectors.toList());
        nuevaOrden.setDetalles(detalles);

        return ordenServicioRepository.save(nuevaOrden);
    }
}