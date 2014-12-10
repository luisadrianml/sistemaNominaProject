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
public class Dependientes {
    private int id;
    private int id_empleado; 
    private String nombre;
    private String apellido;
    private String cedula;
    private String tipo;

    public Dependientes() {
    }

    public Dependientes(int id, int id_empleado, String nombre, String apellido, String cedula, String tipo) {
        this.id = id;
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.tipo = tipo;
    }
    

    public Dependientes(String tipo) {
        this.tipo = tipo;
    }
    
    
    public enum Tipo_Dependientes {
        HIJO {
            @Override
            public String toString() {
                return "hijo";
            }
        },
        
        CONYUGE {
            @Override
            public String toString() {
                return "conyuge";
            }
        },
        
        PADRE {
            @Override
            public String toString() {
                return "padre";
            }
        },
        
        MADRE {
            @Override
            public String toString() {
                return "madre";
            }
        },
        
        ABUELO {
            @Override
            public String toString() {
                return "abuelo";
            }
        },
        ABUELA {
            @Override
            public String toString() {
                return "abuela";
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return (this.tipo.substring(0, 1).toUpperCase() + this.tipo.substring(1));
    }
    
    


}
