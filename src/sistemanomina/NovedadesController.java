/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import entidades.Deduccion;
import entidades.Empleado;
import entidades.Ingreso;
import entidades.TipoIngresos;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mysql.MySqlDataBase;

/**
 * FXML Controller class
 *
 * @author pc167
 */
public class NovedadesController implements Initializable {
    
    MySqlDataBase database = new MySqlDataBase();
    
    private Empleado empleado;
    private ArrayList<Empleado> arrayEmp;
    
    private ObservableList<Empleado> listEmpleados;
    
    private TipoIngresos tipoingreso;
    private ArrayList<TipoIngresos> arrayTipoIngreso;
    
    private ObservableList<TipoIngresos> listTiposIngreso;
    
    @FXML
    private Button btn_agregar_tipoingresos;

    @FXML
    private ComboBox<Empleado> cmb_ID_ingresos;
    

    @FXML
    private Button btn_tipodeduc_agregar;

    @FXML
    private ComboBox<TipoIngresos> cmb_tipo_ingresos;

    @FXML
    private TextField txt_nombre_tipoingresos;

    @FXML
    private TextField txt_monto_ingreso;

    @FXML
    private Button btn_agregar_ingresos;

    @FXML
    private Button btn_borrar_ingreso;

    @FXML
    private ComboBox<?> cmb_ID_deducciones;

    @FXML
    private TextField txt_nombre_tipodeduccion;

    @FXML
    private TableView<Deduccion> table_deducciones;

    @FXML
    private ComboBox<?> cmb_tipo_deducciones;

    @FXML
    private ComboBox<?> cmb_tipoingresos;

    @FXML
    private Button btn_borrar_tipodeduc;

    @FXML
    private TableView<Ingreso> table_ingresos;

    @FXML
    private Tab tabIngresos;

    @FXML
    private Tab tabDeducciones;

    @FXML
    private ComboBox<?> cmb_tipodeduc;

    @FXML
    private TextField txt_monto_deducciones;

    @FXML
    void btn_deducciones_agregar(ActionEvent event) {

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        database.getConn();
        
        llenarIDEmpleado();
        llenarTIPOingreso();
        
        tabIngresos.setOnSelectionChanged(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                llenarIDEmpleado();
                llenarTIPOingreso();
            }
        });
        
        cmb_ID_ingresos.valueProperty().addListener(new ChangeListener<Empleado>() {

            @Override
            public void changed(ObservableValue<? extends Empleado> observable, Empleado oldValue, Empleado newValue) {
                    llenarTablaIngresos();
            
            }
            
        });
    }    
    
    void llenarTablaIngresos() {
        ResultSet rs;
    }
    
    void llenarTIPOingreso() {
        arrayTipoIngreso = new ArrayList<>();
        
        ResultSet rs = database.Select("*", "tipos_ingresos");
        
        try {
            while(rs.next()) {
                arrayTipoIngreso.add(new TipoIngresos(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        listTiposIngreso = FXCollections.observableArrayList(arrayTipoIngreso);
        cmb_tipo_ingresos.setItems(listTiposIngreso);
    }

    
    void llenarIDEmpleado() {
        arrayEmp = new ArrayList<>();
        ResultSet rs = database.Select("id", "empleado_admin", "id_estado", "1");
        try {
            while(rs.next()) {
                arrayEmp.add(new Empleado(rs.getInt("id")));
            }
        } catch(SQLException ex) {
            System.out.println(ex);
        }
        
        listEmpleados = FXCollections.observableArrayList(arrayEmp);
        cmb_ID_ingresos.setItems(listEmpleados);
    }
    
}
