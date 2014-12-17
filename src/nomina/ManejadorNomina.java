/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */
package nomina;

import entidades.Deduccion;
import entidades.Empleado;
import entidades.Ingreso;
import mysql.MySqlDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase que maneja la clase de Nomina, siendo esta la que realiza los procedimientos mientras que Nomina es un recipiente.
 */
public final class ManejadorNomina {

    private static List<Impuesto> sImpuestos;
    private static int PUNTO_DECIMAL = 2;

    /**
     * Metodo que genera la nomina del empleado que se envia
     * @param emp Parametro del empleado al calcular nomina
     * @param i Especificacion de factor de salario, de si debe ser dividido. Se usa en quincena, y semanal.
     * @return Retorna el objeto nomina con sus atributos configurados
     */
    public static Nomina generarNomina(Empleado emp, Double i){
        MySqlDataBase db = new MySqlDataBase();
        Connection con = db.getConn();

        if( null == sImpuestos )
            sImpuestos = getAllImpuestos(con);

        Double salario = 0.0;

            try{
            PreparedStatement stm = con.prepareStatement("SELECT NOMBRE,MONTO FROM EMPLEADO_ADMIN EA JOIN CARGO C ON EA.ID_CARGO = C.ID WHERE EA.ID = ?");
            stm.setInt(1,emp.getID_empleado());

            ResultSet result = stm.executeQuery();
            if(result.next())
                salario = result.getDouble("MONTO");

        }catch( SQLException ex ){
            ex.printStackTrace();
        }

        if (i == 2.0 || i == 4.0) {
            salario = salario/i;
        } else if (i != 0) {
            salario = i;
        }

        Nomina nomina = new Nomina();
        nomina.setEmpleado(emp);
        nomina.setSalario(salario);

        nomina.setIngresos(getIngresos(con,emp.getID_empleado()));
        nomina.setDeducciones(getDeducciones(con,emp.getID_empleado()));
        if (i == 0.0 || (i!=2) || (i!=4)) {
            injectaImpuestos(nomina);
        }

        return nomina;
    }

    /**
     * Metodo que genera una lista nomina de una lista de empleados que se envia
     * @param emps Parametros de la lista de empleados a calcular nomina
     * @return Retorna una lista de objeto nomina con sus atributos configurados
     */
    public static List<Nomina> generarNomina(List<Empleado> emps ){
        if( null == emps || 0 == emps.size() )
            return Collections.EMPTY_LIST;
        List<Nomina> nominas = new ArrayList<>();
        for( Empleado emp : emps )
            nominas.add( generarNomina(emp, 0.0));
        return nominas;
    }
    
    /**
     * Metodo que da la suma de los ingresos de un empleado sin las horas extras
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna un valor numerico double
     */
    public static Double getSumaIngresosSinHoras(Nomina nomina) {
        return getSalarioMasIngresos(nomina, false) - nomina.getSalario();
    }
    
    /**
     * Metodo que da la suma de ingresos de un empleado con las horas extras
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna un valor numerico doble
     */
    public static Double getSumaIngresosConHoras(Nomina nomina) {
        return getSalarioMasIngresos(nomina, true) - nomina.getSalario();
    }

    /**
     * Metodo que da el salario del empleado mas sus ingresos 
     * @param nomina Nomina a la que se realiza el proceso
     * @param incluirHorasExtras Parametro booleano para especificar si se necesitan las horas extras o no
     * @return Retorna un valor numerico double
     */
    public static Double getSalarioMasIngresos(Nomina nomina, boolean incluirHorasExtras){
        Double montoTotal = nomina.getSalario();
        if( null != nomina.getIngresos() ){
            for( Ingreso ingreso : nomina.getIngresos() ){
                if( !incluirHorasExtras && ingreso.getNombre().equalsIgnoreCase("HORAS_EXTRAS") )
                    continue;
                montoTotal += ingreso.getMonto();
            }
        }
        return montoTotal;
    }

    /**
     * Metodo que da la suma de los deducibles del empleado (no incluye impuestos)
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna un valor numerico double
     */
    public static Double getOtrosDeducibles(Nomina nomina){
        // Double montoTotal = nomina.getSalario();
        Double montoTotal = 0.0;
        if( null != nomina.getDeducciones() ){
            for( Deduccion deduccion : nomina.getDeducciones() ){
                montoTotal += deduccion.getMonto();
            }
        }
        return round(montoTotal, PUNTO_DECIMAL);
    }

    /**
     * Metodo que calcula el impuesto sfs de un empleado
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna el valor numerico double
     */
    public static Double calculaSFS(Nomina nomina){
        return round(getSalarioMasIngresos(nomina,false) * nomina.getSFS().getFactor(), PUNTO_DECIMAL);
    }

    /**
     * Metodo que calcula el impuesto afp de un empleado
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna el valor numerico double
     */
    public static Double calculaAFP(Nomina nomina){
        return round(getSalarioMasIngresos(nomina,false) * nomina.getAFP().getFactor(), PUNTO_DECIMAL);
    }

