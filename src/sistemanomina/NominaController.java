/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import entidades.Deduccion;
import entidades.Empleado;
import entidades.Ingreso;
import entidades.TipoSalario;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
 * FXML Controller class
 *
 * @author pc167
 */
public class NominaController implements Initializable {
    Dialogs dg;
    //String fileDirectory = "C:\\Comprobante_Nomina_"+id_employee+".pdf";
    MySqlDataBase database = new MySqlDataBase();
    private ObservableList<TipoSalario> listTipoSalario = FXCollections.observableArrayList(new TipoSalario(1, "Mensual"), new TipoSalario(2, "Quincenal"), new TipoSalario(3, "Semana"),
            new TipoSalario(4, "Hora"));
    ActionEvent event = new ActionEvent();
    
    @FXML
    private ComboBox<Empleado> cmb_ID_nomina;
    @FXML
    private ComboBox<TipoSalario> cmb_tipoSalario;
    
    Empleado empleado;
    private ArrayList<Empleado> arrayEmp;
    private ObservableList<Empleado> listEmpleado;
    
    @FXML
    private ComboBox<TipoSalario> cmb_tipoSalario2;
    
    @FXML
    private ComboBox<?> cmb_mes_todos;
    
    @FXML
    private ComboBox<?> cmb_periodo_todos;
        
    @FXML
    private ComboBox<?> cmb_periodo_individual;

    @FXML
    private ComboBox<?> cmb_mes_individual;
    
    Comprobante cmp = new Comprobante();
    Comprobante cmptodos;

    
    /**
     * Metodo para generar la nomina individual del empleado seleccionado, llamando principalmente a la clase Nomina
     * @param event 
     */
    @FXML
    void generarNominaIndividual(ActionEvent event) {
        if (!cmb_ID_nomina.getSelectionModel().isEmpty()) {
            
            Nomina nomina = ManejadorNomina.generarNomina(empleado);
            
            nomina.getAFP();
            ManejadorNomina.calculaAFP(nomina);
            ManejadorNomina.calculaSFS(nomina);
            ManejadorNomina.calculaISR(nomina);
            nomina.getSFS();

            
            cmp.setId_employee(empleado.getID_empleado());
            cmp.setName_employee(empleado.getNombre());
            cmp.setLastname_employee(empleado.getApellido());
            cmp.setSalary_employee(nomina.getSalario());
            
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
        }
    }

    @FXML
    void generarNominatotal(ActionEvent event) {

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cmp = new Comprobante();
        cmb_ID_nomina.setDisable(true);

        llenarTipoSalarios();
        
        cmb_tipoSalario.valueProperty().addListener(new ChangeListener<TipoSalario>() {

            @Override
            public void changed(ObservableValue<? extends TipoSalario> observable, TipoSalario oldValue, TipoSalario newValue) {
                if (!cmb_tipoSalario.getSelectionModel().isEmpty()) {
                    cmb_ID_nomina.setDisable(false);
                    llenarIDEmpleado();
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
    
    void llenarTipoSalarios() {
        cmb_tipoSalario.setItems(listTipoSalario);
        
    }
    
    
    @FXML
    void browserbtn(ActionEvent event) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final File selectedDirectory = directoryChooser.showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
        if (selectedDirectory != null) {
            cmp.setFILE(selectedDirectory.getAbsolutePath()+"\\");
        } else {
            //cmp.setFILE(null);
        }
    }
    
    void llenarIDEmpleado() {
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
        
        listEmpleado = FXCollections.observableArrayList(arrayEmp);
        cmb_ID_nomina.setItems(listEmpleado);
        
        if (arrayEmp.size()==0) {     
            dg.informationWithoutHeaderDialog("No registros", "El campo seleccionado no dispone de registros");                    
        }
    }
    

    
}
