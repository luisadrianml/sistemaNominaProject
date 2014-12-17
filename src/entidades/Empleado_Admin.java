/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 * Clase de entidad de Empleado_admin con getters y setters
 * @author SistemaNomina LJ
 */
public class Empleado_Admin {
    private int id;
    private String fecha_ingreso;
    private int id_departamento;
    private int id_cargo;
    private int tipo_salario;
    private int estado;
    
    private Departamento departamento;
    private Cargo cargo;
    private TipoSalario tipoSalario;
    private Estados estados;

    public Empleado_Admin(int id, String fecha_ingreso, int id_departamento, int id_cargo, int tipo_salario, int estado, Departamento departamento, Cargo cargo, TipoSalario tipoSalario) {
        this.id = id;
        this.fecha_ingreso = fecha_ingreso;
        this.id_departamento = id_departamento;
        this.id_cargo = id_cargo;
        this.tipo_salario = tipo_salario;
        this.estado = estado;
        this.departamento = departamento;
        this.cargo = cargo;
        this.tipoSalario = tipoSalario;
    }

    public Empleado_Admin(int id, String fecha_ingreso, int id_departamento, int id_cargo, int tipo_salario, int estado) {
        this.id = id;
        this.fecha_ingreso = fecha_ingreso;
        this.id_departamento = id_departamento;
        this.id_cargo = id_cargo;
        this.tipo_salario = tipo_salario;
        this.estado = estado;
    }

    public Empleado_Admin(int id, String fecha_ingreso, Departamento departamento, Cargo cargo, TipoSalario tipoSalario, int estado) {
        this.id = id;
        this.fecha_ingreso = fecha_ingreso;
        this.estado = estado;
        this.departamento = departamento;
        this.cargo = cargo;
        this.tipoSalario = tipoSalario;
    }

    @Override
    public String toString() {
        return "" +id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
    }

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public int getTipo_salario() {
        return tipo_salario;
    }

    public void setTipo_salario(int tipo_salario) {
        this.tipo_salario = tipo_salario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public TipoSalario getTipoSalario() {
        return tipoSalario;
    }

    public void setTipoSalario(TipoSalario tipoSalario) {
        this.tipoSalario = tipoSalario;
    }

    public Estados getEstados() {
        return estados;
    }

    public void setEstados(Estados estados) {
        this.estados = estados;
    }

    
    
    
    
}
