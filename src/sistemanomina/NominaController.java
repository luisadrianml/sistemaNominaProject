/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */
package sistemanomina;

import entidades.Deduccion;
import entidades.Empleado;
import entidades.Historico;
import entidades.Ingreso;
import entidades.Meses;
import entidades.TipoSalario;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mysql.MySqlDataBase;
import nomina.Comprobante;
import nomina.ManejadorNomina;
import nomina.Nomina;

/**
 * Clase controladora de la vista de Nomina
 * Esta clase llama a la clase ManejadorNomina y Nomina para recibir los calculos, y se encarga de imprimirlos con la clase Comprobante
 *
 * @author SistemaNominaLJ
 */
public class NominaController implements Initializable {
    /** Atributos que permiten acceso a base de datos e interaccion con usuarios */    
    private Dialogs dg;
    private MySqlDataBase database = new MySqlDataBase();
    private ObservableList<TipoSalario> listTipoSalario = FXCollections.observableArrayList(new TipoSalario(1, "Mensual"), new TipoSalario(2, "Quincenal"), new TipoSalario(3, "Semana"));
    private ActionEvent event = new ActionEvent();
    private ArrayList<Empleado> allEmployee;
    private ObservableList<Meses> listMeses = FXCollections.observableArrayList(new Meses(1, "Enero"), new Meses(2, "Febrero"), new Meses(3, "Marzo"), 
            new Meses(4, "Abril"), new Meses(5, "Mayo"), new Meses(6, "Junio"), new Meses(7, "Julio"), new Meses(8, "Agosto"), new Meses(9, "Septiembre"), new Meses(10, "Octubre"), 
            new Meses(11, "Noviembre"), new Meses(12, "Diciembre"));
    private ObservableList<Integer> listPeriodos;  
    private Empleado empleado;
    private ArrayList<Empleado> arrayEmp;
    private ObservableList<Empleado> listEmpleado;       
    private Comprobante cmp = new Comprobante();
    private Comprobante cmptodos;
    private Historico ht;
    private boolean seleccionadBrowser = false;
    private String seleccionadoBrowser = "";
    private String ANO;

    /** Atributos de la vistas que permiten su manipulacion */
    @FXML
    private ComboBox<Empleado> cmb_ID_nomina;
    
    @FXML
    private ComboBox<TipoSalario> cmb_tipoSalario;
    
    @FXML
    private ComboBox<TipoSalario> cmb_tipoSalario2;
    
    @FXML
    private ComboBox<Meses> cmb_mes_todos;
    
    @FXML
    private ComboBox<Integer> cmb_periodo_todos;
        
    @FXML
    private ComboBox<Integer> cmb_periodo_individual;

    @FXML
    private ComboBox<Meses> cmb_mes_individual;

