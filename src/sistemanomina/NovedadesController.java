/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import entidades.Deduccion;
import entidades.Empleado;
import entidades.Ingreso;
import entidades.TipoDeduccion;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import mysql.MySqlDataBase;

/**
 * FXML Controller class
 *
 * @author pc167
 */
public class NovedadesController implements Initializable {
    
    MySqlDataBase database = new MySqlDataBase();
    int flag_bug = 1;
    
    private Empleado empleado;
    private ArrayList<Empleado> arrayEmp;
    
    private ObservableList<Empleado> listEmpleados;
    
    private TipoIngresos tipoingreso;
    private ArrayList<TipoIngresos> arrayTipoIngreso;
    private ObservableList<TipoIngresos> listTiposIngreso;
        
    private TipoDeduccion deducciones;
    private ArrayList<TipoDeduccion> arrayTipoDeducciones;
    private ObservableList<TipoDeduccion> listTiposDeducciones;
    
    private Ingreso ingresos;
    private ArrayList<Ingreso> arrayTablaIngresos;
    private ObservableList<Ingreso> listTablaIngresos;

    private Deduccion deduccion;
    private ArrayList<Deduccion> arrayTablaDeduccion;
    private ObservableList<Deduccion> listTablaDeduccion;
    

    @FXML
    private ComboBox<Empleado> cmb_ID_ingresos;
    @FXML
    private ComboBox<TipoIngresos> cmb_tipo_ingresos;
    @FXML
    private Button btn_borrardeduccion;
    @FXML
    private Button btn_borrar_ingresos_;
    @FXML
    private TextField txt_nombre_tipoingresos;
    @FXML
    private TextField txt_monto_ingreso;
    @FXML
    private ComboBox<Empleado> cmb_ID_deducciones;
    @FXML
    private TextField txt_nombre_tipodeduccion;
    @FXML
    private TableView<Deduccion> table_deducciones;
    @FXML
    private ComboBox<TipoDeduccion> cmb_tipo_deducciones;
    @FXML
    private ComboBox<TipoIngresos> cmb_tipoingresos;
    @FXML
    private TableView<Ingreso> table_ingresos;
    @FXML
    private Tab tabIngresos;
    @FXML
    private Tab tabDeducciones;
    @FXML
    private ComboBox<TipoDeduccion> cmb_tipodeduc;
    @FXML
    private TextField txt_monto_deducciones;
    @FXML
    private Button bdn_borrar_tipoingreso;
    
    /**
     * Declaracion de boton que se ejecuta al seleccionar el Boton de agregar en la tab de deducciones
     * @param event 
     * Parametro de la accion de recibir el clic en el boton
     */
    @FXML
    void btn_deducciones_agregar(ActionEvent event) {
        Dialogs dg = new Dialogs(event);
        if (all_camps(2)) {
            Deduccion deduccion2 = new Deduccion(cmb_tipo_deducciones.getValue().getNombre(), Double.parseDouble(txt_monto_deducciones.getText()));
            deduccion2.setId_empleado(cmb_ID_deducciones.getValue().getID_empleado());
            deduccion2.setId(cmb_tipo_deducciones.getValue().getId());
            database.Insert("deduccioness_emp(id_empleado,tipo_deduccion,monto,estado)", "'"+deduccion2.getId_empleado()+"','"+deduccion2.getId()+"',"+deduccion2.getMonto()+","+1);
            limpiar(2);
            dg.informationWithoutHeaderDialog("Ingresar deduccion por empleado", "La deduccion de ese empleado ha sido agregado");
        } else {
            dg.errorDialog("Error ingresos empleado", null, "No se ha podido agregar dicho ingreso al empleado, confirme todos los campos.");
        }
    }
    
        @FXML
    void btn_agregar_ingresos(ActionEvent event) {
        Dialogs dg = new Dialogs(event);
        if (all_camps(1)) {
            Ingreso ingreso = new Ingreso(cmb_tipo_ingresos.getValue().getNombre(), Double.parseDouble(txt_monto_ingreso.getText()));
            ingreso.setId_empleado(cmb_ID_ingresos.getValue().getID_empleado());
            ingreso.setId(cmb_tipo_ingresos.getValue().getId());
            database.Insert("ingresos_emp(id_empleado,tipo_ingreso,monto)", "'"+ingreso.getId_empleado()+"','"+ingreso.getId()+"',"+ingreso.getMonto()+"");
            dg.informationWithoutHeaderDialog("Ingresar ingresos por empleado", "El ingreso de ese empleado ha sido agregado");
            limpiar(1);
        }  else {
            dg.errorDialog("Error ingresos empleado", null, "No se ha podido agregar dicho ingreso al empleado, confirme todos los campos.");
        }
        
    }
    
