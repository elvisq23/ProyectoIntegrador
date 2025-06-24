package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByDni(String dni);

    // Método para encontrar solo conductores ACTIVOS por el rol "CONDUCTOR"
    List<Usuario> findByRoles_NombreAndEstadoTrue(String roleName);

    // Query para buscar conductores ACTIVOS por rol y palabra clave
    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.nombre = :roleName AND u.estado = TRUE AND " +
           "(LOWER(u.nombres) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR " +
           "LOWER(u.apellidos) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR " +
           "LOWER(u.dni) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR " +
           "LOWER(u.correo) LIKE LOWER(CONCAT('%', :searchQuery, '%')))")
    List<Usuario> findActiveConductoresBySearchQuery(@Param("roleName") String roleName, @Param("searchQuery") String searchQuery);
}