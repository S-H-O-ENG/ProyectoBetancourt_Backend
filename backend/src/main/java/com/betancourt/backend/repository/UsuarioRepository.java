<<<<<<< HEAD
package com.betancourt.backend.repository;

import java.util.Optional;
=======
//esta carpeta repository se crea al mismo nivel de model para que asi sea una estructura limpia de java, su funcion es conectase con la bd en postgre y su funcion es traducir los metodos de java a consultas sql para que la bd pueda entenderlas y poder hacer las consultas a la bd, esta carpeta es la que se encarga de hacer las consultas a la bd y devolver los resultados a la aplicacion, osea que esta carpeta es la que se encarga de hacer el puente entre la aplicacion y la bd, esta carpeta OBLIGATORIA porque sin ella no se podria hacer ninguna consulta a la bd y por lo tanto no se podria obtener ningun dato de la bd

//LA DIFRENCIA DE TRADUCCION ENTRE ESTE ARCHICO Y ORM ES LA SIGUIENTE ORM(HIBERNATE)= hace el trabajo de traducir REPOSITORY(este) = es cmo el menu o las opciones que utilizamos en el codigo para poder usar el orm, osea que este archivo es el que nos permite usar el orm para hacer consultas a la bd, sin tener que escribir codigo sql, sino que podemos usar metodos de java para hacer consultas a la bd, y el orm se encarga de traducir esos metodos de java a consultas sql para que la bd pueda entenderlas y poder hacer las consultas a la bd
package com.betancourt.backend.repository;

import java.util.Optional;


import com.betancourt.backend.model.Usuario; //importacion de la clase usuario para poder usarla en este archivo // cada archivo en repository debe de importar la tabla a la que se vaya a referir
import org.springframework.data.jpa.repository.JpaRepository; //importacion de la libreria jpa para poder usarla en este archivo // esta importacion es como una libreria que trae ya todos los comandos basicos de bases de datos por ejemplo SAVE para guardar FINDALL para buscar todos COUNT para contar DELETE para eliminar etc, osea es como unn CRUD 


>>>>>>> Develop

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betancourt.backend.domain.enums.UserRole;
import com.betancourt.backend.model.Usuario;

<<<<<<< HEAD
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<Usuario> findByRoleAndIsActive(UserRole role, Boolean isActive, Pageable pageable);
    Page<Usuario> findByRole(UserRole role, Pageable pageable);
    Page<Usuario> findByIsActive(Boolean isActive, Pageable pageable);
}
=======
//OJO, AQUI ES PUBLIC INTERFACE Y NO PUBLIC CLASS PQ = LA INTERFAZ ES EL MENU O LO QUE NOS DICE QUE PODEMOS HACER, LA INTERRFAZ NO LLEVA CODIGO POR DENTRO, SOLO DECLARAMOS LAS CLASES QUE VAMOS A UTILIZAR O LAS LIBRERIAAS, SI PONEMOS ESTO CMO CLASS VA A DAR ERROR ADEMAS DE QUE DEBERIAMOS ESCRIBIR NOSTROS MISMOS EL CRUD EN CODDIGO, CON LA INTERFAZ Y LA DECLAACIONES YA TENEMOS TODO ESO Y JAVA LO ARRANCA APENAS PRENDAMOS LA APP

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
}
>>>>>>> Develop
