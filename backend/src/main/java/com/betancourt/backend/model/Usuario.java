//direccion del archivo
package com.betancourt.backend.model;

//importaciones de librerias necesarias
import jakarta.persistence.Entity; //ESTA LIBRERIA ES PARA INDICAR QUE LA CLASE ES UNA ENTIDAD DE BASE DE DATOS
import jakarta.persistence.GeneratedValue; //ESTA LIBRERIA ES PARA INDICAR QUE EL VALOR DEL ID SE GENERA AUTOMATICAMENTE
import jakarta.persistence.GenerationType; //ESTA LIBRERIA ES PARA INDICAR EL TIPO DE GENERACION DEL ID
import jakarta.persistence.Id; //ESTA LIBRERIA ES PARA INDICAR QUE EL ATRIBUTO ES EL ID DE LA ENTIDAD
import jakarta.persistence.Table; //ESTA LIBRERIA ES PARA INDICAR EL NOMBRE DE LA TABLA EN LA BASE DE DATOS

//import jakarta.persistence.*; SIRVE PARA REEMPLAZAR TODOS LAS IMPORACIONES ANTERIORES, ESTE IMPORTA TODO LO DE JAKARTA PERSISTENCE DE UNA


//entity sirve para indicar que esta clase es una entidad de la base de datos osea una tabla, eso hace que cada objeto que se cree aqui se convierta en un registro dentro de la base de datos
@Entity
//table sirve para indicar el nombre de la tabla que se va a crear en la base de datos, es el mapeador de tablas
//el mapeador es el orm = mapeo de objeto relacional = aqui el orm es hibernate que se comunica atrabes de jpa con la base de datos, hibernate es el que se encarga de crear la tabla en la base de datos y de mapear los objetos a registros en la tabla, osea que el orm hace de puente entre la base de datos y el lenguaje de programacion, en este caso java, para que no tengamos que escribir codigo sql para crear la tabla y los registros, sino que hibernate lo hace por nosotros
//jpa = java persistence api = es la api de java que nos permite trabajar con bases de datos relacionales, es la que nos permite mapear objetos a registros en la base de datos, y es la que nos permite hacer consultas a la base de datos sin tener que escribir codigo sql, sino que podemos usar metodos de java para hacer consultas a la base de datos
@Table(name = "usuarios")

public class Usuario {
    @Id //id sirve para indicar que este atributo es el id de la entidad, osea que es el identificador unico de cada registro en la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generatedvalue sirve para indicar que el valor del id se genera automaticamente, osea que no tenemos que asignarle un valor al id, sino que la base de datos lo hace por nosotros, y el strategy sirve para indicar el tipo de generacion del id, en este caso identity significa que el id se genera automaticamente y es unico para cada registro
    private Integer idusuario; //atributo idusuario es el id de la entidad, osea que es el identificador unico de cada registro en la tabla, y es de tipo integer porque es un numero entero
    private String nombre; //atributo nombre es el nombre del usuario, y es de tipo string porque es una cadena de caracteres
    private String apellido; //atributo apellido es el apellido del usuario, y es de tipo string porque es una cadena de caracteres
    private String telefono; //atributo telefono es el telefono del usuario, y es de tipo string porque aunque el telefono sean numros no se usan para hacer operaciones y muchas veces tienen codigos de pais y signos, por eso es mejor guaradlos como string
    private String correo; //atributo correo es el correo del usuario, y es de tipo string porque es una cadena de caracteres
    private String pass; //contra 
    private Boolean estado; //

    
    //se generar metodos constructores, se genera 2 tipos de constructor 1. vacio = ESTE CONTRCUTOR ES OBLIGATORIO Y VITAL PORQUE CUANDO EL PROGRAMA DE SPING HACE UNA CONSULTA A POSTGRE NECECITA CREAR UN OBJETO VACIO EN LA MEMORIA PARA DESPUES LLENARLO CON LOS DATOS DE LA BD, SIN ESTE CONSTRCUTOR VACIO DARA ERROR 2. constructor con parametros = NO ES OBLIGATORIO PERO SE USA PARA QUE NOSOSTROS PODAMOS CREAR UN OBJETO PONIENDO TODOS LOS DATOS DE UNA VEZ, OSEA QUE NOSOTROS PODEMOS CREAR UN OBJETO Y PASARLE TODOS LOS DATOS DEL USUARIO DE UNA VEZ, EN LUGAR DE CREAR EL OBJETO VACIO Y DESPUES LLENARLO CON LOS SETTERS, ESTE CONSTRUCTOR CON PARAMETROS ES MAS RAPIDO Y EFICIENTE PARA CREAR OBJETOS

    public Usuario() {
    }

    public Usuario(Integer idusuario, String nombre, String apellido, String telefono, String correo, String pass, Boolean estado) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.pass = pass;
        this.estado = estado;
    }

    
    //se generan los metodos getters y setters, estos metodos son los que nos permiten acceder a los atributos de la clase, osea que nos permiten obtener y modificar los valores de los atributos, estos metodos son necesarios porque los atributos son privados y no se pueden acceder directamente desde fuera de la clase, por eso necesitamos estos metodos para poder acceder a ellos

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    
    
    
}

