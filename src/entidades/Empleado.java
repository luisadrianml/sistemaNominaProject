/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 * Clase de entidad de Empleado con getters y setters
 * @author SistemaNomina LJ
 */
public class Empleado {
    private int ID_empleado;
    private String cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private char sexo;
    private String estadocivil;
    private String nacimiento;
    private String email;
    private String telefono;
    private String movil;
    private int estado;
    
    private Sexo sex;
    private EstadoCivil estadocvil;

    public Sexo getSex() {
        return sex;
    }
    
    public void setSex(Sexo sex) {
        this.sex = sex;
    }

    public EstadoCivil getEstadocvil() {
        return estadocvil;
    }

    public void setEstadocvil(EstadoCivil estadocvil) {
        this.estadocvil = estadocvil;
    }

    public Empleado(int ID_empleado) {
        this.ID_empleado = ID_empleado;
    }
       

    public Empleado(int ID_empleado, String cedula, String nombre, String apellido, String direccion, char sexo, String estadocivil, String nacimiento, String email, String telefono, String movil) {
        this.ID_empleado = ID_empleado;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.sexo = sexo;
        this.estadocivil = estadocivil;
        this.nacimiento = nacimiento;
        this.email = email;
        this.telefono = telefono;
        this.movil = movil;
    }
    
        public Empleado(int ID_empleado, String cedula, String nombre, String apellido, String direccion, Sexo sexo, EstadoCivil estadocivil, String nacimiento, String email, String telefono, String movil) {
        this.ID_empleado = ID_empleado;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.sex = sexo;
        this.estadocvil = estadocivil;
        this.nacimiento = nacimiento;
        this.email = email;
        this.telefono = telefono;
        this.movil = movil;
    }

    public Empleado() {

    }
    
    

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getID_empleado() {
        return ID_empleado;
    }

    public void setID_empleado(int ID_empleado) {
        this.ID_empleado = ID_empleado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    @Override
    public String toString() {
        return this.ID_empleado+"";
    }

    
    
}
