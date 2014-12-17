/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */
package sistemanomina;

import entidades.Cargo;
import entidades.Departamento;
import entidades.Dependientes;
import entidades.Empleado;
import entidades.Empleado_Admin;
import entidades.EstadoCivil;
import entidades.Estados;
import entidades.Sexo;
import entidades.TipoSalario;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mysql.MySqlDataBase;

/**
 * Clase controladora de la vista que permite editar a los empleados
 *
 * @author SistemaNomina LJ
 */
public class EditEmployeeController implements Initializable {
    
    MySqlDataBase database = new MySqlDataBase();
    ArrayList<Empleado_Admin> arrayAdminID;
    private ObservableList<Empleado_Admin> listAdminID;
    Empleado_Admin empleado_admin;
    Empleado empleado;
    Dependientes dependiente;
    
    
    // Llenado normal de combobox
    //    
    ArrayList<Departamento> arrayDepart;
    ArrayList<Cargo> arrayCargo;
    ArrayList<TipoSalario> arrayTipoSalario;

    private final ObservableList<Sexo> genero = FXCollections.observableArrayList(new Sexo("Masculino", 'm'), new Sexo("Femenino", 'f'));

    private final ObservableList<EstadoCivil> estadocivil = FXCollections.observableArrayList(
    new EstadoCivil("Soltero", "soltero"),new EstadoCivil("Casado","casado"),new EstadoCivil("Divorciado","divorciado"),
            new EstadoCivil("Viudo","viudo"));
    
    private final ObservableList<Estados> estados = FXCollections.observableArrayList(
    new Estados(1,"Activo"), new Estados(2, "Inactivo")
    );
    
    private ObservableList<Dependientes> dependientes = FXCollections.observableArrayList(new Dependientes(Dependientes.Tipo_Dependientes.ABUELA.toString()),new Dependientes(Dependientes.Tipo_Dependientes.ABUELO.toString()),
            new Dependientes(Dependientes.Tipo_Dependientes.CONYUGE.toString()), new Dependientes(Dependientes.Tipo_Dependientes.HIJO.toString()),new Dependientes(Dependientes.Tipo_Dependientes.MADRE.toString()),
            new Dependientes(Dependientes.Tipo_Dependientes.PADRE.toString()));
    
    private ObservableList<Departamento> departamentos;
    
    private ObservableList<Cargo> cargoslist;
    
    private ObservableList<TipoSalario> tiposSalarios;
    
    private Dialogs dg;
    @FXML
    private TextField dependientes_ced;

    @FXML
    private TextField empleado_telefono;

    @FXML
    private ComboBox<Sexo> empleado_sexo;
    
    @FXML
    private ComboBox<Estados> cmb_estado;

    @FXML
    private DatePicker admin_fechaIngreso;

    @FXML
    private ComboBox<TipoSalario> admin_tiposalario;

    @FXML
    private ComboBox<Cargo> admin_cargo;

    @FXML
    private TextField empleado_direccion;
    
    @FXML
    private ImageView searchIconClicked;

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
    private ComboBox<Empleado_Admin> admin_id;

    @FXML
    private DatePicker empleado_nacimiento;

    @FXML
    private TextField empleado_nombre;

    @FXML
    private TextField empleado_mail;

    @FXML
    private TextField empleado_movil;

    @FXML
    private TextField dependientes_apellido;

    @FXML
    private TextField admin_monto_salario;

    @FXML
    private TextField empleado_apellido;

