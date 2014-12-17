/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */
package sistemanomina;

import entidades.Cargo;
import entidades.Departamento;
import entidades.Dependientes;
import entidades.EstadoCivil;
import entidades.Sexo;
import entidades.TipoSalario;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mysql.MySqlDataBase;

/**
 * Clase que controla la vista de agregar empleado 
 *
 * @author SistemaNomina LJ
 */
public class AddEmployeeController implements Initializable {
    
    MySqlDataBase database = new MySqlDataBase();
    Dialogs dg;
    Departamento departamento;
    Cargo cargos;
    TipoSalario tipoSalario;
    int flag_bug =1;
    
//    
    ArrayList<Departamento> arrayDepart;
    ArrayList<Cargo> arrayCargo;
    ArrayList<TipoSalario> arrayTipoSalario;
//    
    private final ObservableList<Sexo> genero = FXCollections.observableArrayList(new Sexo("Masculino", 'm'), new Sexo("Femenino", 'f'));

    private final ObservableList<EstadoCivil> estadocivil = FXCollections.observableArrayList(
    new EstadoCivil("Soltero", "soltero"),new EstadoCivil("Casado","casado"),new EstadoCivil("Divorciado","divorciado"),
            new EstadoCivil("Viudo","viudo"));
    
    private ObservableList<Dependientes> dependientes = FXCollections.observableArrayList(new Dependientes(Dependientes.Tipo_Dependientes.ABUELA.toString()),new Dependientes(Dependientes.Tipo_Dependientes.ABUELO.toString()),
            new Dependientes(Dependientes.Tipo_Dependientes.CONYUGE.toString()), new Dependientes(Dependientes.Tipo_Dependientes.HIJO.toString()),new Dependientes(Dependientes.Tipo_Dependientes.MADRE.toString()),
            new Dependientes(Dependientes.Tipo_Dependientes.PADRE.toString()));
    
    private ObservableList<Departamento> departamentos;
    
    private ObservableList<Cargo> cargoslist;
    
    private ObservableList<TipoSalario> tiposSalarios;
//  
    @FXML
    private TextField dependientes_ced;

    @FXML
    private TextField empleado_telefono;

    @FXML
    private ComboBox<Sexo> empleado_sexo;

    @FXML
    private DatePicker admin_fechaIngreso;

    @FXML
    private ComboBox<TipoSalario> admin_tiposalario;

    @FXML
    private ComboBox<Cargo> admin_cargo;

    @FXML
    private TextField empleado_direccion;

    @FXML
    private ComboBox<EstadoCivil> empleado_civil;

    @FXML
    private ComboBox<Departamento> admin_departamento;

    @FXML
    private TextField empleado_cedula;

    @FXML
    private ComboBox<Dependientes> dependiente_tipo;

    @FXML
    private TextField dependientes_nombre; 
    
    @FXML
    private Button btn_limpiarEvent;

    @FXML
    private TextField admin_id;

    @FXML
    private DatePicker empleado_nacimiento;

    @FXML
    private TextField empleado_mail;

    @FXML
    private TextField empleado_movil;

    @FXML
    private TextField dependientes_apellido;

    @FXML
    private TextField admin_monto_salario;
    
    @FXML
    private TextField empleado_nombre;
    
    
    @FXML
    private TextField empleado_apellido;

