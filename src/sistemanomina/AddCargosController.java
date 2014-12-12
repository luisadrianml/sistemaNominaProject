/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mysql.MySqlDataBase;

/**
 * FXML Controller class
 *
 * @author pc167
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

        if (all_camps()) {
            database.Insert("cargo(nombre,monto)", "'"+cargocrear_nombre.getText()+"',"+cargocrear_monto.getText());
            
            llenarCargos();
            limpiarCampos(1);
        }
    }

    @FXML
    void btn_edit(ActionEvent event) {
        database.Update("cargo", "monto="+cargoedit_monto.getText(), "id", cmbedit_nombre.getValue().getId());
        
        limpiarCampos(2);
    }
    
    void limpiarCampos(int state) {
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        database.getConn();
        llenarCargos();
        
        
        cmbedit_nombre.valueProperty().addListener(new ChangeListener<Cargo>() {

            @Override
            public void changed(ObservableValue<? extends Cargo> observable, Cargo oldValue, Cargo newValue) {
                if (flag_bug==1) {
                    colocarMonto(newValue);
                }
            
            }
            
        });
        
    }    
    
    private void colocarMonto(Cargo crg) {
        cargoedit_monto.setText(crg.getSalario()+"");
    }

    private void llenarCargos() {
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
        return true;
    }
    
}
