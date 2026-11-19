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

        if (!usuarioRepository.existsByCorreo(correoAdmin)) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setApellido("Betancourt");
            admin.setCorreo(correoAdmin);
            admin.setPassword(passwordEncoder.encode("Admin1234*"));
            admin.setRol(UserRole.SUPER_ADMIN);
            admin.setActivo(true);

            usuarioRepository.save(admin);
            System.out.println(">>> DataSeeder: Usuario SUPER_ADMIN creado (" + correoAdmin + " / Admin1234*)");
        }
    }
}
