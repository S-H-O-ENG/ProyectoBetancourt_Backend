package com.betancourt.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio para enviar correos electrónicos
 * relacionados con la recuperación de contraseña.
 *
 * Necesita una configuración SMTP válida.
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String frontendUrl;
    private final int recoveryMinutes;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${app.recovery.frontend-url:http://localhost:5173}") String frontendUrl,
            @Value("${app.recovery.expiration-minutes:15}") int recoveryMinutes) {

        this.mailSender = mailSender;
        this.frontendUrl = frontendUrl.replaceAll("/+$", "");
        this.recoveryMinutes = recoveryMinutes;
    }

    /**
     * Envía el enlace para restablecer la contraseña.
     */
    public void enviarRecuperacion(
            String correo,
            String token) {

        // Construye el enlace que abrirá React.
        String enlace = frontendUrl
            + "/?resetToken="
            + token;

        SimpleMailMessage mensaje =
            new SimpleMailMessage();

        mensaje.setTo(correo);

        mensaje.setSubject(
            "Recuperación de contraseña - Taller De Betancourt"
        );

        mensaje.setText(
            "Recibimos una solicitud para cambiar tu contraseña.\n\n"
            + "Abre el siguiente enlace para continuar:\n"
            + enlace
            + "\n\nEste enlace es válido por " + recoveryMinutes + " minutos."
            + "\nSi no solicitaste este cambio, ignora este mensaje."
        );

        // Envía el mensaje mediante SMTP.
        mailSender.send(mensaje);
    }
}