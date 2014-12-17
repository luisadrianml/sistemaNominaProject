/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */
package nomina;

import entidades.Deduccion;
import entidades.Empleado;
import entidades.Ingreso;

import java.util.List;

/**
 * Clase de Nomina, entidad que representa lo que se busca en el sistema.
 */
public final class Nomina {
    private Impuesto sfs;
    private Impuesto afp;
    private ISR isr;
    private Empleado empleado;
    private List<Ingreso> ingresos;
    private List<Deduccion> deducciones;
    private Double salario;

    /**
     * Getter de SFS
     * @return El impuesto como entidad 
     */
    public Impuesto getSFS() {
        return sfs;
    }

    /**
     * Setter de SFS
     * @param sfs Impuesto que se va configurar
     */
    public void setSFS(Impuesto sfs) {
        this.sfs = sfs;
    }

    public Impuesto getAFP() {
        return afp;
    }

    public void setAFP(Impuesto afp) {
        this.afp = afp;
    }

    public ISR getISR() {
        return isr;
    }

    public void setISR(ISR isr) {
        this.isr = isr;
    }

    /**
     * Metodo que retorna empleado de la nomina
     * @return Retorna el empleado actual en la nomina
     */
    public Empleado getEmpleado() {
        return empleado;
    }

    /**
     * Metodo que setea un empleado en la nomina
     * @param empleado Empleado a setear en la nomina
     */
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    /**
     * Metodo que retorna una lista de ingresos
     * @return Retorna lista de Ingresos de la nomina del empleado
     */
    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    /**
     * Setter de los ingresos de la nomina
     * @param ingresos Ingresos a setear en la clase
     */
    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }
    
    /**
     * Metodo que retorna una lista de deducciones
     * @return Retorna lista de deducciones de la nomina del empleado
     */
    public List<Deduccion> getDeducciones() {
        return deducciones;
    }
   /**
     * Setter de los deducciones de la nomina
     * @param deducciones deducciones a setear en la clase
     */
    public void setDeducciones(List<Deduccion> deducciones) {
        this.deducciones = deducciones;
    }

    /**
     * Metodo que retorna el salario
     * @return Retorna el salario del empleado
     */
    public Double getSalario() {
        return salario;
    }

    /**
     * Metodo que setea el salario
     * @param salario Salario a setear en el objecto
     */
    public void setSalario(Double salario) {
        this.salario = salario;
    }

    /**
     * Metodo que sobrescribe el tostring para imprirmir todo lo que contiene la nomina
     * @return Un conjunto de string que contiene los datos creados
     */
    @Override
    public String toString() {
        return "Nomina{" +
                "sfs=" + sfs +
                ", afp=" + afp +
                ", isr=" + isr +
                ", empleado=" + empleado +
                ", ingresos=" + ingresos +
                ", deducciones=" + deducciones +
                ", salario=" + salario +
                '}';
    }
}
