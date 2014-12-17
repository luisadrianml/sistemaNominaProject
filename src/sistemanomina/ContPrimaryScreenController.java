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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Clase que controla la vista del menu de contable
 *
 * @author SistemaNomina LJ
 */
public class ContPrimaryScreenController implements Initializable {

    @FXML
    void btn_addEmployee(ActionEvent event) {
        try {
            // Abrir ventana
            Stage stage1 = new Stage();
            Parent root2;
            root2 = FXMLLoader.load(getClass().getResource("/vistas/AddEmployee.fxml"));
            Scene scene = new Scene(root2);
            stage1.setScene(scene);
            stage1.getIcons().add(new Image("/images/logo_solo.png"));
                        stage1.setTitle("Sistema Nomina LJ 2014");

            stage1.show();
            // Fin de forma de abrir ventana
             
            // Fora de ocultar ventana actual (no la que acabas de abrir hay arriba)
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.hide();
            
            //Metodo que esta atengo a cuando se cierre la ventana Stage1 para mostrar la que estaba antes
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
            // Stage de ventana nueva a abrir
            Stage stage1 = new Stage();
            Parent root2;
            root2 = FXMLLoader.load(getClass().getResource("/vistas/EditEmployee.fxml"));
            Scene scene = new Scene(root2);
            stage1.setScene(scene);
            stage1.getIcons().add(new Image("/images/logo_solo.png"));
                        stage1.setTitle("Sistema Nomina LJ 2014");

            stage1.show();
            
              // Stage de ventana actual
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            
            // cerrar ventana actual
            stage.hide();
            
            // Metodo para estar atento al cerrar la Stage que se acaba de abrir para mostrar el menu general
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
    void cerrar(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void btn_calcularPrestaciones(ActionEvent event) {
try {
            Stage stage1 = new Stage();
            Parent root2;
            root2 = FXMLLoader.load(getClass().getResource("/vistas/CalculoPrestaciones.fxml"));
            Scene scene = new Scene(root2);
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
        } catch (IOException ex) {
            Logger.getLogger(ContPrimaryScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btn_nomina(ActionEvent event) {
try {
            Stage stage1 = new Stage();
            Parent root2;
            root2 = FXMLLoader.load(getClass().getResource("/vistas/Nomina.fxml"));
            Scene scene = new Scene(root2);
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
        } catch (IOException ex) {
            Logger.getLogger(ContPrimaryScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void btn_Departamentos(ActionEvent event) {
        try {
            Stage stage1 = new Stage();
            Parent root2;
            root2 = FXMLLoader.load(getClass().getResource("/vistas/AddDepartamentos.fxml"));
            Scene scene = new Scene(root2);
            stage1.getIcons().add(new Image("/images/logo_solo.png"));
            stage1.setScene(scene);
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
                        stage1.setTitle("Sistema Nomina LJ 2014");

            stage1.getIcons().add(new Image("/images/logo_solo.png"));
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
     * Metodo que inicializa la clase
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