    boolean limpiar(int i) {
        boolean validated = false;
        switch(i) {
            case 1: {
                    table_ingresos.setItems(null);
                    txt_monto_ingreso.clear();
                    cmb_ID_ingresos.getSelectionModel().clearSelection();
                    cmb_tipo_ingresos.getSelectionModel().clearSelection();
                    
                    txt_nombre_tipoingresos.clear();
                break;
            }
            case 2: {
                table_deducciones.setItems(null);
                txt_monto_deducciones.clear();
                cmb_ID_deducciones.getSelectionModel().clearSelection();
                cmb_tipo_deducciones.getSelectionModel().clearSelection();
                
                txt_nombre_tipodeduccion.clear();
                break;
            }
        }
        
        return validated;
    }

    @FXML
    void btn_agregar_tipoingresos(ActionEvent event) {
        Dialogs dg = new Dialogs(event);
        if (!txt_nombre_tipoingresos.getText().isEmpty()) {
            database.Insert("tipos_ingresos(nombre)", "'"+txt_nombre_tipoingresos.getText()+"'");
            limpiar(1);
            llenarTIPOingreso();
            dg.informationWithoutHeaderDialog("Tipo de ingreso", "El tipo de ingreso ha sido creado exitosamente");
        } else {
            dg.errorDialog("Error tipo de ingreso", null, "El agregar el tipo de ingreso no se ha podido realizar");
        }
    }

    @FXML
    void btn_borrar_ingreso(ActionEvent event) {
        Dialogs dg = new Dialogs(event);
        dg.errorDialog("Borrar tipo de ingreso", null, "El borrado de tipo de ingreso no ha sido implementado");

        if (!cmb_tipoingresos.getSelectionModel().isEmpty()) {
            database.Delete("tipos_ingresos", "id", cmb_tipo_ingresos.getValue().getId()+"");
        } 

    }

    @FXML
    void btn_borrarIngresos(ActionEvent event) {
        Dialogs dg = new Dialogs(event);
        if (table_ingresos.getSelectionModel().getSelectedItem() != null && dg.confirmationDialog("Borrado permanente", null, "El borrado de ingresos "
                + "es una accion no revertible, favor de confirmar dicha accion.")) {
            Ingreso ing = (Ingreso) table_ingresos.getSelectionModel().getSelectedItem();
            database.Delete("ingresos_emp", "id", ing.getId()+"");
            limpiar(1);
            dg.informationWithoutHeaderDialog("Ingreso borrada", "El ingreso ha sido borrado exitosamente");
        } else {
            dg.errorDialog("Error ingreso", null, "El ingreso no ha podido ser borrado");
 
        }
    }
    
    @FXML
    void btn_borrardeduccion(ActionEvent event) {
        Dialogs dg = new Dialogs(event);
        if (table_deducciones.getSelectionModel().getSelectedItem() != null && dg.confirmationDialog("Borrado permanente", null, "El borrado de deducciones "
                + "es una accion no revertible, favor de confirmar dicha accion.")) {
            Deduccion deduc = (Deduccion) table_deducciones.getSelectionModel().getSelectedItem();
            database.Delete("deduccioness_emp", "id", deduc.getId()+"");
            limpiar(2);
            dg.informationWithoutHeaderDialog("Deduccion borrada", "La deduccion ha sido borrada exitosamente");
        } else {
            dg.errorDialog("Error deduccion", null, "La deduccion no ha podido ser borrada");
        }
    }

