package com.betancourt.backend.config;

import com.betancourt.backend.domain.enums.UserRole;
import com.betancourt.backend.model.Usuario;
import com.betancourt.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    private static final String ADMIN_EMAIL = "admin@betancourt.com";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            if (!usuarioRepository.existsByEmail(ADMIN_EMAIL)) {
                Usuario superAdmin = Usuario.builder()
                        .firstName("Administrador")
                        .lastName("Betancourt")
                        .email(ADMIN_EMAIL)
                        .passwordHash(passwordEncoder.encode("Admin1234*"))
                        .role(UserRole.SUPER_ADMIN)
                        .isActive(true)
                        .build();

                usuarioRepository.save(superAdmin);
                System.out.println("Super Admin creado");
            }
        };
    }
}
