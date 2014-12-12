package entidades;

/**
 * Created by pc166 on 10/12/2014.
 */
public class Ingreso extends FlujoEfectivo{
    
    private int id;
    
    public Ingreso(String nombre, Double monto) {
        super(nombre, monto);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
