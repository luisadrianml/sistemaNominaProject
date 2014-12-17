/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */
package nomina;

/**
 * Clase identidad de los impuestos donde se almacenan sus datos, y factores
 */
public  class Impuesto implements Cloneable{
    protected String nombre;
    protected double factor;

    public Impuesto(String nombre, double factor) {
        this.nombre = nombre;
        this.factor = factor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    @Override
    public String toString() {
        return "Impuesto{" +
                "nombre='" + nombre + '\'' +
                ", factor=" + factor +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
