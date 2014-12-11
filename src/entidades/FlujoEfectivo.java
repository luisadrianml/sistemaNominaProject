package entidades;

/**
 * Created by pc166 on 10/12/2014.
 */
public abstract class FlujoEfectivo {
    protected String nombre;
    protected Double monto;

    public FlujoEfectivo(String nombre, Double monto) {
        this.nombre = nombre;
        this.monto = monto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "FlujoEfectivo{" +
                "nombre='" + nombre + '\'' +
                ", monto=" + monto +
                '}';
    }
}
