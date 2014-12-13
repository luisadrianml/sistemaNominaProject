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
 * Created by pc166 on 10/12/2014.
 */
public final class ManejadorNomina {

    private static List<Impuesto> sImpuestos;

    public static Nomina generarNomina(Empleado emp){
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

        Nomina nomina = new Nomina();
        nomina.setEmpleado(emp);
        nomina.setSalario(salario);

        nomina.setIngresos(getIngresos(con,emp.getID_empleado()));
        nomina.setDeducciones(getDeducciones(con,emp.getID_empleado()));
        injectaImpuestos(nomina);

        return nomina;
    }


    public static List<Nomina> generarNomina(List<Empleado> emps ){
        if( null == emps || 0 == emps.size() )
            return Collections.EMPTY_LIST;
        List<Nomina> nominas = new ArrayList<>();
        for( Empleado emp : emps )
            nominas.add( generarNomina(emp));
        return nominas;
    }


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

    public static Double getOtrosDeducibles(Nomina nomina){
        // Double montoTotal = nomina.getSalario();
        Double montoTotal = 0.0;
        if( null != nomina.getDeducciones() ){
            for( Deduccion deduccion : nomina.getDeducciones() ){
                montoTotal += deduccion.getMonto();
            }
        }
        return montoTotal;
    }


    public static Double calculaSFS(Nomina nomina){
        return Math.floor(getSalarioMasIngresos(nomina,false) * nomina.getSFS().getFactor());
    }


    public static Double calculaAFP(Nomina nomina){
        return Math.floor(getSalarioMasIngresos(nomina,false) * nomina.getAFP().getFactor());
    }


    public static Double calculaISR(Nomina nomina){
        Double salarioReal = getSalarioMasIngresos(nomina,true) - (calculaSFS(nomina) + calculaAFP(nomina) );
        System.out.println("salarioReal="+salarioReal);
        Double salarioAnual = Math.floor(salarioReal * 12);
        System.out.println("salarioAnual="+salarioAnual);


        ISR isr = nomina.getISR();
        Double excedente = Math.abs( isr.getDesdeSalario() - salarioAnual );
        System.out.println("excedente="+excedente);

        Double isrAnual = excedente * isr.getFactor();
        System.out.println("isrAnual="+isrAnual);

        Double isrMasExcesoPorIsr = isrAnual + isr.getExceso();
        System.out.println("isrMasExcesoPorIsr="+isrMasExcesoPorIsr);

        Double isrMensual = Math.floor(isrMasExcesoPorIsr / 12);
        System.out.println("isrMensual="+isrMensual);

        return isrMensual;
    }


    public static Double getTotalADeducir(Nomina nomina){
        return calculaSFS(nomina) + calculaAFP(nomina) + calculaISR(nomina) + getOtrosDeducibles(nomina);
    }


    public static Double getNetoAPagar(Nomina nomina){
        return getSalarioMasIngresos(nomina,true) - getTotalADeducir(nomina);
    }


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


    private static boolean esEligeRangoDeISR(Double salario,ISR isr){
        Double salarioAnual = Math.ceil(salario*12);
        Double maxRango = isr.getHastaSalario() == 0.0 ? Double.MAX_VALUE : isr.getHastaSalario();
        if( isr.getDesdeSalario() <= salarioAnual && salarioAnual <= maxRango )
            return true;
        return false;
    }



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



}
