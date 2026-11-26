package com.betancourt.backend.service;

import com.betancourt.backend.dto.*;
import com.betancourt.backend.model.*;
import com.betancourt.backend.repository.*;
import com.betancourt.backend.security.JwtService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Map;

/**
 * Servicio encargado de:
 *
 * 1. Validar las credenciales.
 * 2. Generar el JWT.
 * 3. Consultar el perfil del usuario.
 * 4. Solicitar recuperación de contraseña.
 * 5. Restablecer la contraseña.
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarios;
    private final PasswordResetTokenRepository resetTokens;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    private final int recoveryMinutes;

    // Generador seguro de números aleatorios.
    private final SecureRandom random = new SecureRandom();

    public AuthService(
            UsuarioRepository usuarios,
            PasswordResetTokenRepository resetTokens,
            PasswordEncoder encoder,
            JwtService jwtService,
            EmailService emailService,
            @Value("${app.recovery.expiration-minutes}") int recoveryMinutes) {

        this.usuarios = usuarios;
        this.resetTokens = resetTokens;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.recoveryMinutes = recoveryMinutes;
    }

    /**
     * Inicia sesión verificando el correo,
     * la contraseña y el estado de la cuenta.
     */
    public Map<String, Object> login(LoginRequest request) {

        // Busca el usuario por correo.
        Usuario usuario = usuarios
            .findByCorreoIgnoreCase(request.correo().trim())
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales incorrectas"
                )
            );

        // Comprueba que la cuenta esté habilitada
        // y que la contraseña coincida con BCrypt.
        if (!Boolean.TRUE.equals(usuario.getEstado())
                || !encoder.matches(
                    request.password(),
                    usuario.getPass()
                )) {

            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Credenciales incorrectas"
            );
        }

        // Genera el token JWT.
        String token = jwtService.generarToken(usuario);

        // Devuelve los datos que necesita React.
        return Map.of(
            "token", token,
            "role", usuario.getRol(),
            "nombre", usuario.getNombre(),
            "correo", usuario.getCorreo(),
            "expiresIn", jwtService.getExpirationMs()
        );
    }

    /**
     * Consulta la información del usuario autenticado.
     */
    public Map<String, Object> perfil(String correo) {

        Usuario usuario = usuarios
            .findByCorreoIgnoreCase(correo)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
                )
            );

        return Map.of(
            "id", usuario.getIdusuario(),
            "nombre", usuario.getNombre(),
            "apellido",
            usuario.getApellido() == null
                ? ""
                : usuario.getApellido(),
            "correo", usuario.getCorreo(),
            "rol", usuario.getRol()
        );
    }

    /**
     * Solicita la recuperación de contraseña.
     */
    @Transactional
    public void solicitarRecuperacion(
            ForgotPasswordRequest request) {

        // Busca la cuenta asociada al correo.
        Usuario usuario = usuarios
            .findByCorreoIgnoreCase(request.correo().trim())
            .orElse(null);

        // No revela si el correo está registrado.
        if (usuario == null
                || !Boolean.TRUE.equals(usuario.getEstado())) {
            return;
        }

        // Elimina solicitudes anteriores.
        resetTokens.deleteByUsuario_Idusuario(
            usuario.getIdusuario()
        );

        // Genera 32 bytes aleatorios.
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        // Convierte los bytes a un token seguro para URL.
        String rawToken = Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(bytes);

        // Crea el registro del token.
        PasswordResetToken entity = new PasswordResetToken();

        // Guarda únicamente el hash del token.
        entity.setTokenHash(sha256(rawToken));

        entity.setUsuario(usuario);

        // Establece su vencimiento.
        entity.setExpiresAt(
            Instant.now().plus(
                recoveryMinutes,
                ChronoUnit.MINUTES
            )
        );

        resetTokens.save(entity);

        // Envía el enlace al correo del usuario.
        try {

            emailService.enviarRecuperacion(
                usuario.getCorreo(),
                rawToken
            );

        } catch (RuntimeException ex) {

            // Si el correo falla, informa el problema
            // sin exponer el token de recuperación.
            throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "No fue posible enviar el correo. Verifica SMTP."
            );
        }
    }

    /**
     * Cambia la contraseña utilizando un token válido.
     */
    @Transactional
    public void restablecerPassword(
            ResetPasswordRequest request) {

        // Busca el token mediante su hash.
        PasswordResetToken registro = resetTokens
            .findByTokenHash(sha256(request.token()))
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Enlace inválido o vencido"
                )
            );

        // Verifica que no haya sido utilizado
        // y que todavía no haya vencido.
        if (registro.isUsed()
                || registro.getExpiresAt().isBefore(
                    Instant.now()
                )) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Enlace inválido o vencido"
            );
        }

        // Recupera el usuario relacionado.
        Usuario usuario = registro.getUsuario();

        // Cifra la nueva contraseña con BCrypt.
        usuario.setPass(
            encoder.encode(request.nuevaPassword())
        );

        usuarios.save(usuario);

        // Marca el token como utilizado.
        registro.setUsed(true);
        resetTokens.save(registro);

        // Elimina los demás tokens de recuperación.
        resetTokens.deleteByUsuario_Idusuario(
            usuario.getIdusuario()
        );
    }

    /**
     * Convierte el token original en un hash SHA-256.
     */
    private String sha256(String value) {

        try {

            byte[] hash = MessageDigest
                .getInstance("SHA-256")
                .digest(
                    value.getBytes(StandardCharsets.UTF_8)
                );

            return java.util.HexFormat
                .of()
                .formatHex(hash);

        } catch (Exception ex) {

            throw new IllegalStateException(
                "No fue posible procesar el token",
                ex
            );
        }
    }
}