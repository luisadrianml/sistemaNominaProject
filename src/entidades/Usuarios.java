/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author pc167
 */
public class Usuarios {
    private int id_tipo;
    private String usuario;
    private String clave;
    private int id;

    public Usuarios(int id_tipo, String usuario, String clave, int id) {
        this.id_tipo = id_tipo;
        this.usuario = usuario;
        this.clave = clave;
        this.id = id;
    }
    
    

    public Usuarios(int id_tipo) {
        this.id_tipo = id_tipo;
    }



    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getNombre() {
        return usuario;
    }

    public void setNombre(String nombre) {
        this.usuario = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
