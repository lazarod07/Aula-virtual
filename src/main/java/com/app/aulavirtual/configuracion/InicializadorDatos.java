package com.app.aulavirtual.configuracion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.aulavirtual.entidades.User;
import com.app.aulavirtual.repositorios.UserRepository;

@Configuration
public class InicializadorDatos {

    @Bean
    CommandLineRunner inicializarBaseDatos(
            UserRepository usuRepository,
            PasswordEncoder passwordEncoder) {

        return arg -> {
            if (usuRepository.findByUsername("admin").isEmpty()) {
                User user = new User(null, "admin"
                , passwordEncoder.encode("12345"), "ADMIN");
                usuRepository.save(user);
            }
        };
    }
}
