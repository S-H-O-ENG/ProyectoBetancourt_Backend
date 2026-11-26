// Dirección del paquete donde se ubica este controlador web dentro de la estructura del proyecto
package com.betancourt.backend.controller;

// Importaciones de librerías nativas de Java para manejar estructuras de datos y valores opcionales
import java.util.HashMap; // Permite crear estructuras de clave-valor para armar el JSON de respuesta
import java.util.Map; // Interfaz estándar que respalda al HashMap
import java.util.Optional; // La "caja de seguridad" para evitar errores de nulos (NullPointerException) si no existe el correo

import org.springframework.beans.factory.annotation.Autowired; // Permite la inyección automática de dependencias en Spring
import org.springframework.http.ResponseEntity; // Permite controlar las respuestas HTTP exactas (ej. 200 OK, 401 No autorizado)
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Herramienta para comparar contraseñas encriptadas con BCrypt
import org.springframework.web.bind.annotation.CrossOrigin; // Configura CORS para permitir peticiones desde React (puerto 3000)
import org.springframework.web.bind.annotation.PostMapping; // Mapea peticiones HTTP de tipo POST (ideal para envíos de formularios/login)
import org.springframework.web.bind.annotation.RequestBody; // Captura el JSON enviado por React y lo convierte en un objeto Java
import org.springframework.web.bind.annotation.RequestMapping; // Define la ruta URL base para los endpoints de este controlador
import org.springframework.web.bind.annotation.RestController; // Indica que la clase es un controlador REST que devuelve datos directos (JSON)

import com.betancourt.backend.dto.LoginRequest; // El molde DTO para capturar de forma limpia el correo y contraseña del login
import com.betancourt.backend.model.Usuario; // La entidad Usuario mapeada a la tabla de la base de datos
import com.betancourt.backend.repository.UsuarioRepository; // El repositorio para hacer consultas automáticas en PostgreSQL
import com.betancourt.backend.security.JwtService; // El servicio personalizado para generar y validar los tokens JWT

@RestController // Indica que esta clase es un controlador REST; sus métodos devuelven datos
                // directos (JSON) y no páginas web HTML
@RequestMapping("/api/auth") // Ruta base de la URL para este controlador; todas las peticiones aquí
                             // empezarán con: http://localhost:8080/api/auth
@CrossOrigin(origins = "http://localhost:5173") // Configura CORS para permitir que tu app de React (en el puerto 3000)
                                                // se comunique sin bloqueos
public class AuthController {

    @Autowired // Inyección de dependencias: conecta automáticamente el repositorio de usuarios
               // para poder buscar en la BD
    private UsuarioRepository usuarioRepository;

    @Autowired // Inyección de dependencias: conecta tu clase JwtService para poder fabricar el
               // token cuando el login sea exitoso
    private JwtService jwtService;

    @Autowired // Inyección de dependencias: conecta el encriptador BCrypt definido en el
               // DataSeeder para verificar claves cifradas
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login") // Define que este método solo responderá a peticiones HTTP de tipo POST en la
                           // ruta: /api/auth/login
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // @RequestBody agarra el JSON enviado por React y lo convierte en un objeto
        // Java de tipo LoginRequest

        // 1. Buscamos en la base de datos si existe un usuario con el correo que
        // ingresó el usuario
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(loginRequest.getCorreo());

        // 2. Evaluamos si el usuario fue encontrado (isPresent devuelve true si la
        // "caja" tiene un usuario adentro)
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get(); // Extraemos el objeto Usuario de adentro del Optional para poder leer
                                                // sus datos

            // 3. Validamos dos condiciones de seguridad esenciales:
            // a) Comparamos la contraseña escrita en el texto plano con el Hash encriptado
            // guardado en la BD usando BCrypt.
            // b) Verificamos que el estado del usuario sea true (activo, asegurando que un
            // usuario inhabilitado no pueda entrar).
            boolean passwordCorrecta = passwordEncoder.matches(loginRequest.getpassword(), usuario.getPassword());

            if (passwordCorrecta && Boolean.TRUE.equals(usuario.getActivo())) {

                // 4. Si las credenciales son correctas, usamos tu JwtService para generar un
                // token único basado en el correo del usuario
                String token = jwtService.generateToken(usuario.getCorreo());

                // 5. Creamos un mapa (diccionario) para empaquetar los datos de éxito que
                // mandaremos de regreso a React en formato JSON
                Map<String, Object> response = new HashMap<>();
                response.put("token", token); // El token JWT generado
                response.put("mensaje", usuario.getNombre()); // Un mensaje amigable de bienvenida
                response.put("nombre", usuario.getNombre()); // El nombre del usuario obtenido de la BD
                response.put("correo", usuario.getCorreo()); // El correo del usuario
                response.put("role", usuario.getRol());

                // 6. Retornamos un código HTTP 200 (OK) junto con el mapa convertido
                // automáticamente a JSON
                return ResponseEntity.ok(response);
            }
        }

        // 7. Si el correo no existe en la base de datos, la contraseña falla o el
        // usuario está inactivo,
        // el programa no explota (gracias al Optional), sino que devuelve un error HTTP
        // 401 (Unauthorized) ordenado y limpio
        return ResponseEntity.status(401).body("Correo o contraseña incorrectos");
    }
}
