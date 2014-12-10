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
public class Cargo extends Empresa {
    private int id;
    private String nombre;
    private float salario;
    
    
    public Cargo(int id, String nombre) {
        this.nombre = nombre;
        this.id = id;
    }
    
    public Cargo(int id, String nombre, float salario) {
        this.nombre = nombre;
        this.id = id;
        this.salario = salario;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String toString() {
        return this.nombre;
    }
    
    public void setSalario(float salario) {
        this.salario = salario;
    }
    
    public float getSalario() {
        return this.salario;
    }
}
