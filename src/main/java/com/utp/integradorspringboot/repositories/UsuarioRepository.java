package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByDni(String dni);

    // **¡AÑADE ESTA LÍNEA AQUÍ!**
    Optional<Usuario> findByCodigoVerificacion(String codigoVerificacion); // <-- ¡Esta es la que faltaba!

    List<Usuario> findByRoles_NombreAndEstadoTrue(String roleName);

    @Query("SELECT DISTINCT u FROM Usuario u JOIN FETCH u.roles r LEFT JOIN FETCH u.sede s WHERE r.nombre = :roleName AND u.estado = true AND " +
            "(:searchQuery IS NULL OR LOWER(u.nombres) LIKE %:searchQuery% OR " +
            "LOWER(u.apellidos) LIKE %:searchQuery% OR " +
            "LOWER(u.dni) LIKE %:searchQuery% OR " +
            "LOWER(u.correo) LIKE %:searchQuery%)")
    List<Usuario> findActiveConductoresBySearchQuery(@Param("roleName") String roleName, @Param("searchQuery") String searchQuery);

    @Query("SELECT DISTINCT u FROM Usuario u JOIN FETCH u.sede s JOIN u.roles r WHERE r.nombre IN :roleNames AND u.estado = true")
    List<Usuario> findByRoles_NombreInAndEstadoTrue(@Param("roleNames") List<String> roleNames);

    @Query("SELECT DISTINCT u FROM Usuario u JOIN FETCH u.sede s JOIN u.roles r WHERE r.nombre IN :roleNames AND u.estado = true AND " +
            "(:searchQuery IS NULL OR LOWER(u.nombres) LIKE %:searchQuery% OR " +
            "LOWER(u.apellidos) LIKE %:searchQuery% OR " +
            "LOWER(u.dni) LIKE %:searchQuery% OR " +
            "LOWER(u.correo) LIKE %:searchQuery% OR " +
            "LOWER(s.nombreSede) LIKE %:searchQuery%)")
    List<Usuario> findActiveColaboradoresBySearchQuery(@Param("roleNames") List<String> roleNames, @Param("searchQuery") String searchQuery);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.sede s JOIN u.roles r WHERE u.id = :id AND r.nombre IN :roleNames")
    Optional<Usuario> findByIdAndRoles_NombreIn(@Param("id") Integer id, @Param("roleNames") List<String> roleNames);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.sede s LEFT JOIN FETCH u.roles r WHERE u.id = :id")
    Optional<Usuario> findByIdWithDetails(@Param("id") Integer id);

    Optional<Usuario> findByCorreoIgnoreCase(String correo);
}