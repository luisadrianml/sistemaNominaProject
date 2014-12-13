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
public class TipoSalario {
    private int id;
    private String nombre;
    private float factor;

    public TipoSalario(int id, String nombre, float factor) {
        this.id = id;
        this.nombre = nombre;
        this.factor = factor;
    }
    
        public TipoSalario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    
    public String toString() {
        return this.nombre;
    }
    
    
}
