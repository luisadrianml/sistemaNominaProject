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

    }

    @FXML
    void btn_edit(ActionEvent event) {

    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        database.getConn();
        llenarCargos();
        
    }    

    private void llenarCargos() {
        arrayCargo = new ArrayList();
        
        ResultSet rs = database.Select("*", "cargo_monto");
        
        try {
            while (rs.next()) {
                rs.getString("nombre");
                rs.getString("id");
                rs.getFloat("monto");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
}
