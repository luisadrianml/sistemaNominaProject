/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 * Clase de entidad de Departamento con getters y setters
 * @author SistemaNomina LJ
 */
public class Departamento extends Empresa {
    private int id;
    private String nombre;
    
    
    public Departamento(int id, String nombre, int idEmpresa) {
        super(idEmpresa);
        this.nombre = nombre;
        this.id = id;

    }
    
    public Departamento(int id, String nombre) {
        this.nombre = nombre;
        this.id = id;

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
    
}

