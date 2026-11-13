//la carpeta confg se crea al mismo nivel de model para que asi sea una estructura limpia de java
//la carpeta se llama config pq regñlas de java y springboot porque agrupa el comportamiento global de la aplicacion, ocmo la seguridad cnecines o inyeccion de datos etc

//Dataseeder.java // ESTE ARCHIVO ES UN ARCHIVO QUE SE EJECUTA UNA SOLA VEZ CADA QUE SE ENCIENDE LA PP O EL SEERVIDOR, SIU FUNCON ES INICIAR DATOS QUE SON NECESARIOSEN LA BASE DE DATOS// SE GUARDA EN CONFIG PQ COMO DICE ARRIBA ES LA CARPETA QUE GUARDA LAS REGLAS GLOBALES DEL SISTEMA // SU FUNCIONA IENTO ES 1. DATASEEDER REVISA LA BD AUTOMATICAMENTE AL INICIAR, OSEA TRAE LOS DATOS O PREGUNTA QUE SI HAY ALGUN DATO REGITRADO 2. SI NO HAY NADA NI CUENTAS NI NADA, SE NECECITA CREAR UN SUPER_ADMIN Y EL MANDA LA LERTA 3. CREA LCUENTA MAESTRA 4. REPITE CICLO SIN OBVIAMENTE SIN CREAR LA CUETA DE NUEVO

package com.betancourt.backend.config;

import com.betancourt.backend.model.Usuario; // importamos la tabla - clase usuairo para poder trabjar en ella, por ejemplo para crear objetos 
import com.betancourt.backend.repository.UsuarioRepository; // traemos la clase esta pra poder comunicarnos con la bd poder hacer el crud 
import org.springframework.boot.CommandLineRunner; // ejecucion auto al momento de iniciar
import org.springframework.context.annotation.Bean; // para ejecutar metodos y guardae resulatdos
import org.springframework.context.annotation.Configuration; // pa indicar que aqui hay clases que definen configuraciones

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; //pa encriptar

//declaramos @confi.. para que srpingboot se entere de que esta clase tiene archivos de configuraicon
@Configuration 


public class DataSeeder {
    @Bean 
    public BCryptPasswordEncoder passwordEncoder(){
        return new  BCryptPasswordEncoder();
    }

    // bean es un comando que sirve para que apenas se inicie la app ejecute el metodo
    @Bean
    //command.... se usa para ejecutar las tareas de el metodo apenas se inicie
    //usuario repo usuaio repo.. aqui estamos llamando o trayendo la clase que creamos en la carpeta de repositorio para poder usarlo
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository){
        //return args // es una funcion que se ejecuta justo en el momento de arranque 
        return args -> {
            if (usuarioRepository.count() == 0 ){
                Usuario superAdmin = new Usuario();
                superAdmin.setNombre("Super");
                superAdmin.setApellido("Admin");
                superAdmin.setCorreo("Admin@betancourt.com");

                String passwordEncrip = passwordEncoder().encode("admin12345");
                superAdmin.setPass(passwordEncrip);


                superAdmin.setEstado(true);

                usuarioRepository.save(superAdmin);
                System.out.println("Super Admin creado");
            }else{
                System.out.println("la bd ya tiene registros");
            }
        };
    } 

    
}
