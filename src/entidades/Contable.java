/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 * Clase de entidad de Contable con getters y setters
 * @author SistemaNomina LJ
 */
public class Contable extends Usuarios {

    public Contable(int id_tipo, String usuario, String clave, int id) {
        super(id_tipo, usuario, clave, id);
    }

    public Contable() {
        super(2);
    }

    @Override
    public String toString() {
        return "Contable";
    }
    
    
        
}