    @FXML
    void btn_tipodeduc_agregar(ActionEvent event) {
        Dialogs dg = new Dialogs(event);
            if (!txt_nombre_tipodeduccion.getText().isEmpty()) {
            database.Insert("tipos_descuentos(nombre)", "'"+txt_nombre_tipodeduccion.getText()+"'");
            limpiar(2);
            llenarTIPODeduccion();
            dg.informationWithoutHeaderDialog("Tipo de deduccion", "El tipo de deduccion ha sido agregado exitosamente");
        } else {
                dg.errorDialog("Error tipo de deduccion", null, "El tipo de deduccion no ha podido ser creado");

            }
    } 

    @FXML
    void btn_borrar_tipodeduc(ActionEvent event) {
        Dialogs dg = new Dialogs(event);
        dg.errorDialog("Borrar tipo de deduccion", null, "El borrado de tipo de deduccion no ha sido implementado");
        if (!txt_nombre_tipodeduccion.getText().isEmpty()) {
            database.Insert("tipos_descuentos(nombre)", "'"+txt_nombre_tipodeduccion.getText()+"'");
            limpiar(2);
            llenarTIPODeduccion();
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        database.getConn();
        
        Validation.asignEventHandler(txt_monto_ingreso, 7, 30); 
        Validation.asignEventHandler(txt_monto_deducciones, 7, 30);
        Validation.asignEventHandler(txt_nombre_tipodeduccion, 6, 20);
        Validation.asignEventHandler(txt_nombre_tipoingresos, 6, 20);
        
        llenarIDEmpleado();
        llenarTIPOingreso();
        
        table_ingresos.getColumns().clear();
        table_deducciones.getColumns().clear();
        
        TableColumn firstTipoIngreso = new TableColumn("Tipo de ingreso");
        TableColumn firstSecondTipoDeduccion = new TableColumn("Tipo de deduccion");
        TableColumn secondMonto = new TableColumn("Monto");
        TableColumn secondSecondMonto = new TableColumn("Monto");
    
        firstTipoIngreso.setCellValueFactory (
        new PropertyValueFactory<Ingreso, String>("nombre"));
        firstTipoIngreso.setMinWidth(300);
        
        firstSecondTipoDeduccion.setCellValueFactory(new PropertyValueFactory<Deduccion, String>("nombre"));
        firstSecondTipoDeduccion.setMinWidth(300);
        
        secondMonto.setCellValueFactory(
                new PropertyValueFactory<Ingreso, Double>("monto"));
        secondMonto.setMinWidth(250);
        
        secondSecondMonto.setCellValueFactory(new PropertyValueFactory<Deduccion, Double>("monto"));
        secondSecondMonto.setMinWidth(250);
        
        
        table_ingresos.getColumns().addAll(firstTipoIngreso, secondMonto);
        table_deducciones.getColumns().addAll(firstSecondTipoDeduccion, secondSecondMonto);
        
        tabIngresos.setOnSelectionChanged(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                cmb_ID_ingresos.getItems().clear();
                txt_monto_ingreso.clear();
                llenarIDEmpleado();
                llenarTIPOingreso();
                table_ingresos.setItems(null);
            }
        });
        
