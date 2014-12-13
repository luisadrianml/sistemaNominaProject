/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemanomina;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mysql.MySqlDataBase;


/**
 *
 * @author pc167
 */
public class LoginPrimaryController implements Initializable {
    
    MySqlDataBase database = new MySqlDataBase();
    
    @FXML
    private Button bttn_login;

    @FXML
    private Button hBClean;

    @FXML
    private TextField text_user;

    @FXML
    private PasswordField text_pass;

    @FXML
    private Label label;
    

    @FXML
    void hBlogin(ActionEvent event) throws Exception {
//        String usuario = "";
//        String password = "";
//        
//        ResultSet rs;
//        rs = database.Select("usuario,clave", "usuarios", "usuario", text_user.getText().toLowerCase());
//        try {
//            while(rs.next()) {
//                usuario = rs.getString("usuario");
//                password = rs.getString("clave");
//            }
//        } catch(Exception ex) {
//            System.out.println(ex);
//        }
//        
//        
//        if ((usuario.equals(text_user.getText().toLowerCase())) && password.equals(text_pass.getText())) {
//            
            // abrir nueva ventana
            Stage stage1 = new Stage();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/vistas/ContPrimaryScreen.fxml"));
            Scene scene = new Scene(root);
            stage1.setScene(scene);
            stage1.setMaxWidth(600);
            stage1.setMinHeight(394);
            stage1.setResizable(false);
            stage1.show();
            
            // cerrar ventana actual
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
//        } else {
//            /// SHOW DIALOG DE ERROR
//        }
//        
//        
            database.closeConn();
    }
    
    @FXML
    void hBClean(ActionEvent event) {
         text_pass.clear();
         text_user.clear();
         
         Node source = (Node) event.getSource();
         Dialogs dg = new Dialogs((Stage) source.getScene().getWindow());
         dg.informationDialog("Boton de limpiar", "Presionado boton", "Se supone que tiene icono del login");
         

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        database.getConn();
        ///new EmpresaDataController().fillEmpresas();
        
    }   
    
}
    
    
    