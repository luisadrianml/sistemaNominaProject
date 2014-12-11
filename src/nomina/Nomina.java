package nomina;

import entidades.Deduccion;
import entidades.Empleado;
import entidades.Ingreso;

import java.util.List;

/**
 * Created by pc166 on 10/12/2014.
 */
public final class Nomina {
    private Impuesto sfs;
    private Impuesto afp;
    private ISR isr;
    private Empleado empleado;
    private List<Ingreso> ingresos;
    private List<Deduccion> deducciones;
    private Double salario;

    public Impuesto getSFS() {
        return sfs;
    }

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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }

    public List<Deduccion> getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(List<Deduccion> deducciones) {
        this.deducciones = deducciones;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

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
