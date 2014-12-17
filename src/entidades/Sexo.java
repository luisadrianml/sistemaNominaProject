/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 * Clase de entidad de Sexo con getters y setters
 * @author SistemaNomina LJ
 */
public class Sexo {
    private String nombre;
    private char id;
    
    public Sexo(String nombre, char id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getId() {
        return id;
    }

    public void setId(char id) {
        this.id = id;
    }
    
    public String toString() {
        return this.nombre;
    }
    
    
}