    /**
     * Metodo del boton de guardar empleado
     * @param event Evento del boton
     */
    @FXML
    void btn_empleado_guardar(ActionEvent event) {
        dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());
        if (all_camps_validated(event)) {
            
            empleado_admin = new Empleado_Admin(admin_id.getValue().getId(), admin_fechaIngreso.getValue().toString(), admin_departamento.getValue().getId(), admin_cargo.getValue().getId(), admin_tiposalario.getValue().getId(), 
                    cmb_estado.getValue().getId(), admin_departamento.getValue(),admin_cargo.getValue(), admin_tiposalario.getValue());
            
            empleado_admin = settingItState(empleado_admin);
            
            empleado = new Empleado(empleado_admin.getId(), empleado_cedula.getText(),empleado_nombre.getText(), empleado_apellido.getText(), empleado_direccion.getText(), empleado_sexo.getValue(), empleado_civil.getValue(), 
                    empleado_nacimiento.getValue().toString(), empleado_mail.getText(), empleado_telefono.getText(),empleado_movil.getText());
            
            dependiente = new Dependientes(dependiente.getId(), empleado_admin.getId() , dependientes_nombre.getText(), dependientes_apellido.getText(),dependientes_ced.getText(),dependiente_tipo.getValue().getTipo());
  
            try {
                database.Update("empleado_admin", "fecha_ingreso='"+empleado_admin.getFecha_ingreso()+"',"
                    +"id_departamento="+empleado_admin.getDepartamento().getId()+","
                    +"id_cargo="+empleado_admin.getCargo().getId()+",tipo_salario="
                    +empleado_admin.getTipoSalario().getId()
                    +",id_estado='"+empleado_admin.getEstados().getId()+"'", "id", ""+empleado_admin.getId());
            
            database.Update("empleado_personal", "cedula='"+empleado.getCedula()+"',nombre='"+empleado.getNombre()+"',"
                    +"apellido='"+empleado.getApellido()+"',direccion='"+empleado.getDireccion()+"',sexo='"
                    +empleado.getSex().getId()+"',estado_civil='"+empleado.getEstadocvil().getId()+"',"
                    +"email = '" + empleado.getEmail() + "',movil='" + empleado.getMovil() + "'," 
                    + " telefono = '" + empleado.getTelefono() + "'", "id_empleado", ""+empleado_admin.getId());
            
            database.Update("dependientes", "nombre='"+dependiente.getNombre()+"',"
                    +"apellido = '"+dependiente.getApellido() + "',"
                    +"cedula='"+dependiente.getCedula()+ "'," 
                    +"tipo_dependiente='"+dependiente.getTipo()+"'"
                    , "id_empleado", ""+empleado_admin.getId());

                dg.informationWithoutHeaderDialog("Usuario editado", "El usuario ha sido editado existosamente.");
            } catch (Exception ex) {
                dg.exceptionDialog("Error editando usuario", "Error 3D174ND0", "Se ha encontrado un error editando este usuario.", ex);

            }
            

        }

    }

    /**
     * Metodo del boton para limpiar campos
     * @param event Evento del boton
     * @FIXME Sin implementar aun
     */
    @FXML
    void btn_empleado_limpiar(ActionEvent event) {
    }

    /**
     * Metodo inicializador de clase
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database.getConn();
    
        llenarIDs();
        ComboBoxFills();
        
        Validation.asignEventHandler(empleado_nombre, 1, 0);
        Validation.asignEventHandler(empleado_cedula, 3, 11);
        Validation.asignEventHandler(empleado_apellido, 1, 0);
        Validation.asignEventHandler(empleado_mail, 4, 0);
        Validation.asignEventHandler(empleado_movil, 3, 10);
        Validation.asignEventHandler(empleado_telefono, 3, 10);
        
        Validation.asignEventHandler(dependientes_nombre, 1,0);
        Validation.asignEventHandler(dependientes_apellido, 1, 0);
        Validation.asignEventHandler(dependientes_ced, 4, 11);
        
        
        
    admin_id.valueProperty().addListener(new ChangeListener<Empleado_Admin>() {

        @Override
        public void changed(ObservableValue<? extends Empleado_Admin> observable, Empleado_Admin oldValue, Empleado_Admin newValue) {
            
            if (!admin_id.getSelectionModel().isEmpty()) {
                fillCampos(newValue);
            }
        }
    
    
    });
    
    } 
    
    /**
     * Metodo para la busqueda de codigo de empleado
     * @deprecated 
     */
    @FXML
    void searchIconClicked(ActionEvent event) {
        Dialogs dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());
        dg.warningDialog("Busqueda de empleados", null, "El sistema de busqueda de empleados por nombre y apellido "
                + "aun no ha sido implementado.");
    }
    
    /**
     * Metodo para validar campos
     * @param event Evento del boton
     * @return Retorna positivo si los campos estan vacios
     */
    public boolean all_camps_validated(ActionEvent event) {
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
            dg.errorDialog("Campos no llenos", null, "No todos los campos pueden estar en blanco.");
            return false;
        }
    }
    
    /**
     * Metodo para limpieza de botones
     */
    public void limpiar() {
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
    
    /**
     * Metodo para llenar 
     * @param empAdmin 
     */
    public void fillCampos(Empleado_Admin empAdmin) {

        empleado_admin = empAdmin;
        
        ResultSet rs = database.Select("*", "empleado_personal", "id_empleado", ""+empleado_admin.getId());
        ResultSet rs2 = database.Select("*", "dependientes","id_empleado",""+empleado_admin.getId());
        try {
            while(rs.next()) {
                empleado = new Empleado(empleado_admin.getId());
                empleado.setCedula(rs.getString("cedula"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setDireccion(rs.getString("direccion"));
                empleado.setSex(new Sexo((rs.getString("sexo").equals("m")) ? "Masculino" : "Femenino", rs.getString("sexo").charAt(0)));
                empleado.setEstadocvil(new EstadoCivil(rs.getString("estado_civil").substring(0,1)+rs.getString("estado_civil").substring(1), rs.getString("estado_civil")));
                empleado.setNacimiento(rs.getString("nacimiento"));
                empleado.setEmail(rs.getString("email"));
                empleado.setMovil(rs.getString("movil"));
                empleado.setTelefono(rs.getString("telefono"));   
            }
            
            while(rs2.next()) {
                dependiente = new Dependientes();
                dependiente.setId_empleado(empleado_admin.getId());
                dependiente.setId(rs2.getInt("id"));
                dependiente.setNombre(rs2.getString("nombre"));
                dependiente.setApellido(rs2.getString("apellido"));
                dependiente.setCedula(rs2.getString("cedula"));
                dependiente.setTipo(rs2.getString("tipo_dependiente"));
            }
        } catch(SQLException ex) {
            System.out.println(ex);

        }
        
        admin_fechaIngreso.setValue(LocalDate.of(Integer.parseInt(empleado_admin.getFecha_ingreso().substring(0, 4)), calcularMes(Integer.parseInt(empleado_admin.getFecha_ingreso().substring(5, 7))) , Integer.parseInt(empleado_admin.getFecha_ingreso().substring(8, 10))));
        admin_departamento.setValue(empleado_admin.getDepartamento());
        admin_cargo.setValue(empleado_admin.getCargo());
        admin_tiposalario.setValue(empleado_admin.getTipoSalario());
        admin_monto_salario.setText(empleado_admin.getCargo().getSalario() +"");
        
        dependientes_nombre.setText(dependiente.getNombre());
        dependientes_apellido.setText(dependiente.getApellido());
        dependientes_ced.setText(dependiente.getCedula());
        dependiente_tipo.setValue(dependiente);
        
                
        empleado_cedula.setText(empleado.getCedula());
        empleado_nombre.setText(empleado.getNombre());
        empleado_apellido.setText(empleado.getApellido());
        empleado_sexo.setValue(empleado.getSex());
        empleado_civil.setValue(empleado.getEstadocvil());
        empleado_nacimiento.setValue(LocalDate.of(Integer.parseInt(empleado.getNacimiento().substring(0, 4)), calcularMes(Integer.parseInt(empleado.getNacimiento().substring(5, 7))) , Integer.parseInt(empleado.getNacimiento().substring(8, 10))));
        empleado_mail.setText(empleado.getEmail());
        empleado_movil.setText(empleado.getMovil());
        empleado_telefono.setText(empleado.getTelefono());
        empleado_direccion.setText(empleado.getDireccion());
        
        
     
        cmb_estado.setValue(settingItState(empleado_admin).getEstados());
    }
    
    /**
     * Metodo que permite configurar el estado del empleado
     * @param empleado_admin Empleado al que configurar el estado de acuerdo a lo obtenido de la base de datos
     * @return Retorna el empleado con su estado
     */
    public Empleado_Admin settingItState(Empleado_Admin empleado_admin) {
            if (empleado_admin.getEstado() == 1) {
            empleado_admin.setEstados( new Estados(1,"Activo")) ;
        } else {
            empleado_admin.setEstados( new Estados(2,"Inactivo"));
        }
            
            return empleado_admin;
        }
    
    /**
     * Metodo para llenar combobox
     */
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
        
        cmb_estado.setItems(estados);

 
    }
    
    /**
     * Metodo que obtiene data de los departamentos
     */
    public void gettingDataforDepartamento() {
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
    
    /**
     * Metodo que obtiene data de los cargos de la empresa
     */
    public void gettingDataforCargo() {
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
    
    /**
     * Metodo que obtiene data de los tipos de salario
     */
    public void gettingDataforTipoSalario() {
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
    
    private Month calcularMes(int mes) {

        Month m = null;
        
        switch (mes) {
            case 1: {
                m = m.JANUARY;
                break;
            }
            
            case 2: {
                m = m.FEBRUARY;
                break;
            }
            
            case 3: {
                m = m.MARCH;
                break;
            }
            
            case 4: {
                m = m.APRIL;
                break;
            }
            
            case 5: {
                m = m.MAY;
                break;
            }
            
            case 6: {
                m = m.JUNE;
                break;
            }
            
            case 7: {
                m = m.JULY;
                break;
            }
            
            case 8: {
                m = m.AUGUST;
                break;
            }
            
            case 9: {
                m = m.SEPTEMBER;
                break;
            }
            
            case 10: {
                m = m.OCTOBER;
                break;
            }
            case 11: {
                m = m.NOVEMBER;
                break;
            }
            
            case 12: {
                m = m.DECEMBER;
                break;
            }
            
            
        }
        
        return m;
    }
    
    /**
     * Metodo que llena los comboBox
     */
    private void ComboBoxFills() {
        listAdminID = FXCollections.observableArrayList(arrayAdminID);
        admin_id.setItems(listAdminID);
        llenarComboBox();
    }
   
    /**
     * Metodo que llena ID de los empleados
     */
    private void llenarIDs() {
        arrayAdminID = new ArrayList<>();
 //       arrayEmp.clear();
        ResultSet rs = database.Select("*","empleado_admin");
        try{
            while(rs.next()){
                ResultSet rsDP = database.Select("*", "departamento", "id", ""+rs.getInt("id_departamento"));
                ResultSet rsCG = database.Select("*", "cargo", "id", ""+rs.getInt("id_cargo"));
                ResultSet rsTS = database.Select("*", "tipo_salario", "id", ""+rs.getInt("tipo_salario"));
                
                Departamento dp = null;
                Cargo cg = null;
                TipoSalario tp = null;
                
                while (rsDP.next()) {
                    dp = new Departamento(rsDP.getInt("id"),rsDP.getString("nombre"));
                }
                while (rsCG.next()) {
                    cg = new Cargo(rsCG.getInt("id"), rsCG.getString("nombre"), rsCG.getInt("monto"));
                }
                while (rsTS.next()) {
                    tp = new TipoSalario(rsTS.getInt("id"), rsTS.getString("nombre"), rsTS.getFloat("factor"));
                }
                
                /**
                 * @todo 
                 * Creation of objects empleado_admin
                 */
                arrayAdminID.add(new Empleado_Admin(rs.getInt("id"), rs.getString("fecha_ingreso"), dp ,
                cg , tp , rs.getInt("id_estado")));
                
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        
        
    }
    
    /**
     * @deprecated
     * Metodo descartado
     * @param event 
     */
    @FXML
    void menuBar_close(ActionEvent event) {
        MenuBar.menuBarClose(event);
    }

    /**
     * Metodo que muestra ventana de informacion
     * @param event 
     */
    @FXML
    void menuBar_acerca(ActionEvent event) {
        MenuBar.menuBarAcerca(event);
    }
    
    
}
