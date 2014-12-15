/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;


import entidades.Departamento;
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
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mysql.MySqlDataBase;

/**
 * FXML Controller class
 *
 * @author pc167
 */
public class AddDepartamentosController implements Initializable {
    
    MySqlDataBase database = new MySqlDataBase();
    int ID_EMPRESA_UNIQUE = 2;
    int flag_bug = 1;
    
    private int departamento_selected;
    
    private Departamento crg;
    private ArrayList<Departamento> arrayDepartament;
    
    private ObservableList<Departamento> listDepartamentos;

    @FXML
    private TextField departcrear_nombre;

    @FXML
    private ComboBox<Departamento> cmbDPedit_nombre;  
    
    @FXML
    private TextField txt_nombre;

    @FXML
    void btn_crear_dep(ActionEvent event) {
        Dialogs dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());
        if (camp_validated()) {
            database.Insert("departamento(nombre,id_empresa)", "'"+departcrear_nombre.getText()+"',"+ID_EMPRESA_UNIQUE);
            departcrear_nombre.clear();
            limpiar();
            llenarDepartamentos();
            
            dg.informationWithoutHeaderDialog("Departamento creado", "El departamento ha sido creado existosamente.");
        } else {
            dg.errorDialog("Error creacion departamentos", null, "El departamento no ha podido ser creado, compruebe si todos los campos estan llenos.");
        }
    }

    @FXML
    void btn_edit_dp(ActionEvent event) {
        Dialogs dg = new Dialogs((Stage) ((Node) event.getSource()).getScene().getWindow());
        if (!txt_nombre.getText().isEmpty() && !cmbDPedit_nombre.getSelectionModel().isEmpty()) {
                    database.Update("departamento", "nombre='"+ txt_nombre.getText() +"'", "id", departamento_selected);
                    limpiar();
                    llenarDepartamentos();
                    
                    
                    dg.informationWithoutHeaderDialog("Departamento editado", "El departamento ha sido editado");         
        } else {
            dg.errorDialog("Campos vacios", null, "No se ha podido realizar la accion por encontrarse campos vacios");
        }

        
    }
    
    void limpiar() {       
        flag_bug = 2;
        cmbDPedit_nombre.getSelectionModel().clearSelection();
        txt_nombre.clear();
        flag_bug = 1;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarDepartamentos();
        
        Validation.asignEventHandler(departcrear_nombre, 6 , 25);
        Validation.asignEventHandler(txt_nombre, 6, 25);
        
        cmbDPedit_nombre.valueProperty().addListener(new ChangeListener<Departamento>() {

            @Override
            public void changed(ObservableValue<? extends Departamento> observable, Departamento oldValue, Departamento newValue) {
                if (flag_bug == 1) {
                    departamento_selected = newValue.getId();
                    txt_nombre.setText(newValue.getNombre());
                }
                
            }
            
        });
    }    
    
        private void llenarDepartamentos() {
        arrayDepartament = new ArrayList();
        
        ResultSet rs = database.Select("*", "departamento");
        
        try {
            while (rs.next()) {
                arrayDepartament.add(new Departamento(rs.getInt("id"),rs.getString("nombre"),rs.getInt("id_empresa")));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        listDepartamentos = FXCollections.observableArrayList(arrayDepartament);
        cmbDPedit_nombre.setItems(listDepartamentos);
    }
        
    public Departamento indiceDato(ArrayList <Departamento> arrayL, int search_id) {
        int j=0;
 
        for (int i=0 ;i < arrayL.size(); i++) {
            if(arrayL.get(i).getID()== search_id){
                j=i;
                break;
            }
        }
        return arrayL.get(j);
    }
        
    boolean camp_validated() {
        return !txt_nombre.getText().trim().isEmpty();
    }
    
}
