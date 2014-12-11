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
public class Administrador extends Usuarios {

    public Administrador(int id_tipo, String usuario, String clave, int id) {
        super(id_tipo, usuario, clave, id);
    }

    public Administrador() {
        super(1);
    }

    @Override
    public String toString() {
        return "Administrador";
    }

    
}
