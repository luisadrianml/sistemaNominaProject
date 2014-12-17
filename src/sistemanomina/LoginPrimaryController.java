/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */

package sistemanomina;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mysql.MySqlDataBase;


/**
 * Clase controladora del Login donde se hacen las verificaciones de acuerdo a tipo de usuario
 * @author SistemaNomina LJ
 */
public class LoginPrimaryController implements Initializable {
    
    MySqlDataBase database;
    Dialogs dg;
    
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
    
    /**
     * Boton de inicio de sesion
     * @param event Evento de la vista
     * @throws Exception Error si se encuentra problemas accediendo a la base de datos
     */
    @FXML
    void hBlogin(ActionEvent event) throws Exception {
        String usuario = "";
        String password = "";
        int tipo_usuario = 0;
        
        ResultSet rs;
        rs = database.Select("usuario,clave,tipo_usuario", "usuarios", "usuario", text_user.getText().toLowerCase());
        try {
            while(rs.next()) {
                usuario = rs.getString("usuario");
                password = rs.getString("clave");
                tipo_usuario = rs.getInt("tipo_usuario");
            }
        } catch(Exception ex) {
            dg.exceptionDialog("Sistema de nomina", "Error - Inicio de sesion", "Hemos encontrado un problema...", ex);
        }
        
        if (!text_user.getText().isEmpty() && !text_pass.getText().isEmpty()) {

            if ((usuario.equals(text_user.getText().toLowerCase())) && password.equals(text_pass.getText())) {

                if (tipo_usuario == 1) {
                    // abrir nueva ventana
                    Stage stage1 = new Stage();
                    Parent root;
                    root = FXMLLoader.load(getClass().getResource("/vistas/AdminPrimaryScreen.fxml"));
                    Scene scene = new Scene(root);
                    stage1.setScene(scene);
                    stage1.setMaxWidth(600);
                    stage1.setMinHeight(204);
                    stage1.setResizable(false);
                                            stage1.setTitle("Sistema Nomina LJ 2014");

                    stage1.getIcons().add(new Image("/images/logo_solo.png"));
                    stage1.show();

                    // cerrar ventana actual
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    database.closeConn();
                } else if (tipo_usuario ==2) {
                    // abrir nueva ventana
                    Stage stage1 = new Stage();
                    Parent root;
                    root = FXMLLoader.load(getClass().getResource("/vistas/ContPrimaryScreen.fxml"));
                    Scene scene = new Scene(root);
                    stage1.setScene(scene);
                    stage1.setMaxWidth(600);
                    stage1.setMinHeight(394);
                    stage1.setResizable(false);
                                            stage1.setTitle("Sistema Nomina LJ 2014");

                    stage1.getIcons().add(new Image("/images/logo_solo.png"));
                    stage1.show();

                    // cerrar ventana actual
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    database.closeConn();
                } else {

                }

            } else {
                /// SHOW DIALOG DE ERROR
                dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());
                dg.errorDialog("Sistema de Nomina", "Clave/Usuario incorrecto", "La clave o usuario que ha digitado es incorrecta.");
            }
        
        } else {

            
        }

    }
    
    /**
     * Boton limpieza
     * @param event Evento de la vista
     */
    @FXML
    void hBClean(ActionEvent event) {
         text_pass.clear();
         text_user.clear();
    }
    
    /**
     * Metodo que inicializa el controlador
     * @param url Por defecto del metodo
     * @param rb Por defecto del metodo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                    
            database = new MySqlDataBase();
            database.getConn();          
            
            

        ///new EmpresaDataController().fillEmpresas();
        
    }   
    
}
    
    
    