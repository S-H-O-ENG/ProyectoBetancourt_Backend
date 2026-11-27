package com.betancourt.backend.security;

import com.betancourt.backend.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Base64;

/**
 * Servicio encargado de generar y validar los JWT.
 *
 * El token contiene:
 * - Identificador del usuario.
 * - Correo electrónico.
 * - Rol de acceso.
 * - Fecha de creación.
 * - Fecha de vencimiento.
 */
@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs) {

        // Decodifica la clave secreta configurada en Base64.
        this.key = Keys.hmacShaKeyFor(
            Base64.getDecoder().decode(secret)
        );

        // Tiempo de duración del token.
        this.expirationMs = expirationMs;
    }

    /**
     * Genera un JWT para el usuario autenticado.
     */
    public String generarToken(Usuario usuario) {

        Date ahora = new Date();

        return Jwts.builder()

            // Identificador único del usuario.
            .subject(String.valueOf(usuario.getIdusuario()))

            // Información adicional del usuario.
            .claim("correo", usuario.getCorreo())
            .claim("rol", usuario.getRol())
            .claim("nombre", usuario.getNombre())

            // Fecha de creación del token.
            .issuedAt(ahora)

            // Fecha de vencimiento.
            .expiration(
                new Date(ahora.getTime() + expirationMs)
            )

            // Firma del token.
            .signWith(key)

            // Genera el JWT final.
            .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).get("correo", String.class);
    }

    public boolean isTokenValid(String token, String username) {
        Claims claims = extractClaims(token);
        return username.equals(claims.get("correo", String.class))
            && claims.getExpiration().after(new Date());
    }

    /**
     * Valida la firma y vigencia del JWT
     * y obtiene el identificador del usuario.
     */
    public Integer obtenerIdUsuario(String token) {

        String subject = extractClaims(token).getSubject();

        return Integer.valueOf(subject);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    /**
     * Devuelve el tiempo de expiración configurado.
     */
    public long getExpirationMs() {
        return expirationMs;
    }
}