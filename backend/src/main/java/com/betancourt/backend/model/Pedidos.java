package com.betancourt.backend.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "pedidos")

public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpedido;

    private String nombre;
    private String apellido;
    private String rol;
    private String proveedor;
    private String repuesto;
    private Integer cantidad;
    private String importancia;
    private String metodopago;
    private String estado;

    // relacion muchos a muchos entre pedidos y productos, un pedido puede tener
    // muchos productos y un producto puede estar en muchos pedidos, se usa en este
    // caso la manera automatica de java @manytomany porque la tabla intermedia solo
    // contiene las llaves foraneas de las otras tablas, no contiene mas atributos,
    // JOIN TABLE = indicar las caracteristicas de la tabla intermedia, el nombre,
    // join columns = indicar la llave forarea de la tabla actual y inversejoin para
    // indicra la foranea de la otra tabka.
    @ManyToMany
    @JoinTable(name = "pedidos_has_productos", joinColumns = @JoinColumn(name = "idpedido"), inverseJoinColumns = @JoinColumn(name = "idproducto"))
    // private list... es para indicar que esta relacion es una lista de productos,
    // osea que un pedido puede tener muchos productos, y se usa list porque es una
    // coleccion de objetos, y se usa list en lugar de set porque set no permite
    // elementos duplicados, y en este caso si se permite que un pedido tenga el
    // mismo producto varias veces, por eso se usa list.
    private List<Productos> productos;

    // @ManyToOne
    // @JoinColumn(name = "idproveedor")
    // private Proveedor proveedor;

    public Pedidos() {
    }

    public Pedidos(Integer idpedido, String nombre, String apellido, String rol, String proveedor, String repuesto,
            Integer cantidad, String importancia, String metodopago, List<Productos> productos) {
        this.idpedido = idpedido;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.proveedor = proveedor;
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.importancia = importancia;
        this.metodopago = metodopago;
        this.productos = productos;
    }
    

    public Integer getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(String repuesto) {
        this.repuesto = repuesto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getImportancia() {
        return importancia;
    }

    public void setImportancia(String importancia) {
        this.importancia = importancia;
    }

    public String getMetodopago() {
        return metodopago;
    }

    public void setMetodopago(String metodopago) {
        this.metodopago = metodopago;
    }

    public List<Productos> getProductos() {
        return productos;
    }

    public void setProductos(List<Productos> productos) {
        this.productos = productos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

   
}
