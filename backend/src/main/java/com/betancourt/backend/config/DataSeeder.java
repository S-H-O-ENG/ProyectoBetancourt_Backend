package com.betancourt.backend.config;

import com.betancourt.backend.domain.enums.UserRole;
import com.betancourt.backend.model.Usuario;
import com.betancourt.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String correoAdmin = "admin@tallerbetancourt.com";

        boolean adminExiste = usuarioRepository.findAll().stream()
            .anyMatch(usuario -> correoAdmin.equalsIgnoreCase(usuario.getEmail()));

        if (!adminExiste) {
            Usuario admin = new Usuario();
            admin.setFirstName("Administrador");
            admin.setLastName("Betancourt");
            admin.setEmail(correoAdmin);
            admin.setPasswordHash(passwordEncoder.encode("Admin1234*"));
            admin.setRole(UserRole.SUPER_ADMIN);
            admin.setIsActive(true);

            usuarioRepository.save(admin);
            System.out.println(">>> DataSeeder: Usuario SUPER_ADMIN creado (" + correoAdmin + " / Admin1234*)");
        }
    }
}
