/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author pc167
 */
public class ContPrimaryScreenController implements Initializable {

    @FXML
    void btn_addEmployee(ActionEvent event) {
        try {
            Stage stage1 = new Stage();
            Parent root2;
            root2 = FXMLLoader.load(getClass().getResource("/vistas/AddEmployee.fxml"));
            Scene scene = new Scene(root2);
            stage1.setScene(scene);
  
            stage1.show();
            
            
            // cerrar ventana actual
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.hide();
            
            stage1.setOnCloseRequest((WindowEvent we) -> {
                System.out.println("Closing APP");
                stage.show();
                
            });
        } catch (IOException ex) {
            Logger.getLogger(ContPrimaryScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btn_editEmployee(ActionEvent event) {
        try {
            Stage stage1 = new Stage();
            Parent root2;
            root2 = FXMLLoader.load(getClass().getResource("/vistas/EditEmployee.fxml"));
            Scene scene = new Scene(root2);
            stage1.setScene(scene);
            stage1.show();
            
            
            // cerrar ventana actual
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.hide();
            
            stage1.setOnCloseRequest((WindowEvent we) -> {
                System.out.println("Closing APP");
                stage.show();
                
            });
        } catch (IOException ex) {
            Logger.getLogger(ContPrimaryScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void e0f092(ActionEvent event) {
    }

    @FXML
    void btn_calcularPrestaciones(ActionEvent event) {

    }

    @FXML
    void btn_nomina(ActionEvent event) {

    }
    
    @FXML
    void btn_Departamentos(ActionEvent event) {
        try {
            Stage stage1 = new Stage();
            Parent root2;
            root2 = FXMLLoader.load(getClass().getResource("/vistas/AddDepartamentos.fxml"));
            Scene scene = new Scene(root2);
            stage1.setScene(scene);
            stage1.show();
            
            
            // cerrar ventana actual
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.hide();
            
            stage1.setOnCloseRequest((WindowEvent we) -> {
                System.out.println("Closing APP");
                stage.show();
                
            });
        } catch (IOException ex) {
            Logger.getLogger(ContPrimaryScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btn_Cargos(ActionEvent event) {
        try {
            Stage stage1 = new Stage();
            Parent root2;
            root2 = FXMLLoader.load(getClass().getResource("/vistas/AddCargos.fxml"));
            Scene scene = new Scene(root2);
            stage1.setScene(scene);
            stage1.show();
            
            
            // cerrar ventana actual
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.hide();
            
            stage1.setOnCloseRequest((WindowEvent we) -> {
                System.out.println("Closing APP");
                stage.show();
                
            });
        } catch (IOException ex) {
            Logger.getLogger(ContPrimaryScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btn_Novedades(ActionEvent event) {
     try {
            Stage stage1 = new Stage();
            Parent root2;
            root2 = FXMLLoader.load(getClass().getResource("/vistas/Novedades.fxml"));
            Scene scene = new Scene(root2);
            stage1.setScene(scene);
            stage1.show();
            
            
            // cerrar ventana actual
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.hide();
            
            stage1.setOnCloseRequest((WindowEvent we) -> {
                System.out.println("Closing APP");
                stage.show();
                
            });
        } catch (IOException ex) {
            Logger.getLogger(ContPrimaryScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
