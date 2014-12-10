/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import mySql.MySqlDataBase;

/**
 * FXML Controller class
 *
 * @author pc167
 */
public class AddEmployeeController implements Initializable {
    
    MySqlDataBase database = new MySqlDataBase();
    
    Departamento departamento;
    Cargo cargos;
    TipoSalario tipoSalario;
    
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
        
        if (all_camps()) {


                database.Insert("empleado_admin", ""+admin_id.getText()+",'"+admin_fechaIngreso.getValue().toString()+"',"+
                        admin_departamento.getValue().getId()+","+admin_cargo.getValue().getId()+","+admin_tiposalario.getValue().getId()+","+""
                        + "'a'");
  
                database.Insert("empleado_personal", ""+admin_id.getText()+",'"+empleado_cedula.getText()+"','"+empleado_nombre.getText()+"','"+empleado_apellido.getText()+"','"+empleado_direccion.getText()+"','"+empleado_sexo.getValue().getId()+"','"+
                        empleado_civil.getValue().getId()+"','"+empleado_nacimiento.getValue().toString()+"','"+empleado_mail.getText()+"','"+empleado_movil.getText()+"','"+empleado_telefono.getText()+"'");
        
                
                database.Insert("dependientes","id_empleado,nombre,apellido,cedula,tipo_dependiente",admin_id.getText()+",'"+dependientes_nombre.getText()+"','"+dependientes_apellido.getText()+"','"+dependientes_ced.getText()+"','"+dependiente_tipo.getValue().getTipo()+"'");
        }
    }
    

    @FXML
    void btn_empleado_limpiar(ActionEvent event) {

    }


    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        database.getConn();
        llenarComboBox();
        
        admin_cargo.valueProperty().addListener(new ChangeListener<Cargo>() {

            @Override
            public void changed(ObservableValue<? extends Cargo> observable, Cargo oldValue, Cargo newValue) {
                colocarMonto(newValue);
            }
        });
    }    
    
    void colocarMonto(Cargo crg) {
        admin_monto_salario.setText(crg.getSalario()+"");
    
    }
 
    
    private boolean all_camps() {
        return true;
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
        ResultSet rs = database.Select("*","cargo_monto");
        
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

}
