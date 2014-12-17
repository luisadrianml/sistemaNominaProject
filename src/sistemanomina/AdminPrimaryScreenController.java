/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Clase que controla el menu de administrador
 *
 * @author SistemaNomina LJ
 */
public class AdminPrimaryScreenController implements Initializable {
    
    @FXML
    void hB_Users(ActionEvent event) throws IOException {
            Stage stage1 = new Stage();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/vistas/Usuarios.fxml"));
            Scene scene = new Scene(root);
            stage1.setScene(scene);
            stage1.getIcons().add(new Image("/images/logo_solo.png"));
            stage1.setTitle("Sistema Nomina LJ 2014");

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
            stage1.getIcons().add(new Image("/images/logo_solo.png"));
            stage1.setTitle("Sistema Nomina LJ 2014");
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
     * Metodo que inicializa la clase
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    
    
}
