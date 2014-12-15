/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author LuisAdrián
 */
public class MenuBar {
    
    /**
     * Deprecated hasta solucionar problema de cerrar stage
     * @deprecated 
     * @param event
     * 
     */
    public static void menuBarClose(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
    public static void menuBarLimpiar(ArrayList<Object> aO) {
        for (Object o : aO) {
            if (o instanceof TextField) {
                ((TextField) o).clear();
            } else if (o instanceof DatePicker) {
                ((DatePicker) o).setValue(null);
            } else if (o instanceof ComboBox) {
                ((ComboBox) o).getSelectionModel().clearSelection();
            }
        }
    }
    
    public static void menuBarAcerca(ActionEvent event) {
        Dialogs dg = new Dialogs(event);
        dg.longDialog("Acerca", "Sistema de nomina", "El siguiente sistema de nomina ha sido realizado por los estudiantes"
                + " Luis Martinez (13-0219) y Joan Vidal (13-0980) para la asignatura de Analisis y diseño de sistemas con el profesor "
                + "Bernardo Diaz para la Universidad Iberoamericana (UNIBE). "
                + "\n \n \n"
                + "Integrantes: \n"
                + "Luis Martinez 13-0219 \n"
                + "Joan Vidal 13-0980 \n"
                + "\n"
                + "PROYECTO FINAL: Sistema de nomina");
    }
}
