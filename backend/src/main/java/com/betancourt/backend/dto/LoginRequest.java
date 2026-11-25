// Dirección del paquete donde se encuentra este archivo dentro de la estructura del proyecto
package com.betancourt.backend.dto;

// Declaración de la clase pública que servirá como molde para recibir las credenciales desde React
public class LoginRequest {

    // Atributo privado para almacenar el correo electrónico que envía el frontend
    private String correo;

    // Atributo privado para almacenar la contraseña que envía el frontend
    private String password;

    // 1. CONSTRUCTOR VACÍO: OBLIGATORIO Y VITAL.
    // Spring Boot lo necesita para leer el JSON que manda React y transformarlo automáticamente en un objeto Java.
    public LoginRequest() {
    }

    // 2. CONSTRUCTOR CON PARÁMETROS:
    // No es estrictamente obligatorio para Spring, pero nos permite crear objetos pasándole ambos datos de golpe si lo necesitamos.
    public LoginRequest(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    // MÉTODOS GETTERS Y SETTERS:
    // Necesarios porque los atributos son privados y necesitamos una forma segura de leerlos (get) y modificarlos (set).
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
