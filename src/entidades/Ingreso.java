package entidades;

/**
 * Clase de entidad de Ingreso con getters y setters
 * @author SistemaNomina LJ
 */
public class Ingreso extends FlujoEfectivo{
    
    private int id;
    private int id_empleado;
    
    public Ingreso(String nombre, Double monto) {
        super(nombre, monto);
    }

    public Ingreso(int id, int id_empleado, String nombre, Double monto) {
        super(nombre, monto);
        this.id = id;
        this.id_empleado = id_empleado;
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

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }
    
    
}
