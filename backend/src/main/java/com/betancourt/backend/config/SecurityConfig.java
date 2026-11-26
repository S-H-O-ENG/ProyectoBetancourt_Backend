package com.betancourt.backend.config;

import com.betancourt.backend.security.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.*;

import java.util.List;

/**
 * Configuración principal de Spring Security.
 *
 * El sistema utiliza JWT y no mantiene sesiones
 * tradicionales en el servidor.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Codificador de contraseñas mediante BCrypt.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura las rutas públicas y privadas.
     */
    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtFilter) throws Exception {

        return http

            // Deshabilita CSRF porque se utiliza autenticación JWT.
            .csrf(csrf -> csrf.disable())

            // Habilita las solicitudes CORS.
            .cors(Customizer.withDefaults())

            // No crea sesiones HTTP en el servidor.
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            // Define los permisos de acceso.
            .authorizeHttpRequests(auth -> auth

                // Rutas públicas de autenticación.
                .requestMatchers(
                    "/api/auth/login",
                    "/api/auth/forgot-password",
                    "/api/auth/reset-password",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()

                // Permite las solicitudes preliminares CORS.
                .requestMatchers(
                    HttpMethod.OPTIONS, "/**"
                ).permitAll()

                // Las demás rutas requieren autenticación.
                .anyRequest().authenticated()
            )

            // Ejecuta el filtro JWT antes del filtro de usuario.
            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            )

            // Deshabilita autenticación HTTP básica.
            .httpBasic(basic -> basic.disable())

            // Deshabilita formularios de login de Spring.
            .formLogin(form -> form.disable())

            .build();
    }

    /**
     * Configuración CORS para permitir la comunicación
     * entre React y Spring Boot.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.allowed-origin}") String allowedOrigin) {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(allowedOrigin));

        config.setAllowedMethods(
            List.of(
                "GET", "POST", "PUT",
                "PATCH", "DELETE", "OPTIONS"
            )
        );

        config.setAllowedHeaders(
            List.of("Authorization", "Content-Type")
        );

        config.setExposedHeaders(
            List.of("Authorization")
        );

        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}