        tabDeducciones.setOnSelectionChanged(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                cmb_ID_deducciones.getItems().clear();
                txt_monto_deducciones.clear();
                llenarIDEmpleadoD();
                llenarTIPODeduccion();
                table_deducciones.setItems(null);
            }
            
        });
        
        cmb_ID_ingresos.valueProperty().addListener(new ChangeListener<Empleado>() {

            @Override
            public void changed(ObservableValue<? extends Empleado> observable, Empleado oldValue, Empleado newValue) {
                    if (!cmb_ID_ingresos.getSelectionModel().isEmpty()) {
                        
                        llenarTablaIngresos();
                    }
            
            }
            
        });
        
        cmb_ID_deducciones.valueProperty().addListener(new ChangeListener<Empleado>() {

            @Override
            public void changed(ObservableValue<? extends Empleado> observable, Empleado oldValue, Empleado newValue) {
                if (!cmb_ID_deducciones.getSelectionModel().isEmpty()) {
                    llenarTablaDeduccion();
                }
            }
            
        });

        
        table_ingresos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Ingreso>(){

            @Override
            public void changed(ObservableValue<? extends Ingreso> observable, Ingreso oldValue, Ingreso newValue) {
                if (table_ingresos.getSelectionModel().getSelectedItem()!=null) {
                    btn_borrar_ingresos_.setDisable(false);
                    
                } else {
                    btn_borrar_ingresos_.setDisable(true);
                            
                }
            }
            
        });
        
        table_deducciones.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Deduccion>() {

            @Override
            public void changed(ObservableValue<? extends Deduccion> observable, Deduccion oldValue, Deduccion newValue) {
                if (table_deducciones.getSelectionModel().getSelectedItem() != null) {
                    btn_borrardeduccion.setDisable(false);
                } else {
                    btn_borrardeduccion.setDisable(true);
                }
            }
            
        });
    
    }    
    
    void llenarIDEmpleadoD() {
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
        cmb_ID_deducciones.setItems(listEmpleados);
    }
    
    void llenarTIPODeduccion() {
        arrayTipoDeducciones = new ArrayList<>();
        
        ResultSet rs = database.Select("*", "tipos_descuentos");
        
        try {
            while(rs.next()) {
                arrayTipoDeducciones.add(new TipoDeduccion(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        listTiposDeducciones = FXCollections.observableArrayList(arrayTipoDeducciones);
        cmb_tipo_deducciones.setItems(listTiposDeducciones);
        cmb_tipodeduc.setItems(listTiposDeducciones);
    }
    
    boolean all_camps(int i) {
        boolean validated = false;
        switch(i) {
            case 1: {
                if (!cmb_ID_ingresos.getSelectionModel().isEmpty() &&
                !cmb_tipo_ingresos.getSelectionModel().isEmpty() &&
                !txt_monto_ingreso.getText().isEmpty() && txt_monto_ingreso.getText().indexOf(".", txt_monto_ingreso.getText().indexOf(".")+1) == -1
                ) 
                    {
                        validated = true;
                    }
                break;
            }
            case 2: {
                if (!cmb_ID_deducciones.getSelectionModel().isEmpty() && !cmb_tipo_deducciones.getSelectionModel().isEmpty() && !txt_monto_deducciones.getText().isEmpty()
                        &&  txt_monto_deducciones.getText().indexOf(".", txt_monto_deducciones.getText().indexOf(".")+1) == -1) {
                        validated = true;
                }
                break;
            }
        }
        
        return validated;
    }
    
    void llenarTablaIngresos() {
        
        table_ingresos.getSelectionModel().clearSelection();
        
        arrayTablaIngresos = new ArrayList<>();
        ResultSet rs = database.Select("tipos_ingresos.nombre, ingresos_emp.tipo_ingreso, ingresos_emp.monto, ingresos_emp.id_empleado,ingresos_emp.id",
                "tipos_ingresos,ingresos_emp", "tipos_ingresos.id = ingresos_emp.tipo_ingreso and ingresos_emp.id_empleado = "+cmb_ID_ingresos.getValue().getID_empleado());
        
        try {
            while (rs.next()) {
                arrayTablaIngresos.add(new Ingreso(rs.getInt("id"), rs.getInt("id_empleado"), rs.getString("nombre"), rs.getDouble("monto")));
                
                
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        listTablaIngresos = FXCollections.observableArrayList(arrayTablaIngresos);
        table_ingresos.setItems(listTablaIngresos);
        
    }
    
    void llenarTablaDeduccion() {
        
        table_deducciones.getSelectionModel().clearSelection();
        arrayTablaDeduccion = new ArrayList<>();
        ResultSet rs = database.Select("tipos_descuentos.nombre, deduccioness_emp.tipo_deduccion, deduccioness_emp.monto, deduccioness_emp.id_empleado, deduccioness_emp.id, deduccioness_emp.estado ",
                "tipos_descuentos, deduccioness_emp", "tipos_descuentos.id = deduccioness_emp.tipo_deduccion and deduccioness_emp.id_empleado ="+cmb_ID_deducciones.getValue().getID_empleado());
    
        try {
            while (rs.next()) {
                arrayTablaDeduccion.add(new Deduccion(rs.getInt("id"), rs.getInt("id_empleado"), rs.getString("nombre"), rs.getDouble("monto")));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        listTablaDeduccion = FXCollections.observableArrayList(arrayTablaDeduccion);
        table_deducciones.setItems(listTablaDeduccion);
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
        cmb_tipoingresos.setItems(listTiposIngreso);
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
