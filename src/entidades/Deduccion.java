package entidades;

/**
 * Created by pc166 on 10/12/2014.
 */
public class Deduccion extends FlujoEfectivo{
    
    private int id;
    
    public Deduccion(String nombre, Double monto) {
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
