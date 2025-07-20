package com.utp.integradorspringboot;

import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.models.Sede;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.RolRepository;
import com.utp.integradorspringboot.repositories.SedeRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private SedeRepository sedeRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public CommandLineRunner seedDatabase() {
        return args -> {
            seedRoles();
            seedSedes();
            seedUsuariosYRoles();
        };
    }

    private void seedRoles() {
        if (rolRepository.count() == 0) {
            rolRepository.saveAll(List.of(
                    new Rol("DUEÑO", "Rol para dueños de talleres"),
                    new Rol("GERENTE", "Rol para gerentes de sucursal"),
                    new Rol("ENCARGADO", "Rol para encargados de servicio"),
                    new Rol("RECEPCION", "Rol para recepcionistas de servicio"),
                    new Rol("MECANICO", "Rol para mecánicos de taller"),
                    new Rol("ALMACEN", "Rol para personal de almacén"),
                    new Rol("CAJERO", "Rol para personal de caja"),
                    new Rol("CONDUCTOR", "Rol para conductores de vehículos")
            ));
        }
    }

    private void seedSedes() {
        if (sedeRepository.count() == 0) {
            sedeRepository.saveAll(List.of(
                    new Sede("Sede Central Lima","Av. Javier Prado Este 123", "987654321", 100, 0),
                    new Sede("Sede Norte Trujillo","Calle Los Incas 456", "912345678", 80, 0),
                    new Sede("Sede Sur Arequipa","Av. Ejército 789", "998877665", 120, 0)
            ));
        }
    }

    private void seedUsuariosYRoles() {
        if (usuarioRepository.count() == 0) {
            Sede sedeArequipa = sedeRepository.findByNombreSede("Sede Sur Arequipa").orElseThrow();

            Rol rolDueno = rolRepository.findByNombre("DUEÑO").orElseThrow();
            Rol rolGerente = rolRepository.findByNombre("GERENTE").orElseThrow();
            Rol rolEncargado = rolRepository.findByNombre("ENCARGADO").orElseThrow();
            Rol rolRecepcion = rolRepository.findByNombre("RECEPCION").orElseThrow();
            Rol rolMecanico = rolRepository.findByNombre("MECANICO").orElseThrow();
            Rol rolAlmacen = rolRepository.findByNombre("ALMACEN").orElseThrow();
            Rol rolCajero = rolRepository.findByNombre("CAJERO").orElseThrow();
            Rol rolConductor = rolRepository.findByNombre("CONDUCTOR").orElseThrow();

            LocalDateTime now = LocalDateTime.now();

            Usuario dueno = new Usuario("Elvis", "Quilla Lipa", "11111111", "900111111", "", "dueno@metalcars.com", "$2a$12$gRpbwFfmJKV0z8e5rjHQ4uHfW4Fe84O.YpdvfmHhGFh1D7KVB7B5G", now, true, sedeArequipa);
            Usuario gerente = new Usuario("Andre", "Perez Gonzales", "22222222", "900222222", "", "gerente@metalcars.com", "$2a$12$WI2IewOnPU5TqLIPMFKAzepB7QptAbHrIbkEO6/fLOdJw57DIgRs2", now, true, sedeArequipa);
            Usuario encargado = new Usuario("Jean", "Castillo Cotrina", "33333333", "900333333", "", "encargado@metalcars.com", "$2a$10$zGUUGSQNCMdtwlz7NUzkae5.nsFd95s4uOR62Mgb/kQzrO97NToI6", now, true, sedeArequipa);
            Usuario recepcion = new Usuario("Lucia", "Vargas Sánchez", "44444444", "900444444", "", "recepcion@metalcars.com", "$2a$10$lxCT4uwXrj865x59u.4Q2OVsBJPOMFdVuHbKQeioYgxnr7HpUH0fK", now, true, sedeArequipa);
            Usuario mecanico = new Usuario("Roy", "Quispe Inoñan", "55555555", "900555555", "", "mecanico@metalcars.com", "$2a$12$KClwsXKwjyiN4.0/8XpmxOWhEj9yDZEO6QESxQMPhUzVv6oWTg1gu", now, true, sedeArequipa);
            Usuario almacen = new Usuario("Jeremy", "Ochoa Rivadeneyra", "66666666", "900666666", "", "almacen@metalcars.com", "$2a$12$rbAUTOt95J2DBzKutSG10uVVehzl0JINYher.e0.gIslm7OV/yLj6", now, true, sedeArequipa);
            Usuario cajero = new Usuario("Jeremy", "Ochoa Rivadeneyra", "77777777", "900777777", "", "cajero@metalcars.com", "$2a$12$u3DXjW.qUL8qi3qpggaca.8KTlY70hxn8WMQyrmrrEfAFdPkoi21q", now, true, sedeArequipa);
            Usuario conductor = new Usuario("Josue", "Rivera Ambrosio", "88888888", "900888888", "", "conductor@metalcars.com", "$2a$12$W.fxa/QqSoC0O9uSAR6ImO6X3c.cyVcJacw5OaIZOfXxYLrm2I.e6", now, true, sedeArequipa);

            dueno.addRol(rolDueno);
            gerente.addRol(rolGerente);
            encargado.addRol(rolEncargado);
            recepcion.addRol(rolRecepcion);
            mecanico.addRol(rolMecanico);
            almacen.addRol(rolAlmacen);
            cajero.addRol(rolCajero);
            conductor.addRol(rolConductor);

            usuarioRepository.saveAll(List.of(
                    dueno, gerente, encargado, recepcion, mecanico, almacen, cajero, conductor
            ));
        }
    }


}
