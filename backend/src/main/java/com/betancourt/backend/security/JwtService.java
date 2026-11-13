package com.betancourt.backend.security;

//importaciones para poder usar JWT
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

//etiqueta para marcar este archivo como logica de negocio? y poder usarlo en otras partes 
@Service
public class JwtService {

    // es un comando que nos genar una clave para firmar los jwt del taller se usa para evirar falsificaciones de el jwt sin ets clave cualquier token inventado se rechaza
    private static final String SECRET_KEY = "clavetallerbetancourtde32caracteresomaspvpoq";

    //esta es una funcion que toma la contra anteriolr y la convierte primero a bytes y luego a partir de eso genera un codifo encripatdo que se usa en cada jwt del taller para marcalos como autenticos
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // aqui se genera wl jwt
    
    //esta es la funcjon que recibe los datos del usuario, el correo por ejemplo y devuelve el jwt en txt
    public String generateToken(String username) {
        //jwt builder es el generador de tokens de la liberia que estamos usando
        return Jwts.builder()
                //subject... guarda la infromacion del usuario en el token para saber de quien es 
                .subject(username) 
                //issiedata registra la fecha en la que se genero el jwt
                .issuedAt(new Date(System.currentTimeMillis()))
                //esto es para definir cuanto va a durar el token activo, se puede poner cualquier timpom y lo que hacemos para definir la expiracion es sumarle un x tiempo a la fecha y hora del iniciode sesipn
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) 
                //esta linea pone la clave que hicimos arriba en el token, lo marca como autentico
                .signWith(getSigningKey()) 
                //esta linea pone toda la informacion en una sola cadena 
                .compact();
    }

    //esta es una funcion que nos ayuda a saber de quien e  el token, lo que se hace es que esta funcion lo desempaqueta para saber a quien pertenece , lo desempaqueta usando la clve que definimos = verifiwith(signedkey) y luego lo lee con (getpayload) 
    public String extractUsername(String token) {
        //lo de abajo es lo que inicia el desempaquetado
        Claims claims = Jwts.parser()
                //usa la clave secreta
                .verifyWith(getSigningKey())
                .build()
                //esto toma el token y verifica la firma
                .parseSignedClaims(token)
                //esto lo lee y lo guarda
                .getPayload();
        return claims.getSubject();
    }

    // este es una segunda capa para validar el token
    //creamos una funcion booleana donde se va a comparar el token y el nombre de usuario 
    public boolean isTokenValid(String token, String username) {
        //esta liena llama al metodo de arriba para saber con que va a comprar el token =, con que datos
        final String extractedUsername = extractUsername(token);
        //y esto hace la validacion 
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    //este es una funcion que nos verifica el tiempo del token, si todavia sirve  o no, una funcion booleana
    private boolean isTokenExpired(String token) {
        //67 a 72 aqui repetimmos el proceso de la funcion pasada para sacar la informacion del token agregando la expiracion
        Date expiration = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        // y aqui comparamos la hora actual = new date con el comandoo before que lo que hace es prguntar si la fecha de expiracion es anterior a la hora actual
        return expiration.before(new Date());
    }
}