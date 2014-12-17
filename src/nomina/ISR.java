/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */
package nomina;

/**
 * Clase hija de impuestos que contiene los valores especificos del ISR como sus diferentes escalas
 */
public class ISR extends Impuesto implements Cloneable{

    private double desdeSalario;
    private double hastaSalario;
    private double exceso;

    public ISR(String nombre, double factor) {
        super(nombre, factor);
    }

    public ISR(String nombre, double factor, double desdeSalario, double hastaSalario, double exceso) {
        super(nombre, factor);
        this.desdeSalario = desdeSalario;
        this.hastaSalario = hastaSalario;
        this.exceso = exceso;
    }

    public double getDesdeSalario() {
        return desdeSalario;
    }

    public void setDesdeSalario(double desdeSalario) {
        this.desdeSalario = desdeSalario;
    }

    public double getHastaSalario() {
        return hastaSalario;
    }

    public void setHastaSalario(double hastaSalario) {
        this.hastaSalario = hastaSalario;
    }

    public double getExceso() {
        return exceso;
    }

    public void setExceso(double exceso) {
        this.exceso = exceso;
    }

    @Override
    public String toString() {

        return "ISR{" +
                "desdeSalario=" + desdeSalario +
                ", hastaSalario=" + hastaSalario +
                ", exceso=" + exceso +
                ", nombre= "+nombre+
                ", factor= "+factor+
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
