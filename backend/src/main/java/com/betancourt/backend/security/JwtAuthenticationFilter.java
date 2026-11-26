package com.betancourt.backend.security;

import com.betancourt.backend.model.Usuario;
import com.betancourt.backend.repository.UsuarioRepository;

import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro que intercepta las peticiones HTTP.
 *
 * Comprueba el JWT y carga la identidad y el rol
 * del usuario en el contexto de Spring Security.
 */
@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UsuarioRepository usuarioRepository) {

        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        // Obtiene el encabezado Authorization.
        String header = request.getHeader("Authorization");

        // Comprueba que se haya enviado un Bearer token.
        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);
            SecurityContextHolder.clearContext();

            try {

                // Extrae el ID del usuario desde el JWT.
                Integer id = jwtService.obtenerIdUsuario(token);

                // Consulta al usuario directamente en PostgreSQL.
                Usuario usuario = usuarioRepository
                    .findById(id.longValue())
                    .orElse(null);

                // Solo permite cuentas existentes y habilitadas.
                if (usuario != null
                        && Boolean.TRUE.equals(usuario.getEstado())) {

                    // Crea la autenticación con el correo y el rol.
                    var auth =
                        new UsernamePasswordAuthenticationToken(
                            usuario.getCorreo(),
                            null,
                            List.of(
                                new SimpleGrantedAuthority(usuario.getRol()),
                                new SimpleGrantedAuthority("ROLE_" + usuario.getRol())
                            )
                        );

                    // Guarda la autenticación en Spring Security.
                    SecurityContextHolder.getContext()
                        .setAuthentication(auth);
                }

            } catch (JwtException | IllegalArgumentException ex) {

                // Si el JWT es inválido o venció,
                // se elimina el contexto de autenticación.
                SecurityContextHolder.clearContext();
            }
        }

        // Continúa con la siguiente etapa de la petición.
        chain.doFilter(request, response);
    }
}