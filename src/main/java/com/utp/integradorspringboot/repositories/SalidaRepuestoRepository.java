package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.SalidaRepuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalidaRepuestoRepository extends JpaRepository<SalidaRepuesto, Long> {

    // Puedes añadir métodos de búsqueda personalizados si los necesitas,
    // por ejemplo, para buscar salidas por repuesto, mecánico, o servicio asociado.
    List<SalidaRepuesto> findByRepuesto_Id(Long repuestoId);
    List<SalidaRepuesto> findByMecanico_Id(Long mecanicoId);
    List<SalidaRepuesto> findByServicioIdAsociado(Long servicioId);
}