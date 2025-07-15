package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.OrdenServicio;
import java.util.List;
import java.util.Optional;

public interface OrdenServicioService {

    List<OrdenServicio> getAllOrdenesServicio();

    Optional<OrdenServicio> getOrdenServicioById(Long id);

}