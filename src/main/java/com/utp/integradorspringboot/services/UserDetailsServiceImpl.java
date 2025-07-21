package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Rol; 
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection; 
import java.util.stream.Collectors; 

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));

        if (usuario.getEstado() == null || !usuario.getEstado()) {
            throw new UsernameNotFoundException("Usuario no verificado o inactivo: " + correo);
        }

        
        Collection<? extends GrantedAuthority> authorities = usuario.getRoles().stream()
            .map(rol -> new SimpleGrantedAuthority(rol.getNombre())) 
            .collect(Collectors.toList());

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasenia())
                .authorities(authorities) 
                .build();
    }
}