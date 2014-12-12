package nomina;

import entidades.Empleado;

import java.util.List;

/**
 * Created by pc166 on 10/12/2014.
 */
public class Tests {
    public static void main(String... args) {
        testPresentaNominaCorrecta();
    }

    public static void testPresentaNominaCorrecta() {
        Empleado emp = new Empleado();
        emp.setID_empleado(3);

        Nomina nomina = ManejadorNomina.generarNomina(emp);
        System.out.println(nomina.getSalario());
//        System.out.println(nomina);
//        System.out.println(""+ManejadorNomina.getNetoAPagar(nomina)+"hola");
//        System.out.println("------------------");
//        System.out.println( "AFP="+ManejadorNomina.calculaAFP(nomina) +" : SFS="+ ManejadorNomina.calculaSFS(nomina) +" : ISR="+ManejadorNomina.calculaISR(nomina) );
//

    }
}
