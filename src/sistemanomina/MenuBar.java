/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
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
 * Clase del Menubar disponible en agregar empleados y en editar empleados
 * @author SistemaNomina LJ
 */
public class MenuBar {
    
    /**
     * Deprecated hasta solucionar problema de cerrar stage
     * @deprecated No se puede usar por problemas en llamado al Stage
     * @param event 
     * 
     */
    public static void menuBarClose(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
    /**
     * Metodo que permite la limpieza de cualquier tipo de objecto del area de las vistas
     * @param aO Parametro de <PRE><CODE>ArrayList</CODE></PRE> que permite la limpieza de cualquier tipo por medio de su clase padre
     */
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
    
    /**
     * Metodo que permite el llamado a una ventana que muestra informacion acerca del proyecto realizado
     * @param event ActionEvent de la ventana para establecer padre al dialogo desplegado
     */
    public static void menuBarAcerca(ActionEvent event) {
        Dialogs dg = new Dialogs();
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