    /**
     * Metodo que calcula el impuesto isr de un empleado
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna el valor numerico double
     */
    public static Double calculaISR(Nomina nomina){
        Double salarioReal = getSalarioMasIngresos(nomina,true) - (calculaSFS(nomina) + calculaAFP(nomina) );
        System.out.println("salarioReal="+salarioReal);
        Double salarioAnual = round(salarioReal * 12, PUNTO_DECIMAL);
        System.out.println("salarioAnual="+salarioAnual);


        ISR isr = nomina.getISR();
        Double excedente = Math.abs( isr.getDesdeSalario() - salarioAnual );
        System.out.println("excedente="+excedente);

        Double isrAnual = excedente * isr.getFactor();
        System.out.println("isrAnual="+isrAnual);

        Double isrMasExcesoPorIsr = isrAnual + isr.getExceso();
        System.out.println("isrMasExcesoPorIsr="+isrMasExcesoPorIsr);

        Double isrMensual = round(isrMasExcesoPorIsr / 12, PUNTO_DECIMAL);
        System.out.println("isrMensual="+isrMensual);

        return isrMensual;
    }
    
    /**
     * Metodo que calcula el isr de un empleado con un parametro extra, especificamente usado para calculo de isr 
     * cuando ya se conoce el AFP y SFS 
     * @param nomina Nomina a la que se realiza el proceso
     * @param sumadeSFSyAFP Valor de la suma de impuestos a la que reducir esta para calcular ISR
     * @return Retorna el impuesto en valor numerico double
     */
    public static Double calculaISR(Nomina nomina, Double sumadeSFSyAFP){
        Double salarioReal = getSalarioMasIngresos(nomina,true) - sumadeSFSyAFP;
        System.out.println("salarioReal="+salarioReal);
        Double salarioAnual = round(salarioReal * 12, PUNTO_DECIMAL);
        System.out.println("salarioAnual="+salarioAnual);


        ISR isr = nomina.getISR();
        Double excedente = Math.abs( isr.getDesdeSalario() - salarioAnual );
        System.out.println("excedente="+excedente);

        Double isrAnual = excedente * isr.getFactor();
        System.out.println("isrAnual="+isrAnual);

        Double isrMasExcesoPorIsr = isrAnual + isr.getExceso();
        System.out.println("isrMasExcesoPorIsr="+isrMasExcesoPorIsr);

        Double isrMensual = round(isrMasExcesoPorIsr / 12, PUNTO_DECIMAL);
        System.out.println("isrMensual="+isrMensual);

        return isrMensual;
    }

    /**
     * Metodo que da el total a deducir de un empleado
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna el total a deducir del empleado (los impuestos mas otros)
     */
    public static Double getTotalADeducir(Nomina nomina){
        return round(calculaSFS(nomina) + calculaAFP(nomina) + calculaISR(nomina) + getOtrosDeducibles(nomina), PUNTO_DECIMAL);
    }

    /**
     * Metodo que da el valor neto a pagar a un empleado, su salario con ingresos menos el total a deducir
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna el valor numerico double
     */
    public static Double getNetoAPagar(Nomina nomina){
        return round(getSalarioMasIngresos(nomina,true) - getTotalADeducir(nomina), PUNTO_DECIMAL);
    }
    
    /**
     * Metodo que da el valor neto a pagar de un empleado, recibiendo los impuestos ya planteados
     * @param nomina Nomina a la que se realiza el proceso
     * @param impuestosSueldoCompleto Valor de impuestos hecho a un sueldo superior al que se realiza el valor neto
     * @return Retorna el valor neto numerico en double
     */
    public static Double getNetoAPagar(Nomina nomina, Double impuestosSueldoCompleto){
        return round(getSalarioMasIngresos(nomina,true) - (impuestosSueldoCompleto + getOtrosDeducibles(nomina)), PUNTO_DECIMAL);
    }
    
    /**
     * Metodo que da los valores de ingresos con el salario sin sus horas
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna el valor de ingresos con salario sin horas
     */
    public static Double getNetoIngresosSinHoras(Nomina nomina) {
        return getSalarioMasIngresos(nomina, false);
    }
    
    /**
     * Metodo que da los solo valores de ingresos sin sus horas
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna el valor de ingresos sin horas
     */
    public static Double getNetoSoloIngresosSinHoras(Nomina nomina) {
        return getSalarioMasIngresos(nomina, false) - nomina.getSalario();
    }
    
    /**
     * Metodo que da los valores de ingresos con sus horas
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna el valor de ingresos con horas
     */
    public static Double getNetoSoloIngresosConHoras(Nomina nomina) {
        return getSalarioMasIngresos(nomina, true) - nomina.getSalario();
    }
    
    /**
     * Metodo que da los valores de ingresos con salario y con sus horas
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna el valor de ingresos con salario y las horas
     */
    public static Double getNetoIngresosConHoras(Nomina nomina) {
        return getSalarioMasIngresos(nomina, true);
    }
    
    /**
     * Metodo que da las horas extras
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna el valor numerico
     */
    public static Double getHorasExtras(Nomina nomina) {
        return getSalarioMasIngresos(nomina, true) - getSalarioMasIngresos(nomina, false);
    }
    
