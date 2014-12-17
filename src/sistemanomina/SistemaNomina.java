/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */

package sistemanomina;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase que crea la primera instancia del sistema, dando ejecucion al mismo sisema de Login
 * @author SistemaNomina LJ
 */
public class SistemaNomina extends Application {
    
    
    public Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/LoginPrimary.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Sistema de Nomina");
        
        stage.setScene(scene);
        stage.getIcons().add(new Image("/images/logo_solo.png"));
        stage.show();
        
        this.stage = stage;
        
  
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
