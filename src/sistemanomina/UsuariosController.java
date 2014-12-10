/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import mySql.MySqlDataBase;

/**
 * FXML Controller class
 *
 * @author pc167
 */
public class UsuariosController implements Initializable {

    MySqlDataBase database = new MySqlDataBase();

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

    @FXML
    void hB_crear(ActionEvent event) {
        int tipo_user;
        if (cmb_tipo.getValue().equals("Administrador")) {
            tipo_user = 1;
        } else {
            tipo_user = 2;
        }
        database.Insert("usuarios", "usuario, clave, tipo_usuario", "'"+text_usuario.getText()+"','"+text_clave.getText()+"',"+tipo_user+"");
        database.Insert("usuario_pers", "'"+text_usuario.getText()+"','"+text_nombre.getText()+"','"+text_apellido.getText()+"','"+text_email.getText()+"'");
        clearFields(1);
        
    }

    @FXML
    void hB_limpiar(ActionEvent event) {
        clearFields(1);
    }
    

    @FXML
    void hb_borrar(ActionEvent event) {
        if (!(cmb_usuarios_borrar.getValue() == null)) {
            database.Delete("usuarios", "usuario", ""+cmb_usuarios_borrar.getValue()+"");
            // SHOW DIALOG FOR SUCCESS
            
            System.out.println("Usuario borrado");
        } else {
        // SHOW DIALOG FOR ERROR
        }
    }
    
    
    @FXML
    void hB_edit(ActionEvent event) {
        database.Update("usuario_pers", "nombre='"+txt_edit_nombre.getText()+"',apellido='"+txt_edit_apellido.getText()+"',correo='"+txt_edit_email.getText()+"'", "id_usuario", cmb_usuario_edit.getValue());
        database.Update("usuarios", "clave='"+txt_edit_clave.getText()+"',tipo_usuario="+((cmb_edit_tipoUs.getValue().equals("Administrador")) ? 1 : 2), "usuario", cmb_usuario_edit.getValue());
        
        clearFields(2);
        fillComboBox_editar();
        fillComboBox_edit_tipos_usuarios();
        System.out.println("Success editando!");
        //SHOW SUCCESS
    }

     private void clearFields(int tab_index) {
         switch (tab_index) {
             case 1: {
                        // TAB PARA CREAR
                        text_nombre.clear();
                        text_apellido.clear();
                        text_clave.clear();
                        text_email.clear();
                        text_usuario.clear();
                        cmb_tipo.setPromptText("Seleccione...");
                 break;
             }
             case 2: {
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
        
      
        ResultSet rs = database.Select("nombre","tipo_usuarios");
        cmb_tipo.setPromptText("Seleccione...");
        cmb_tipo.getItems().clear();
        clearFields(1);
        
        try{
            while(rs.next()){
                cmb_tipo.getItems().add(rs.getString("nombre"));
    
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
    
    private void fillComboBox_edit_tipos_usuarios() {
        ResultSet rs = database.Select("nombre","tipo_usuarios");
        cmb_edit_tipoUs.setPromptText("Seleccione...");
        cmb_edit_tipoUs.getItems().clear();
        clearFields(1);
        
        try{
            while(rs.next()){
                cmb_edit_tipoUs.getItems().add(rs.getString("nombre"));
    
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
        
        
        
        selected_borrar.setOnSelectionChanged(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                fillComboBox_borrar();
            }
        });
        
        selected_edit.setOnSelectionChanged(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                fillComboBox_editar();
                fillComboBox_edit_tipos_usuarios();
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