    /**
     * Metodo para generar la nomina individual del empleado seleccionado, llamando principalmente a la clase Nomina
     * @param event 
     */
    @FXML
    void generarNominaIndividual(ActionEvent event) {
        if (!cmb_ID_nomina.getSelectionModel().isEmpty() && !cmb_tipoSalario.getSelectionModel().isEmpty() &&
                !cmb_mes_individual.getSelectionModel().isEmpty() && !cmb_periodo_individual.getSelectionModel().isEmpty()) {

            switch(cmb_tipoSalario.getValue().getId()) {
                case 1: {
                    switch(cmb_periodo_individual.getValue()) {
                        /**
                         * Switch para determinar el periodo dentro del area de mensual, mensual es uno asi que no es necesario.
                         */
                        case 1: {
                                generadordeNominaFinal();
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (cmb_periodo_individual.getValue()){
                        case 1: { //Primera quincena donde no se descuentan impuestos
                            if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue(), ANO) == null) {
                            generadordeNominaParcial(2.0);
                            } else {
                                dg = new Dialogs(event);
                                dg.errorDialog("No registos o error", null, "No tenemos registros de periodos anteriores o hemos encontrado un error");
                            }
                            break;
                        }
                        case 2: { //Segunda quincena y la final
                            Historico ht;
                            if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue()-1, ANO) != null) {
                                if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue(), ANO) == null) {
                                    generadordeNominaFinalPeriodos(cmb_periodo_individual.getValue().doubleValue(), cmb_periodo_individual.getValue()-1);
                                } else {
                                    dg = new Dialogs(event);
                                    dg.errorDialog("No registos o error", null, "No tenemos registros de periodos anteriores o hemos encontrado un error");
                                }
                            } else {
                                dg = new Dialogs(event); 
                                dg.informationWithoutHeaderDialog("No registos o error", "No tenemos registros de periodos anteriores o hemos encontrado un error");
                            }
                            break;
                        }
                    }
                    break;
                }
                case 3: { // Tipo de salario semanal
                        switch (cmb_periodo_individual.getValue()) {
                            case 1: {  // primera semana de pago
                                    if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue(), ANO) == null) {
                                        generadordeNominaParcial(4.0);
                                    } else {
                                        dg = new Dialogs(event);
                                        dg.errorDialog("No registos o error", null, "No tenemos registros de periodos anteriores o hemos encontrado un error");
                                    }
                                break;
                            }
                            case 2: {// segunda semana de pago
                                    Historico ht;
                                    if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue()-1, ANO) != null) {
                                        if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue(), ANO) == null) {
                                            generadordeNominaParcialAcumulada(4.0, cmb_periodo_individual.getValue()-1);
                                        } else {
                                            dg = new Dialogs(event);
                                            dg.errorDialog("No registos o error", null, "No tenemos registros de periodos anteriores o hemos encontrado un error");
                                        }
                                    } else {
                                        dg = new Dialogs(event); 
                                        dg.informationWithoutHeaderDialog("No registos o error", "No tenemos registros de periodos anteriores o hemos encontrado un error");
                                    }
                                break;
                            }
                            case 3: { // tercera semana de pago
                                    if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue()-2, ANO) != null) {
                                        if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue()-1, ANO) != null) {
                                            if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue(), ANO) == null) {
                                                generadordeNominaParcialAcumulada(4.0, cmb_periodo_individual.getValue()-1);
                                            } else {
                                                dg = new Dialogs(event);
                                                dg.errorDialog("No registos o error", null, "No tenemos registros de periodos anteriores o hemos encontrado un error");
                                            }
                                        } else {
                                            dg = new Dialogs(event);
                                            dg.errorDialog("No registos o error", null, "No tenemos registros de periodos anteriores o hemos encontrado un error");
                                        }
                                    } else {
                                        dg = new Dialogs(event); 
                                        dg.informationWithoutHeaderDialog("No registos o error", "No tenemos registros de periodos anteriores o hemos encontrado un error");
                                    }
                                break;
                            }
                            case 4: { // cuarta semana de pago y final
                                    if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue()-3, ANO) != null) {
                                        if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue()-2, ANO) != null) {
                                            if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue()-1, ANO) != null) {
                                                if (getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), cmb_periodo_individual.getValue(), ANO) == null) {
                                                    generadordeNominaFinalPeriodos(cmb_periodo_individual.getValue().doubleValue(), cmb_periodo_individual.getValue()-1);
                                                } else {
                                                    dg = new Dialogs(event);
                                                }
                                            } else {
                                                dg = new Dialogs(event);
                                                dg.errorDialog("No registos o error", null, "No tenemos registros de periodos anteriores o hemos encontrado un error");
                                            }
                                        } else {
                                            dg = new Dialogs(event);
                                            dg.errorDialog("No registos o error", null, "No tenemos registros de periodos anteriores o hemos encontrado un error");
                                        }
                                    } else {
                                        dg = new Dialogs(event); 
                                        dg.informationWithoutHeaderDialog("No registos o error", "No tenemos registros de periodos anteriores o hemos encontrado un error");
                                    }
                                break;
                            }
                        }
                    break;
                }
                case 4: {
                    break;
                }
            }
        }
        else {
            dg = new Dialogs();
            dg.errorDialog("No campos seleccionado", null, "No ha seleccionado todos los campos");
        }
        
        
    }
    
    /**
     * Metodo que genera la nomina de un empleado con todo y reporte
     */
    public void generadordeNominaFinal() {
        Nomina nomina = ManejadorNomina.generarNomina(empleado,0.0);
        cmp = new Comprobante();
        if (seleccionadBrowser) {
            cmp.setFILE(seleccionadoBrowser);
        }           
        cmp.setId_employee(empleado.getID_empleado());
        cmp.setName_employee(empleado.getNombre());
        cmp.setLastname_employee(empleado.getApellido());
        cmp.setSalary_employee(nomina.getSalario());
        cmp.setMonth(cmb_mes_individual.getValue().getNombre());
        cmp.setCantidadPagos(cmb_periodo_individual.getValue());

        cmp.createandheaderPDF(event);

        cmp.createTable();

        cmp.llenarSalario(nomina.getSalario());

        for (Ingreso ingreso : nomina.getIngresos()) {
            cmp.llenarIngresos(ingreso.getMonto(), ingreso.getNombre());
        }

        Double totalIngresos = 0.0;
        for (Ingreso ingreso : nomina.getIngresos()) {
            totalIngresos += ingreso.getMonto();
        }
        cmp.totalIngresos(totalIngresos);



        Double totalDeduccion = 0.0;

        cmp.llenarDeduccion(ManejadorNomina.calculaAFP(nomina), nomina.getAFP().getNombre());
        cmp.llenarDeduccion(ManejadorNomina.calculaSFS(nomina), nomina.getSFS().getNombre());
        cmp.llenarDeduccion(ManejadorNomina.calculaISR(nomina), nomina.getISR().getNombre());
        totalDeduccion +=ManejadorNomina.calculaAFP(nomina);
        totalDeduccion += ManejadorNomina.calculaSFS(nomina);
        totalDeduccion += ManejadorNomina.calculaISR(nomina);

        for (Deduccion deduccion : nomina.getDeducciones()) {
            cmp.llenarDeduccion(deduccion.getMonto(), deduccion.getNombre());
        }

        for (Deduccion deduccion : nomina.getDeducciones()) {
            totalDeduccion += deduccion.getMonto();
        }
        cmp.totalDeducciones(totalDeduccion);
        cmp.neto(ManejadorNomina.getNetoAPagar(nomina));
        cmp.cerrarDoc();
        cmp.abrirPDF();

        Historico hst = new Historico();
        hst.setDeducciones(ManejadorNomina.getOtrosDeducibles(nomina));
        hst.setId_empleado(empleado.getID_empleado());
        hst.setIngresosMasSalario(ManejadorNomina.getSalarioMasIngresos(nomina, false));
        hst.setMes(cmb_mes_individual.getValue().getId());
        hst.setPeriodo(cmb_periodo_individual.getValue());
        hst.setHorasExtra(ManejadorNomina.getHorasExtras(nomina));
        hst.setSalarioNeto(ManejadorNomina.getNetoAPagar(nomina));
        hst.setSalariobruto(nomina.getSalario());
        hst.setSumaDeducciones(ManejadorNomina.getOtrosDeducibles(nomina)+ManejadorNomina.getSumaImpuestos(nomina));
        hst.setSumaImpuestos(ManejadorNomina.getSumaImpuestos(nomina));
        hst.setSumaIngresos(ManejadorNomina.getNetoSoloIngresosSinHoras(nomina));
        hst.setYear(ANO);

        insertarHistorico(hst);
    }
    
    /**
     * Metodo que genera la nomina final de la que fuese divida en partes (quincenal o semanal)
     * @param partial Parametro que especifica en cuantos pedazos debe partir el sueldo (2 si fuese quincenal, 4 si fuese semanal)
     * @param periodoAnterior Parametro que sirve para conocer cual fue el periodo anterior y tomar los datos desde la base de datos
     */
    public void generadordeNominaFinalPeriodos(Double partial, int periodoAnterior) {
           cmp = new Comprobante();
                   if (seleccionadBrowser) {
            cmp.setFILE(seleccionadoBrowser);
        } 
           Historico hst = new Historico();
           
        Nomina nomina = ManejadorNomina.generarNomina(empleado,0.0);
        Double salariobruto = 0.0;
        Double sumaIngresos = 0.0;
        Double ingresoMasSalario = 0.0;
        Double horasExtra = 0.0;
        Double sumaImpuestos = 0.0;
        Double deducciones= 0.0;
        Double sumaDeducciones= 0.0;
        Double salarioNeto= 0.0;
        
        salariobruto = nomina.getSalario();
        //hst.setSalariobruto(nomina.getSalario());
   
        cmp.setId_employee(empleado.getID_empleado());
        cmp.setName_employee(empleado.getNombre());
        cmp.setLastname_employee(empleado.getApellido());
        cmp.setSalary_employee(nomina.getSalario());
        cmp.setMonth(cmb_mes_individual.getValue().getNombre());
        cmp.setCantidadPagos(cmb_periodo_individual.getValue());

        nomina = ManejadorNomina.generarNomina(empleado, partial);
        
        Historico histo = getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), periodoAnterior, ANO);
        
        horasExtra = ManejadorNomina.getHorasExtras(nomina) + histo.getHorasExtra();
                        
        cmp.createandheaderPDF(event);

        cmp.createTable();

        cmp.llenarSalario(nomina.getSalario());

        for (Ingreso ingreso : nomina.getIngresos()) {
            cmp.llenarIngresos(ingreso.getMonto(), ingreso.getNombre());
        }

        Double totalIngresos = 0.0;
        for (Ingreso ingreso : nomina.getIngresos()) {
            totalIngresos += ingreso.getMonto();
        }
 
        //Double ingresosAnteriores = histo.getHorasExtra()+histo.getSumaIngresos();
        sumaIngresos = histo.getSumaIngresos() + ManejadorNomina.getNetoSoloIngresosSinHoras(nomina);
        //cmp.llenarIngresos(newT, "Ingresos anteriores:");
        cmp.totalIngresos(totalIngresos);
        //hst.setSumaIngresos(ManejadorNomina.getNetoIngresosSinHoras(nomina) + histo.getSumaIngresos());


        Double totalDeduccion = 0.0;
        
        nomina = ManejadorNomina.generarNomina(empleado, nomina.getSalario()+histo.getIngresosMasSalario());
        
        ingresoMasSalario = ManejadorNomina.getSalarioMasIngresos(nomina, false);
        
        cmp.llenarDeduccion(ManejadorNomina.calculaAFP(nomina), nomina.getAFP().getNombre());
        cmp.llenarDeduccion(ManejadorNomina.calculaSFS(nomina), nomina.getSFS().getNombre());
        
        totalDeduccion += ManejadorNomina.calculaAFP(nomina);
        totalDeduccion += ManejadorNomina.calculaSFS(nomina);
        
        sumaImpuestos += ManejadorNomina.calculaAFP(nomina);
        sumaImpuestos += ManejadorNomina.calculaSFS(nomina);
        
        nomina = ManejadorNomina.generarNomina(empleado, partial);
        nomina = ManejadorNomina.generarNomina(empleado, nomina.getSalario()+histo.getIngresosMasSalario()+histo.getHorasExtra());
        
        cmp.llenarDeduccion(ManejadorNomina.calculaISR(nomina, sumaImpuestos), nomina.getISR().getNombre().substring(0,3));
        
        totalDeduccion += ManejadorNomina.calculaISR(nomina, sumaImpuestos);
        sumaImpuestos += ManejadorNomina.calculaISR(nomina, sumaImpuestos);

        for (Deduccion deduccion : nomina.getDeducciones()) {
            cmp.llenarDeduccion(deduccion.getMonto(), deduccion.getNombre());
            deducciones = deduccion.getMonto();
        }

        for (Deduccion deduccion : nomina.getDeducciones()) {
            totalDeduccion += deduccion.getMonto();
        }
        
        nomina = ManejadorNomina.generarNomina(empleado, partial);
        
        sumaDeducciones = sumaImpuestos + deducciones;
        salarioNeto = ManejadorNomina.getNetoAPagar(nomina, sumaImpuestos) + histo.getIngresosMasSalario() + histo.getHorasExtra();
        cmp.totalDeducciones(totalDeduccion);
        cmp.neto(ManejadorNomina.getNetoAPagar(nomina, sumaImpuestos));
        cmp.cerrarDoc();
        cmp.abrirPDF();


        hst.setId_empleado(empleado.getID_empleado());
        hst.setIngresosMasSalario(ingresoMasSalario);
        hst.setMes(cmb_mes_individual.getValue().getId());
        hst.setPeriodo(cmb_periodo_individual.getValue());
        hst.setHorasExtra(horasExtra);
        hst.setSalarioNeto(salarioNeto);
        hst.setSumaDeducciones(sumaDeducciones);
        hst.setSumaImpuestos(sumaImpuestos);
        hst.setDeducciones(deducciones);
        hst.setSumaIngresos(sumaIngresos);
        hst.setSalariobruto(salariobruto);
        hst.setYear(ANO);

        insertarHistorico(hst);
    }
    
    /**
     * Metodo que realiza el generado de nomina de manera parcial si fuese quincenal o semanal. Regularmente usado solo la primera etapa, luego se usa el 
     * <PRE> generadordeNominaParcialAcumulada </PRE>
     * @param partial Parametro de la parte a dividir el sueldo
     */
    public void generadordeNominaParcial(Double partial) {
        cmp = new Comprobante();
                if (seleccionadBrowser) {
            cmp.setFILE(seleccionadoBrowser);
        } 
        Nomina nomina = ManejadorNomina.generarNomina(empleado, 0.0);
        cmp.setId_employee(empleado.getID_empleado());
        cmp.setName_employee(empleado.getNombre());
        cmp.setLastname_employee(empleado.getApellido());
        cmp.setSalary_employee(nomina.getSalario());
        cmp.setMonth(cmb_mes_individual.getValue().getNombre());
        cmp.setCantidadPagos(cmb_periodo_individual.getValue());

        nomina = ManejadorNomina.generarNomina(empleado,partial);

        cmp.createandheaderPDF(event);

        cmp.createTable();

        for (Ingreso ingreso : nomina.getIngresos()) {
                cmp.llenarIngresos(ingreso.getMonto(), ingreso.getNombre());
        }

        Double totalIngresos = 0.0;
        for (Ingreso ingreso : nomina.getIngresos()) {
            totalIngresos += ingreso.getMonto();
        }
        cmp.totalIngresos(totalIngresos);

        cmp.neto(ManejadorNomina.getNetoIngresosConHoras(nomina));
        cmp.cerrarDoc();
        cmp.abrirPDF();
//insert into historico_general(id_empleado, salariobruto, sumaIngresos, ingresosMasSalario, horasExtras, mes, ano, periodo) values

        database.Insert("historico_general(id_empleado, salariobruto, sumaIngresos, ingresoMasSalario, horasExtra, mes, ano, periodo)", 
                empleado.getID_empleado()+","+ManejadorNomina.generarNomina(empleado, 0.0).getSalario()+","+ManejadorNomina.getSumaIngresosSinHoras(nomina)+""
                        + ","+ManejadorNomina.getSalarioMasIngresos(nomina, false)+","+ManejadorNomina.getHorasExtras(nomina) +""
                        + ","+cmb_mes_individual.getValue().getId() + ", '"+ANO+"',"+cmb_periodo_individual.getValue());

    }

    /**
     * Metodo que genera la nomina y reporte del empleado seleccionado pero de manera final, osea el ultimo periodo (2 si es quincenal, en el 4 si es semanal)
     * @param partial Parametro de en cuanto se parte el sueldo
     * @param periodoAnterior Parametro para obtener los datos del periodo anterior, llamandolos desde la base de datos
     */
    public void generadordeNominaParcialAcumulada(Double partial, int periodoAnterior) {
            Historico hist = getHistoricoGeneralPeriodo(empleado, cmb_mes_individual.getValue().getId(), periodoAnterior, ANO);
        cmp = new Comprobante();
                if (seleccionadBrowser) {
            cmp.setFILE(seleccionadoBrowser);
        } 
        Nomina nomina = ManejadorNomina.generarNomina(empleado, 0.0);
        cmp.setId_employee(empleado.getID_empleado());
        cmp.setName_employee(empleado.getNombre());
        cmp.setLastname_employee(empleado.getApellido());
        cmp.setSalary_employee(nomina.getSalario());
        cmp.setMonth(cmb_mes_individual.getValue().getNombre());
        cmp.setCantidadPagos(cmb_periodo_individual.getValue());

        nomina = ManejadorNomina.generarNomina(empleado,partial);

        cmp.createandheaderPDF(event);

        cmp.createTable();

        for (Ingreso ingreso : nomina.getIngresos()) {
                cmp.llenarIngresos(ingreso.getMonto(), ingreso.getNombre());
        }

        Double totalIngresos = 0.0;
        for (Ingreso ingreso : nomina.getIngresos()) {
            totalIngresos += ingreso.getMonto();
        }
        cmp.totalIngresos(totalIngresos);

        cmp.neto(ManejadorNomina.getNetoIngresosConHoras(nomina));
        cmp.cerrarDoc();
        cmp.abrirPDF();
//insert into historico_general(id_empleado, salariobruto, sumaIngresos, ingresosMasSalario, horasExtras, mes, ano, periodo) values

        database.Insert("historico_general(id_empleado, salariobruto, sumaIngresos, ingresoMasSalario, horasExtra, mes, ano, periodo)", 
                empleado.getID_empleado()+","+ManejadorNomina.generarNomina(empleado, 0.0).getSalario()+","+(ManejadorNomina.getSumaIngresosSinHoras(nomina)+hist.getSumaIngresos())+""
                        + ","+(ManejadorNomina.getSalarioMasIngresos(nomina, false)+hist.getIngresosMasSalario())+","+(ManejadorNomina.getHorasExtras(nomina)+hist.getHorasExtra()) +""
                        + ","+cmb_mes_individual.getValue().getId() + ", '"+ANO+"',"+cmb_periodo_individual.getValue());

    }
        
    /**
     * Metodo que permite generar la nomina final (mensual) para todos los empleados, y su reporte en el sistema con deducciones e impuestos
     * @param empleado
     * Parametro empleado permite generar la nomina de ese empleado (es decir, obtener sus valores, etc...)
     * @param cmp
     * Parametro del comprobante que se usa en ese momento
     */
    public void generadordeNominaFinal(Empleado empleado, Comprobante cmp) {
    Nomina nomina = ManejadorNomina.generarNomina(empleado,0.0);

    Double totalIngresos = 0.0;
    for (Ingreso ingreso : nomina.getIngresos()) {
        totalIngresos += ingreso.getMonto();
    }

    Double totalDeduccion = 0.0;
    totalDeduccion +=ManejadorNomina.calculaAFP(nomina);
    totalDeduccion += ManejadorNomina.calculaSFS(nomina);
    totalDeduccion += ManejadorNomina.calculaISR(nomina);

    Double impuestos = totalDeduccion;

    for (Deduccion deduccion : nomina.getDeducciones()) {
        totalDeduccion += deduccion.getMonto();
    }

    cmp.fillNecesario(empleado.getID_empleado()+ " ("+empleado.getNombre()+")", 
            nomina.getSalario().toString(), ManejadorNomina.getNetoSoloIngresosConHoras(nomina).toString(), ManejadorNomina.getSumaImpuestos(nomina).toString(), 
            ManejadorNomina.getOtrosDeducibles(nomina).toString(), ManejadorNomina.getNetoAPagar(nomina).toString());


    Historico hst = new Historico();
    hst.setDeducciones(ManejadorNomina.getOtrosDeducibles(nomina));
    hst.setId_empleado(empleado.getID_empleado());
    hst.setIngresosMasSalario(ManejadorNomina.getSalarioMasIngresos(nomina, false));
    hst.setMes(cmb_mes_todos.getValue().getId());
    hst.setPeriodo(cmb_periodo_todos.getValue());
    hst.setHorasExtra(ManejadorNomina.getHorasExtras(nomina));
    hst.setSalarioNeto(ManejadorNomina.getNetoAPagar(nomina));
    hst.setSalariobruto(nomina.getSalario());
    hst.setSumaDeducciones(ManejadorNomina.getOtrosDeducibles(nomina)+ManejadorNomina.getSumaImpuestos(nomina));
    hst.setSumaImpuestos(ManejadorNomina.getSumaImpuestos(nomina));
    hst.setSumaIngresos(ManejadorNomina.getNetoSoloIngresosSinHoras(nomina));
    hst.setYear(ANO);

         System.out.println("Llegue aqui sin explotar");
    insertarHistorico2(hst);
}
    
     /**
      * Metodo que recibe el clic del boton para determinar cuando debe ejecutarse el calculo de nomina para todos los empleados
      * @param event Parametro al hacer clic al boton 
      */
    @FXML
    void generarNominatotal(ActionEvent event) {
        if (!cmb_tipoSalario2.getSelectionModel().isEmpty() && !cmb_mes_todos.getSelectionModel().isEmpty() && !cmb_periodo_todos.getSelectionModel().isEmpty()) {
            switch(cmb_tipoSalario2.getValue().getId()) {
                case 1: {
                    switch(cmb_periodo_todos.getValue()) {
                        /**
                         * Switch para determinar el periodo dentro del area de mensual, mensual es uno asi que no es necesario.
                         */
                        case 1: {
                            cmp = new Comprobante();
                            cmp.setCantidadPagos(cmb_periodo_todos.getValue());
                            cmp.setMonth(cmb_mes_todos.getValue().getNombre());
                            cmp.createHeader(event);
                            cmp.createTableEmpleado();
                            for (Empleado empleado : allEmployee) {
                                generadordeNominaFinal(empleado, cmp);
                            }
                            cmp.cerrarTabla();
                            cmp.soloCerrarDocFinal();
                            cmp.abrirPDF();
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    break;
                }
                
            }
        } else {
            dg = new Dialogs();
            dg.errorDialog("No campos seleccionado", null, "No ha seleccionado todos los campos");        }
    }
    
    /**
     * Metodo que permite buscar el Historico de la tabla de la base de datos para comparaciones, y comprobaciones de que existen registros de los periodos anteriores
     * @param emp
     * Parametro de empleado que permite identificar ID, el cual se estara trabajando.
     * @param mes
     * @param periodo
     * @param ano
     * @return 
     */
    Historico getHistoricoGeneralPeriodo(Empleado emp, int mes, int periodo, String ano) {
        ht = new Historico();
        ResultSet rs = database.Select("*", "historico_general","id_empleado="+emp.getID_empleado()+" and "
                + "mes="+mes+" and ano = '"+ano+"' and periodo = "+periodo);
        try {
            if (rs != null) {
// historico_general(id_empleado, salariobruto, sumaIngresos, ingresoMasSalario, horasExtra, mes, ano, periodo) values
                if  (rs.next()) {
                    ht.setId_empleado(rs.getInt("id_empleado"));
                    ht.setHorasExtra(rs.getDouble("horasExtra"));
                    ht.setSalariobruto(rs.getDouble("salariobruto"));
                    ht.setSumaIngresos(rs.getDouble("sumaIngresos"));
                    ht.setPeriodo(rs.getInt("periodo"));
                    ht.setIngresosMasSalario(rs.getDouble("ingresoMasSalario"));
                    ht.setYear(rs.getString("ano")); 
                    ht.setMes(rs.getInt("mes"));
                } else {
                    ht = null;
                }

            } else {
                ht = null;
            }
        } catch(SQLException e) {
            Dialogs dg = new Dialogs();
            dg.exceptionDialog("Error en la aplicacion", null, "Hemos encontrado un error en la aplicacion", e);
        }
        
        return ht;
    }
    
    /**
     * Metodo que permite insertar en la base de datos los datos que sirven como historico de los acontecimientos sucedidos en base a la generacion de nomina
     * @param hst 
     * Parametro hst es una entidad Historico que sirve como el objeto que sera introducido en la base de datos, por lo tanto debe contener todos sus atributos.
     */
    public void insertarHistorico(Historico hst) {
        Connection con = database.getConn();
        if (hst!=null) {
// id_empleado int(11), salariobruto decimal(15,2), sumaIngresos decimal(15,2), ingresoMasSalario decimal(15,2), horasExtra decimal(15,2), 
//sumaImpuestos decimal(15,2), deducciones decimal(15,2), sumaDeducciones decimal(15,2), salarioNeto decimal(15,2), mes int(2), ano varchar(4), periodo int(11), 
            try{
            PreparedStatement stm = con.prepareStatement("INSERT INTO HISTORICO_GENERAL VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            stm.setInt(1, hst.getId_empleado());
            stm.setDouble(2, hst.getSalariobruto());
            stm.setDouble(3, hst.getSumaIngresos());
            stm.setDouble(4, hst.getIngresosMasSalario());
            stm.setDouble(5, hst.getHorasExtra());
            stm.setDouble(6, hst.getSumaImpuestos());
            stm.setDouble(7, hst.getDeducciones());
            stm.setDouble(8, hst.getSumaDeducciones());
            stm.setDouble(9, hst.getSalarioNeto());
            stm.setInt(10, hst.getMes());
            stm.setString(11, hst.getYear());
            stm.setInt(12, hst.getPeriodo());
            stm.executeUpdate();
            } catch( SQLException ex ){
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Metodo que permite insertar en la base de datos de historico de todos los sueldos, y pagos de los empleados de tipo mensual
     * @param hst Parametro tipo Historico que contiene los datos a insertar en la base de datos
     */
    public void insertarHistorico2(Historico hst) {
        Connection con = database.getConn();
        if (hst!=null) {
// id_empleado int(11), salariobruto decimal(15,2), sumaIngresos decimal(15,2), ingresoMasSalario decimal(15,2), horasExtra decimal(15,2), 
//sumaImpuestos decimal(15,2), deducciones decimal(15,2), sumaDeducciones decimal(15,2), salarioNeto decimal(15,2), mes int(2), ano varchar(4), periodo int(11), 
            try{
            PreparedStatement stm = con.prepareStatement("INSERT INTO HISTORICO_GENERAL_TODOS VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            stm.setInt(1, hst.getId_empleado());
            stm.setDouble(2, hst.getSalariobruto());
            stm.setDouble(3, hst.getSumaIngresos());
            stm.setDouble(4, hst.getIngresosMasSalario());
            stm.setDouble(5, hst.getHorasExtra());
            stm.setDouble(6, hst.getSumaImpuestos());
            stm.setDouble(7, hst.getDeducciones());
            stm.setDouble(8, hst.getSumaDeducciones());
            stm.setDouble(9, hst.getSalarioNeto());
            stm.setInt(10, hst.getMes());
            stm.setString(11, hst.getYear());
            stm.setInt(12, hst.getPeriodo());
            stm.executeUpdate();
            } catch( SQLException ex ){
                ex.printStackTrace();
            }
        }
    }

    /**
     * Metodo que inicializa el controlador de la clase
     * @param url Parametro por defecto del metodo
     * @param rb Parametro por defecto del metodo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ANO = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        
        cmp = new Comprobante();
        cmb_ID_nomina.setDisable(true);

        llenarTipoSalarios();
        llenarMeses();
        
        cmb_tipoSalario.valueProperty().addListener(new ChangeListener<TipoSalario>() {

            @Override
            public void changed(ObservableValue<? extends TipoSalario> observable, TipoSalario oldValue, TipoSalario newValue) {
                if (!cmb_tipoSalario.getSelectionModel().isEmpty()) {
                    cmb_mes_individual.setDisable(false);
                    llenarIDEmpleado();
                }
            }
            
        });
        
        cmb_mes_individual.valueProperty().addListener(new ChangeListener<Meses>() {

            @Override
            public void changed(ObservableValue<? extends Meses> observable, Meses oldValue, Meses newValue) {
                if (!cmb_mes_individual.getSelectionModel().isEmpty()) {
                    cmb_periodo_individual.setDisable(false);
                    if (cmb_tipoSalario.getValue().getId() == 1) {
                        llenarPeriodos(1);
                    } else if (cmb_tipoSalario.getValue().getId() == 2) {
                        llenarPeriodos(2);
                    } else if (cmb_tipoSalario.getValue().getId() == 3) {
                        llenarPeriodos(4);
                    } else if (cmb_tipoSalario.getValue().getId() == 4) {
                        llenarPeriodos(5);
                    }
                } 
            }
        
        });
        
        cmb_periodo_individual.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (!cmb_periodo_individual.getSelectionModel().isEmpty()) {
                    cmb_ID_nomina.setDisable(false);
                }
            }
            
        });
        
        cmb_tipoSalario2.valueProperty().addListener(new ChangeListener<TipoSalario>() {

            @Override
            public void changed(ObservableValue<? extends TipoSalario> observable, TipoSalario oldValue, TipoSalario newValue) {
                if (!cmb_tipoSalario2.getSelectionModel().isEmpty()) {
                    cmb_mes_todos.setDisable(false);
                    conocerIDEmpleado();
                }
            }
            
        });
        
        cmb_mes_todos.valueProperty().addListener(new ChangeListener<Meses>() {

            @Override
            public void changed(ObservableValue<? extends Meses> observable, Meses oldValue, Meses newValue) {
                            if (!cmb_mes_todos.getSelectionModel().isEmpty()) {
                            cmb_periodo_todos.setDisable(false);
                    if (cmb_tipoSalario2.getValue().getId() == 1) {
                        llenarPeriodos(1);
                    } else if (cmb_tipoSalario2.getValue().getId() == 2) {
                        llenarPeriodos(2);
                    } else if (cmb_tipoSalario2.getValue().getId() == 3) {
                        llenarPeriodos(4);
                    } else if (cmb_tipoSalario2.getValue().getId() == 4) {
                        llenarPeriodos(5);
                    }
                } 
            }
            
        });
        
        
        cmb_ID_nomina.valueProperty().addListener(new ChangeListener<Empleado>() {

            @Override
            public void changed(ObservableValue<? extends Empleado> observable, Empleado oldValue, Empleado newValue) {
                empleado = newValue;
            }
            
        });
    }    
    
    /**
     * Metodo que realiza el llenado de los ComboBox de los Tipos de Salario, usando los atributos por defecto de esta clase
     */
    private void llenarTipoSalarios() {
        cmb_tipoSalario.setItems(listTipoSalario);
        ObservableList<TipoSalario> listTipoSalario2 = FXCollections.observableArrayList(new TipoSalario(1, "Mensual"));
        cmb_tipoSalario2.setItems(listTipoSalario2);
    }
    
    /**
     * Metodo que llena los ComboBox de acuerdo al Tipo de Salario seleccionado
     * @param i Parametro que determina si es mensual, quincenal o semanal.
     */
    private void llenarPeriodos(int i) {
        cmb_periodo_individual.getSelectionModel().clearSelection();
        cmb_periodo_todos.getSelectionModel().clearSelection();
        switch (i) {
            case 1: {
                
                listPeriodos = FXCollections.observableArrayList(new Integer(1));
                cmb_periodo_individual.setItems(listPeriodos);
                cmb_periodo_individual.getSelectionModel().select(0);
                
                cmb_periodo_todos.setItems(listPeriodos);
                cmb_periodo_todos.getSelectionModel().select(0);
                break;
            }
            case 2: {
                
                listPeriodos = FXCollections.observableArrayList(new Integer(1), new Integer(2));
                cmb_periodo_individual.setItems(listPeriodos);
                cmb_periodo_todos.setItems(listPeriodos);
                break;
            }
            case 4: {
                                
                listPeriodos = FXCollections.observableArrayList(new Integer(1), new Integer(2), new Integer(3), new Integer(4));
                cmb_periodo_individual.setItems(listPeriodos);
                cmb_periodo_todos.setItems(listPeriodos);
                break;
            }
            case 5: {
                                // Pago diario
                listPeriodos = FXCollections.observableArrayList(new Integer(1));
                cmb_periodo_individual.setItems(listPeriodos);
                cmb_periodo_todos.setItems(listPeriodos);
                break;
            }
        }
    }

    /**
     * Metodo que realiza la accion de abrir el Browser para la seleccion de carpeta donde guardar el reporte 
     * @param event Parametro que contiene los datos del boton seleccionado, y de la Stage (Permite que el boton lo llame directamente al metodo)
     */
    @FXML
    public void browserbtn(ActionEvent event) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final File selectedDirectory = directoryChooser.showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
        if (selectedDirectory != null) {
            seleccionadBrowser = true;
            seleccionadoBrowser = selectedDirectory.getAbsolutePath()+"\\";
        } else {
            //cmp.setFILE(null);
        }
    }
    
    /**
     * Metodo para llenado de combobox
     */
    private void llenarMeses() {
        cmb_mes_individual.getSelectionModel().clearSelection();
        cmb_mes_todos.getSelectionModel().clearSelection();
        cmb_mes_todos.setItems(listMeses);
        cmb_mes_individual.setItems(listMeses);
    }
    
    /**
     * Metodo para obtener los ID de todos los empleados
     */
    private void conocerIDEmpleado() {
         arrayEmp = new ArrayList<>();
        
        ResultSet rs = database.Select(" empleado_admin.id, empleado_personal.nombre, empleado_personal.apellido,empleado_admin.id_estado", "empleado_admin, empleado_personal",
                "empleado_admin.id = empleado_personal.id_empleado and empleado_admin.id_estado = 1 and empleado_admin.tipo_salario=" + cmb_tipoSalario2.getValue().getId());
        try {
            while(rs.next()) {
                empleado = new Empleado();
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setID_empleado(rs.getInt("id"));
                arrayEmp.add(empleado);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            dg.exceptionDialog("Error - Base de datos", "Errores encontrados", "El programa ha encontrado un problema con la base de datos. Por favor reinicie, o chequee el motor de base de datos.", ex);

        }
        
                if (arrayEmp.isEmpty()) {     
            dg.informationWithoutHeaderDialog("No registros", "El campo seleccionado no dispone de registros");                    
        } else {
                allEmployee = arrayEmp;
                }

        
    }
    
    /**
     * Metodo para llenar el Combobox con todos los ID de empleados, espeficamente seleccionados por su tipo de salario
     */
    private void llenarIDEmpleado() {
        arrayEmp = new ArrayList<>();
        
        ResultSet rs = database.Select(" empleado_admin.id, empleado_personal.nombre, empleado_personal.apellido,empleado_admin.id_estado", "empleado_admin, empleado_personal",
                "empleado_admin.id = empleado_personal.id_empleado and empleado_admin.id_estado = 1 and empleado_admin.tipo_salario=" + cmb_tipoSalario.getValue().getId());
        try {
            while(rs.next()) {
                empleado = new Empleado();
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setID_empleado(rs.getInt("id"));
                arrayEmp.add(empleado);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            dg.exceptionDialog("Error - Base de datos", "Errores encontrados", "El programa ha encontrado un problema con la base de datos. Por favor reinicie, o chequee el motor de base de datos.", ex);

        }
        
                if (arrayEmp.isEmpty()) {     
            dg.informationWithoutHeaderDialog("No registros", "El campo seleccionado no dispone de registros");                    
        } else {
                    listEmpleado = FXCollections.observableArrayList(arrayEmp);
        cmb_ID_nomina.setItems(listEmpleado);
                }

        

    }
      
}