    @FXML
    void btn_empleado_guardar(ActionEvent event) {
            dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());    
        if (all_camps(event)) {
            try {
                database.Insert("empleado_admin(fecha_ingreso,id_departamento,id_cargo,tipo_salario,id_estado)", ""+"'"+admin_fechaIngreso.getValue().toString()+"',"+
                        admin_departamento.getValue().getId()+","+admin_cargo.getValue().getId()+","+admin_tiposalario.getValue().getId()+","+""
                        + "1");
  
                database.Insert("empleado_personal", ""+admin_id.getText()+",'"+empleado_cedula.getText()+"','"+empleado_nombre.getText()+"','"+empleado_apellido.getText()+"','"+empleado_direccion.getText()+"','"+empleado_sexo.getValue().getId()+"','"+
                        empleado_civil.getValue().getId()+"','"+empleado_nacimiento.getValue().toString()+"','"+empleado_mail.getText()+"','"+empleado_movil.getText()+"','"+empleado_telefono.getText()+"'");
        
                
                database.Insert("dependientes","id_empleado,nombre,apellido,cedula,tipo_dependiente",admin_id.getText()+",'"+dependientes_nombre.getText()+"','"+dependientes_apellido.getText()+"','"+dependientes_ced.getText()+"','"+dependiente_tipo.getValue().getTipo()+"'");
                
                dg.informationWithoutHeaderDialog("Usuario creado", "El usuario ha sido creado existosamente.");
                menuBar_limpiar(event);
                
            } catch(Exception ex) {
                dg.exceptionDialog("Error creando usuario", "Error C534ND0", "Se ha encontrado un error creando este usuario.", ex);
                System.out.println(ex);
            }
            
            
        }
        
        
    }
    

    @FXML
    void btn_empleado_limpiar(ActionEvent event) {
        
        menuBar_limpiar(event);
        setID();
    }
    
    
    


    
    /**
     * Metodo que inicializa la clase
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        database.getConn();
        Validation.asignEventHandler(empleado_nombre, 1, 0);
        Validation.asignEventHandler(empleado_cedula, 3, 11);
        Validation.asignEventHandler(empleado_apellido, 1, 0);
        Validation.asignEventHandler(empleado_mail, 4, 0);
        Validation.asignEventHandler(empleado_movil, 3, 10);
        Validation.asignEventHandler(empleado_telefono, 3, 10);
        
        Validation.asignEventHandler(dependientes_nombre, 1,0);
        Validation.asignEventHandler(dependientes_apellido, 1, 0);
        Validation.asignEventHandler(dependientes_ced, 4, 11);

        
        llenarComboBox();
        setID();
        
        admin_cargo.valueProperty().addListener(new ChangeListener<Cargo>() {

            @Override
            public void changed(ObservableValue<? extends Cargo> observable, Cargo oldValue, Cargo newValue) {
                if (!admin_cargo.getSelectionModel().isEmpty()) {
                colocarMonto(newValue);
    
                }
                
            }
        });
    }    
    
    private void setID() {
        ResultSet rs5 = database.Select("max(id)", "empleado_admin");
        
        try {
            while(rs5.next()) {
                admin_id.setText(""+(rs5.getInt(1)+1));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    void colocarMonto(Cargo crg) {
        admin_monto_salario.setText(crg.getSalario()+"");
    
    }
 
    
    private boolean all_camps(ActionEvent event) {
        if (!empleado_nombre.getText().isEmpty() && !empleado_cedula.getText().isEmpty() && !empleado_apellido.getText().isEmpty() &&
                !empleado_civil.getSelectionModel().isEmpty() && 
                !empleado_direccion.getText().isEmpty() && !empleado_mail.getText().isEmpty() && !empleado_movil.getText().isEmpty() &&
                !empleado_sexo.getSelectionModel().isEmpty() && !empleado_telefono.getText().isEmpty() && 
                admin_fechaIngreso.getValue()!=null && !admin_departamento.getSelectionModel().isEmpty() &&
                !admin_cargo.getSelectionModel().isEmpty() && !admin_tiposalario.getSelectionModel().isEmpty() &&
                (empleado_nacimiento.getValue()!=null)) {
            return true;
        } else {
            dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());
            dg.errorDialog("Campos no llenos", null, "No todos los campos han sido llenados como corresponden.");
            return false;
        }
    }
    
    private java.sql.Date formattingDate(String dateFromDatePicker) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse(dateFromDatePicker);
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        return sqlDate;
        
    }
    
    

    private void llenarComboBox() {
        gettingDataforDepartamento();
        gettingDataforCargo();
        gettingDataforTipoSalario();
        empleado_sexo.setItems(genero);
        empleado_civil.setItems(estadocivil);
        departamentos = FXCollections.observableArrayList(arrayDepart);
        admin_departamento.setItems(departamentos);
        cargoslist = FXCollections.observableArrayList(arrayCargo);
        admin_cargo.setItems(cargoslist);
        tiposSalarios = FXCollections.observableArrayList(arrayTipoSalario);
        admin_tiposalario.setItems(tiposSalarios);
        
        dependiente_tipo.setItems(dependientes);
        
        
 
    }
    
    private void gettingDataforDepartamento() {
          arrayDepart = new ArrayList<>();
 //       arrayEmp.clear();
        ResultSet rs = database.Select("id,nombre","departamento");
        
        try{
            while(rs.next()){
                arrayDepart.add(new Departamento(rs.getInt("id"), rs.getString("nombre")));
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        
    }
    
    private void gettingDataforCargo() {
          arrayCargo = new ArrayList<>();
 //       arrayEmp.clear();
        ResultSet rs = database.Select("*","cargo");
        
        try{
            while(rs.next()){
                arrayCargo.add(new Cargo(rs.getInt("id"), rs.getString("nombre"), rs.getInt("monto")));
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        
    }
    
    private void gettingDataforTipoSalario() {
        arrayTipoSalario = new ArrayList<>();
 //       arrayEmp.clear();
        ResultSet rs = database.Select("*","tipo_salario");
        
        try{
            while(rs.next()){
                arrayTipoSalario.add(new TipoSalario(rs.getInt("id"), rs.getString("nombre"), rs.getFloat("factor")));
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
             
    }
    
    /**
     * Metodos del menu bar
     */

    @FXML
    void menuBar_close(ActionEvent event) {
        MenuBar.menuBarClose(event);
    }

    @FXML
    void menuBar_limpiar(ActionEvent event) {
        ArrayList<Object> array = new ArrayList<>();
        array.add(empleado_apellido);
        array.add(empleado_cedula);
        array.add(empleado_civil);
        array.add(empleado_direccion);
        array.add(empleado_mail);
        array.add(empleado_movil);
        array.add(empleado_nacimiento);
        array.add(empleado_nombre);
        array.add(empleado_sexo);
        array.add(empleado_telefono);
        array.add(admin_monto_salario);
        array.add(admin_cargo);
        array.add(admin_departamento);
        array.add(admin_fechaIngreso);
        array.add(admin_tiposalario);
        array.add(dependiente_tipo);
        array.add(dependientes_apellido);
        array.add(dependientes_ced);
        array.add(dependientes_nombre);
        MenuBar.menuBarLimpiar(array);
    }

    @FXML
    void menuBar_acerca(ActionEvent event) {
        MenuBar.menuBarAcerca(event);
    }
}
