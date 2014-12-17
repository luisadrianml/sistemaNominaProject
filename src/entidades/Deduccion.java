package entidades;

/**
 * Clase de entidad deduccion con getters y setters
 * @author SistemaNomina LJ
 */
public class Deduccion extends FlujoEfectivo{
    
    private int id;
    private int id_empleado;
    
    public Deduccion(String nombre, Double monto) {
        super(nombre, monto);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Deduccion(int id, int id_empleado, String nombre, Double monto) {
        super(nombre, monto);
        this.id = id;
        this.id_empleado = id_empleado;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }
    
    
    
    
}