    /**
     * Metodo que da la suma de todos los impuestos
     * @param nomina Nomina a la que se realiza el proceso
     * @return Retorna un valor numerico 
     */
    public static Double getSumaImpuestos(Nomina nomina) {
        return round(calculaAFP(nomina) + calculaISR(nomina) + calculaSFS(nomina), PUNTO_DECIMAL);
    }

    /**
     * Metodo que injecta los impuestos en la nomina
     * @param nomina Nomina a la que se injectaran dichos impuestos
     */
    private static void injectaImpuestos(Nomina nomina){
        try {
            for (Impuesto impuesto : sImpuestos) {
                if (impuesto instanceof ISR) {
                    if (nomina.getISR() == null && esEligeRangoDeISR(nomina.getSalario(), (ISR) impuesto))
                        nomina.setISR((ISR) impuesto);
                }
                if (impuesto.getNombre().equalsIgnoreCase("SFS"))
                    nomina.setSFS((Impuesto)impuesto.clone());
                else if (impuesto.getNombre().equalsIgnoreCase("AFP"))
                    nomina.setAFP((Impuesto) impuesto.clone());
            }
        }catch ( Exception ex ){
            ex.printStackTrace();
        }
    }

    /**
     * Metodo que encuentra el rango del ISR
     * @param salario Monto del empleado para determinar si aplica
     * @param isr Impuesto al que se le realiza la determinacion con sus respectivos factores
     * @return Retorna el valor numerico
     */
    private static boolean esEligeRangoDeISR(Double salario,ISR isr){
        Double salarioAnual = Math.ceil(salario*12);
        Double maxRango = isr.getHastaSalario() == 0.0 ? Double.MAX_VALUE : isr.getHastaSalario();
        if( isr.getDesdeSalario() <= salarioAnual && salarioAnual <= maxRango )
            return true;
        return false;
    }

    /**
     * Metodo que trae todos los impuestos de un empleado
     * @param con Conexion de base datos
     * @return Retorna una lista de los impuestos
     */
    private static List<Impuesto> getAllImpuestos(Connection con){
        final List<Impuesto> impuestos = new ArrayList<>();

        try{
            PreparedStatement stm = con.prepareStatement("select * from impuesto order by desde_salario asc");
            ResultSet result=  stm.executeQuery();
            while( result.next() ){

                String nombre = result.getString( "nombre" );
                Double desdeSalario = result.getDouble("desde_salario");
                Double hastSalario = result.getDouble("hasta_salario");
                Double exceso = result.getDouble( "exceso" );
                Double factor = result.getDouble( "factor" );

                if( !"SFS".equalsIgnoreCase(nombre) && !"AFP".equalsIgnoreCase(nombre) )
                    impuestos.add( new ISR(nombre,factor,desdeSalario,hastSalario,exceso) );
                else
                    impuestos.add( new Impuesto(nombre,factor) );
            }
        }catch( SQLException ex ){
            ex.printStackTrace();
        }
        return impuestos;
    }

    /**
     * Metodo que trae todo los ingresos de un empleado
     * @param con Conexion de base de datos
     * @param empleado Empleado al que se le buscaran ingresos
     * @return Retorna una lista de ingresos
     */
    private static List<Ingreso> getIngresos(Connection con,int empleado){
        final String query = "select ti.nombre ingreso, ie.monto from ingresos_emp ie join tipos_ingresos ti on ie.tipo_ingreso = ti.id where ie.id_empleado = "+empleado;
        final List<Ingreso> ingresos = new ArrayList<>();

        try{
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet result=  stm.executeQuery();
            while( result.next() ){

                String nombre = result.getString( "ingreso" );
                Double monto = result.getDouble("monto");
                ingresos.add( new Ingreso(nombre,monto) );
            }
        }catch( SQLException ex ){
            ex.printStackTrace();
        }
        return ingresos;
    }

    /**
     * Metodo que trae todas las deducciones de un empleado
     * @param con Conexion con la base de datos
     * @param empleado Empleado al que se le buscan las deducciones
     * @return Retorna una lista de deducciones
     */
    private static List<Deduccion> getDeducciones(Connection con, int empleado ){
        final String query = "select td.nombre deduccion, de.monto from deduccioness_emp de join tipos_descuentos td on de.tipo_deduccion = td.id where de.id_empleado = "+empleado;
        final List<Deduccion> ingresos = new ArrayList<>();

        try{
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet result=  stm.executeQuery();
            while( result.next() ){
                String nombre = result.getString( "deduccion" );
                Double monto = result.getDouble("monto");
                ingresos.add( new Deduccion(nombre,monto) );
            }
        }catch( SQLException ex ){
            ex.printStackTrace();
            
        }
        return ingresos;
    }
   
    /**
     * Metodo para redondear numeros a ciertos ceros del punto decimal
     * @param value Valor a redondear
     * @param places Cantidad de ceros despues del punto se van a redondear
     * @return Retorna el valor en double redondeado a los puntos decimales expresados
     */
    public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
}

}
