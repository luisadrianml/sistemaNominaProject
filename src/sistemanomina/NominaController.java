/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import entidades.Deduccion;
import entidades.Empleado;
import entidades.Ingreso;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
    
    MySqlDataBase database = new MySqlDataBase();

    @FXML
    private ComboBox<Empleado> cmb_ID_nomina;
    
    Empleado empleado;
    private ArrayList<Empleado> arrayEmp;
    private ObservableList<Empleado> listEmpleado;
    
    
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
            
            Comprobante cmp = new Comprobante();
            cmp.setId_employee(empleado.getID_empleado());
            cmp.setName_employee(empleado.getNombre());
            cmp.setLastname_employee(empleado.getApellido());
            cmp.setSalary_employee(nomina.getSalario());
            cmp.createandheaderPDF();
            
            cmp.createTable();
            
            for (Ingreso ingreso : nomina.getIngresos()) {
                cmp.llenarIngresos(ingreso.getMonto(), ingreso.getNombre());
            }
            
            Double totalIngresos = 0.0;
            for (Ingreso ingreso : nomina.getIngresos()) {
                totalIngresos += ingreso.getMonto();
            }
            cmp.totalIngresos(totalIngresos);
            
            for (Deduccion deduccion : nomina.getDeducciones()) {
                cmp.llenarDeduccion(deduccion.getMonto(), deduccion.getNombre());
            }
           // cmp.llenarDeduccion(nomina.getDeducciones().get(0).getMonto(), nomina.getDeducciones().get(0).getNombre());
            Double totalDeduccion = 0.0;
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
        
        llenarIDEmpleado();
        
        cmb_ID_nomina.valueProperty().addListener(new ChangeListener<Empleado>() {

            @Override
            public void changed(ObservableValue<? extends Empleado> observable, Empleado oldValue, Empleado newValue) {
                empleado = newValue;
            }
            
        });
    }    
    
    void llenarIDEmpleado() {
        arrayEmp = new ArrayList<>();
        
        ResultSet rs = database.Select(" empleado_admin.id, empleado_personal.nombre, empleado_personal.apellido,empleado_admin.id_estado", "empleado_admin, empleado_personal",
                "empleado_admin.id = empleado_personal.id_empleado and empleado_admin.id_estado = 1");
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
        }
        
        listEmpleado = FXCollections.observableArrayList(arrayEmp);
        cmb_ID_nomina.setItems(listEmpleado);
    }
    

    
}
