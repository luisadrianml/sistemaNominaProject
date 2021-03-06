/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 * Clase de entidad de Empresa con getters y setters
 * @author SistemaNomina LJ
 */
public class Empresa {
    
    private int ID;
    private String nombre;
    private String rnc;
    
    public Empresa(int ID, String nombre, String rnc) {
        this.ID = ID;
        this.nombre= nombre;
        this.rnc = rnc;
    }
    
    public Empresa() {
        //Empty constructor
    }
    
     public Empresa(int id) {
        this.ID = id;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRnc() {
        return rnc;
    }

    public void setRnc(String rnc) {
        this.rnc = rnc;
    }
    
    

}
