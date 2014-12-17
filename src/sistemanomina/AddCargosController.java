/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */
package sistemanomina;

import entidades.Cargo;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mysql.MySqlDataBase;

/**
 * Clase controladora de la vista de agregar cargos
 *
 * @author SistemaNomina LJ
 */
public class AddCargosController implements Initializable {
    
    int flag_bug = 1;
    
    MySqlDataBase database = new MySqlDataBase();
    Cargo cargos;
    ArrayList<Cargo> arrayCargo;
    
    private ObservableList<Cargo> listCargos;
    
    @FXML
    private TextField cargoedit_monto;

    @FXML
    private TextField cargocrear_nombre;

    @FXML
    private ComboBox<Cargo> cmbedit_nombre;

    @FXML
    private TextField cargocrear_monto;

    @FXML
    void btn_crear(ActionEvent event) {
        Dialogs dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());

        if (all_camps()) {
            database.Insert("cargo(nombre,monto)", "'"+cargocrear_nombre.getText()+"',"+cargocrear_monto.getText());
            
            llenarCargos();
            limpiarCampos(1);
            
            dg.informationWithoutHeaderDialog("Cargo creado", "El cargo ha sido creado existosamente");
        } else {
            dg.errorDialog("Cargo no creado", null, "El cargo no ha sido creado, compruebe los campos.");
        }
    }

    @FXML
    void btn_edit(ActionEvent event) {
        Dialogs dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());
        if (!cmbedit_nombre.getSelectionModel().isEmpty() && !cargoedit_monto.getText().isEmpty()  && cargoedit_monto.getText().indexOf(".", cargoedit_monto.getText().indexOf(".")+1) == -1){
            database.Update("cargo", "monto="+cargoedit_monto.getText(), "id", cmbedit_nombre.getValue().getId());
            limpiarCampos(2);
            
            dg.informationWithoutHeaderDialog("Cargo editado", "El cargo ha sido editado existosamente");
        } else {
            dg.errorDialog("Error editando cargo", null, "El cargo no puede ser editado, compruebe los campos y su seleccion.");
        }
        

    }
    /**
     * Metodo para limpiar campos de la clase
     * @param state Parametro para especificar que area de la vista
     */
    public void limpiarCampos(int state) {
        if(state==1) {
            cargocrear_nombre.clear();
            cargocrear_monto.clear();
        } else {
            flag_bug=2;
            cmbedit_nombre.getSelectionModel().clearSelection();
            cargoedit_monto.clear();
            flag_bug=1;
        }
    }
    

    
    /**
     * Metodo que inicializa la clase
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        database.getConn();
        llenarCargos();
        
        Validation.asignEventHandler(cargoedit_monto, 7, 10);
        Validation.asignEventHandler(cargocrear_monto, 7, 10);
        Validation.asignEventHandler(cargocrear_nombre, 6, 20);
        
        cmbedit_nombre.valueProperty().addListener(new ChangeListener<Cargo>() {

            @Override
            public void changed(ObservableValue<? extends Cargo> observable, Cargo oldValue, Cargo newValue) {
                if (!cmbedit_nombre.getSelectionModel().isEmpty()) {
                    colocarMonto(newValue);
                }
            
            }
            
        });
        
    }    
    
    private void colocarMonto(Cargo crg) {
        cargoedit_monto.setText(crg.getSalario()+"");
    }

    /**
     * Metodo que llena los cargos que existen en la base de datos
     */
    public void llenarCargos() {
        arrayCargo = new ArrayList();
        
        ResultSet rs = database.Select("*", "cargo");
        
        try {
            while (rs.next()) {
                arrayCargo.add(new Cargo(rs.getInt("id"),rs.getString("nombre"),rs.getFloat("monto")));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        listCargos = FXCollections.observableArrayList(arrayCargo);
        cmbedit_nombre.setItems(listCargos);
    }

    private boolean all_camps() {
        return !cargocrear_nombre.getText().isEmpty() && !cargocrear_monto.getText().isEmpty() &&  cargocrear_monto.getText().indexOf(".", cargocrear_monto.getText().indexOf(".")+1) == -1;
    }
    
}
