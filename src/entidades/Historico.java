/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 * Clase de entidad de Historico con getters y setters
 * @author SistemaNomina LJ
 */
public class Historico {
    private int id_empleado;
    private Double salariobruto;
    private Double sumaIngresos;
    private Double ingresosMasSalario;
    private Double sumaImpuestos;
    private Double deducciones;
    private Double sumaDeducciones;
    private Double salarioNeto;
    private Double horasExtra;
    private int mes;
    private String year;
    private int periodo;

    public int getId_empleado() {
        return id_empleado;
    }

    public Double getHorasExtra() {
        return horasExtra;
    }

    public void setHorasExtra(Double horasExtra) {
        this.horasExtra = horasExtra;
    }
    

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Double getSalariobruto() {
        return salariobruto;
    }

    public void setSalariobruto(Double salariobruto) {
        this.salariobruto = salariobruto;
    }

    public Double getSumaIngresos() {
        return sumaIngresos;
    }

    public void setSumaIngresos(Double sumaIngresos) {
        this.sumaIngresos = sumaIngresos;
    }

    public Double getIngresosMasSalario() {
        return ingresosMasSalario;
    }

    public void setIngresosMasSalario(Double ingresosMasSalario) {
        this.ingresosMasSalario = ingresosMasSalario;
    }

    public Double getSumaImpuestos() {
        return sumaImpuestos;
    }

    public void setSumaImpuestos(Double sumaImpuestos) {
        this.sumaImpuestos = sumaImpuestos;
    }

    public Double getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(Double deducciones) {
        this.deducciones = deducciones;
    }

    public Double getSumaDeducciones() {
        return sumaDeducciones;
    }

    public void setSumaDeducciones(Double sumaDeducciones) {
        this.sumaDeducciones = sumaDeducciones;
    }

    public Double getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(Double salarioNeto) {
        this.salarioNeto = salarioNeto;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }
    
    
}
