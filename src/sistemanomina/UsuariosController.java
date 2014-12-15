/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import entidades.Administrador;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mysql.MySqlDataBase;

/**
 * FXML Controller class
 *
 * @author pc167
 */
public class UsuariosController implements Initializable {

    
    /**
     * Declaracion de variables 
     */
    MySqlDataBase database = new MySqlDataBase();
    Dialogs dg;
    /**
     * Declaracion variables de graficos
     */
    @FXML
    private ComboBox<String> cmb_edit_tipoUs;

    @FXML
    private Tab selected_edit;
    
    @FXML
    private Tab selected_crear;

    @FXML
    private TextField txt_edit_apellido;

    @FXML
    private TextField txt_edit_nombre;

    @FXML
    private PasswordField txt_edit_clave;

    @FXML
    private ComboBox<String> cmb_usuario_edit;

    @FXML
    private TextField text_apellido;

    @FXML
    private TextField text_email;

    @FXML
    private TextField text_nombre;

    @FXML
    private TextField text_usuario;

    @FXML
    private Tab selected_borrar;

    @FXML
    private ComboBox<String> cmb_usuarios_borrar;

    @FXML
    private ComboBox<String> cmb_tipo;

    @FXML
    private PasswordField text_clave;

    @FXML
    private Button hB_edit;

    @FXML
    private TextField txt_edit_email;
    
    
    /**
     * Metodo al clic el boton de Crear
     * @param event 
     */
    @FXML
    void hB_crear(ActionEvent event) {
        Dialogs dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());
        int tipo_user;
        
