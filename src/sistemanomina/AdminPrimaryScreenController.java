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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author pc167
 */
public class AdminPrimaryScreenController implements Initializable {
    
    @FXML
    void hB_Users(ActionEvent event) throws IOException {
            Stage stage1 = new Stage();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/vistas/Usuarios.fxml"));
            Scene scene = new Scene(root);
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
    }

    @FXML
    void hB_Empre(ActionEvent event) throws IOException {

        Stage stage1 = new Stage();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/vistas/EmpresaData.fxml"));
            Scene scene = new Scene(root);
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
                    
    }
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    
    
}
