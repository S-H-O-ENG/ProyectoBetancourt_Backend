package com.betancourt.backend.repository;

<<<<<<< HEAD
public class ClientesRepository {
=======
import com.betancourt.backend.model.Clientes; //importacion de la clase usuario para poder usarla en este archivo // cada archivo en repository debe de importar la tabla a la que se vaya a referir
import org.springframework.data.jpa.repository.JpaRepository; //importacion de la libreria jpa para poder usarla en este archivo // esta importacion es como una libreria que trae ya todos los comandos basicos de bases de datos por ejemplo SAVE para guardar FINDALL para buscar todos COUNT para contar DELETE para eliminar etc, osea es como unn CRUD 
import org.springframework.stereotype.Repository; 

@Repository

public interface ClientesRepository extends JpaRepository<Clientes, Integer> {
>>>>>>> Develop
    
}