        if (all_camps(1)) {
            

            if (cmb_tipo.getValue().equals("Administrador")) {
                tipo_user = 1;
            } else {
                tipo_user = 2;
            }
                        database.Insert("usuarios", "usuario, clave, tipo_usuario", "'"+text_usuario.getText().toLowerCase()+"','"+text_clave.getText()+"',"+tipo_user+"");
                        database.Insert("usuario_pers", "'"+text_usuario.getText()+"','"+text_nombre.getText()+"','"+text_apellido.getText()+"','"+text_email.getText()+"'");


            clearFields(1); // Metodo de limpieza de campos
            dg.informationWithoutHeaderDialog("Creacion de usuarios", "Usuario creado existosamente.");
            fillComboBox_tipo_usuario();
        } else {
            dg.informationWithoutHeaderDialog("Creacion de usuarios", "Favor de ingresar todos los campos...");
        }
    }
    
    boolean all_camps(int i) { 
        boolean r = false;
        if (i == 1) {
            if (!text_nombre.getText().isEmpty() && !text_apellido.getText().isEmpty() && !text_usuario.getText().isEmpty() && 
                    !text_clave.getText().isEmpty() && !text_email.getText().isEmpty() && !cmb_tipo.getSelectionModel().isEmpty()) {
                r = true;
            }
        } else if (i == 2) {
            r = true;
        }
        
        return r;
    }

    @FXML
    void hB_limpiar(ActionEvent event) {
        clearFields(1);
    }
    

    @FXML
    void hb_borrar(ActionEvent event) {
               dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());
     
        if (!(cmb_usuarios_borrar.getValue() == null)) {
            
            if (dg.confirmationDialog("Borrado de usuario", null, "Se va a borrar un usuario, es una acción que no puede ser revertida. Favor confirmar.")) {
                database.Delete("usuarios", "usuario", ""+cmb_usuarios_borrar.getValue()+"");  
                dg.informationWithoutHeaderDialog("Usuario borrado", "El usuario ha sido borrado existosamente.");
                fillComboBox_borrar();
            } 
        } else {
            dg.errorDialog("Error - borrado de usuario", null, "El usuario no ha podido ser borrado.");
        // SHOW DIALOG FOR ERROR
        }
    }
    
    /**
     * Metodo al clic el boton Editar de la misma Tab editar. Ejecuta las acciones de llevar a la base los datos nuevos.
     * @param event 
     */
    @FXML
    void hB_edit(ActionEvent event) {
        Dialogs dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());
        
        if (!cmb_usuario_edit.getSelectionModel().isEmpty()) {
                database.Update("usuario_pers", "nombre='"+txt_edit_nombre.getText()+"',apellido='"+txt_edit_apellido.getText()+"',correo='"+txt_edit_email.getText()+"'", "id_usuario", cmb_usuario_edit.getValue());
                database.Update("usuarios", "clave='"+txt_edit_clave.getText()+"',tipo_usuario="+((cmb_edit_tipoUs.getValue().equals("Administrador")) ? 1 : 2), "usuario", cmb_usuario_edit.getValue());
        
                clearFields(2);
                fillComboBox_editar();
                fillComboBox_edit_tipos_usuarios();
                //System.out.println("Success editando!");
                /**
                 * @todo SHOW SUCCESS
                 */
                dg.informationWithoutHeaderDialog("Usuario editado", "El usuario ha sido editado existosamente.");
        } else {
            dg.errorDialog("Usuario no ha sido editado", null, "El usuario no ha podido ser editado, compruebe la seleccion de empleado");
        }
  
        
    }
    
    /**
     * Metodo para limpiar los campos de la Tab seleccionada
     * @param tab_index 
     */
     private void clearFields(int tab_index) {
         switch (tab_index) {
             case 1: {
                        // TAB PARA CREAR
                        text_nombre.clear();
                        text_apellido.clear();
                        text_clave.clear();
                        text_email.clear();
                        text_usuario.clear();
                        //cmb_tipo.getItems().clear();
                        cmb_tipo.setPromptText("Seleccione...");
                 break;
             }
             case 2: {
                        // TAB PARA EDITAR
                        txt_edit_nombre.clear();
                        txt_edit_apellido.clear();
                        txt_edit_clave.clear();
                        txt_edit_email.clear();
                        cmb_usuario_edit.setPromptText("Seleccione...");
                        cmb_edit_tipoUs.setPromptText("Seleccione...");
                 break;
             }
         }

    }
     
    private void fillComboBox_tipo_usuario() {
        
      
        ResultSet rs = database.Select("*","tipo_usuarios");
        cmb_tipo.setPromptText("Seleccione...");
        cmb_tipo.getItems().clear();
        clearFields(1);
        
        try{
            while(rs.next()){
                cmb_tipo.getItems().add(rs.getString("nombre").substring(0,1).toUpperCase()+rs.getString("nombre").substring(1));
    
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        
    }
     
    private void fillComboBox_borrar() {
        ResultSet rs = database.Select("usuario","usuarios");
        cmb_usuarios_borrar.setPromptText("Seleccione...");
        cmb_usuarios_borrar.getItems().clear();
        clearFields(1);
        
        try{
            while(rs.next()){
                cmb_usuarios_borrar.getItems().add(rs.getString("usuario"));
    
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    private void fillComboBox_editar() {
        ResultSet rs = database.Select("usuario","usuarios");
        cmb_usuario_edit.setPromptText("Seleccione...");
        cmb_usuario_edit.getItems().clear();
        clearFields(1);
        
        try{
            while(rs.next()){
                cmb_usuario_edit.getItems().add(rs.getString("usuario"));
    
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    
    /**
     * Metodo para llenar el combobox de los tipos de usuarios usuarios que dispone la base de datos
     */
    private void fillComboBox_edit_tipos_usuarios() {
        ResultSet rs = database.Select("nombre","tipo_usuarios");
        cmb_edit_tipoUs.setPromptText("Seleccione...");
        cmb_edit_tipoUs.getItems().clear();
        clearFields(1);
        
        try{
            while(rs.next()){
                cmb_edit_tipoUs.getItems().add(rs.getString("nombre").substring(0,1).toUpperCase() + rs.getString("nombre").substring(1));
    
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        database.getConn();
        
        fillComboBox_tipo_usuario();
        Validation.asignEventHandler(text_nombre, 1, 0);
        Validation.asignEventHandler(text_apellido, 1, 0);
        Validation.asignEventHandler(txt_edit_apellido, 1, 0);
        Validation.asignEventHandler(txt_edit_nombre, 1, 0);
        
        
        selected_borrar.setOnSelectionChanged(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                cmb_usuarios_borrar.getItems().clear();
                fillComboBox_borrar();
            }
        });
        
        selected_edit.setOnSelectionChanged(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                cmb_usuario_edit.getItems().clear();
                fillComboBox_editar();
                fillComboBox_edit_tipos_usuarios();
                clearFields(2);
            }
        });
        
        selected_crear.setOnSelectionChanged(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                fillComboBox_tipo_usuario();
               
            }
        });
        
        cmb_usuario_edit.valueProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ResultSet rs1 = database.Select("*", "usuarios", "usuario", ""+newValue);
                ResultSet rs2 = database.Select("*", "usuario_pers", "id_usuario", ""+newValue);
                int tipo_user;
                try {
                    while(rs2.next()) {
                        txt_edit_nombre.setText(rs2.getString("nombre"));
                        txt_edit_apellido.setText(rs2.getString("apellido"));
                        txt_edit_email.setText(rs2.getString("correo"));
                    }
                    while(rs1.next()) {
                        txt_edit_clave.setText(rs1.getString("clave"));
                        tipo_user = rs1.getInt("tipo_usuario");
                        fillComboBox_edit_tipos_usuarios();
                        cmb_edit_tipoUs.setValue((tipo_user==1) ? "Administrador" : "Contable");
                       
                    }
                } catch(Exception ex) {
                    System.out.println(ex);
                
                }
            }
        
        });
        
        
        
    }    

  
    
}
