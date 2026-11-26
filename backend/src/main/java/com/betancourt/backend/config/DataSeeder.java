package com.betancourt.backend.config;

import com.betancourt.backend.model.Usuario;
import com.betancourt.backend.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Crea un usuario administrador inicial.
 *
 * No modifica ni elimina los usuarios existentes.
 */
@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner inicializarUsuarioAdministrador(
            UsuarioRepository repository,
            PasswordEncoder encoder) {

        return args -> {

            // Comprueba si existen usuarios registrados.
            if (repository.count() == 0) {

                Usuario admin = new Usuario();

                admin.setNombre("Super");
                admin.setApellido("Admin");

                admin.setCorreo(
                    "admin@betancourt.com"
                );

                // La contraseña se guarda cifrada.
                admin.setPass(
                    encoder.encode("Admin12345*")
                );

                admin.setEstado(true);

                admin.setRol("SUPER_ADMIN");

                repository.save(admin);

                System.out.println(
                    "Usuario administrador inicial creado."
                );

                System.out.println(
                    "Cambia la contraseña inicial inmediatamente."
                );

            } else {

                System.out.println(
                    "La tabla usuarios ya contiene registros."
                );
            }
        };
    }
}