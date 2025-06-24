package com.utp.integradorspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Ya no necesitas esta importación si eliminas el bean
// import org.springframework.security.crypto.password.PasswordEncoder; // Ya no necesitas esta importación si eliminas el bean

@Configuration
public class AppConfig {

    // ELIMINA O COMENTA ESTE MÉTODO
    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    */

    // Si tienes otros beans aquí, déjalos. Por ejemplo:
    // @Bean
    // public OtroBean otroBean() {
    //     return new OtroBean();
    // }
}