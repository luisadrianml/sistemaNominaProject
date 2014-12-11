/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import entidades.Empresa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mySql.MySqlDataBase;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author pc167
 */
public class EmpresaDataController implements Initializable {
    
    MySqlDataBase database = new MySqlDataBase();
    Empresa empresa;
    ArrayList <Empresa> arrayEmp = null;
    
    @FXML
    private ComboBox<Integer> cmb_emp_id;

    @FXML
    private TextField txt_emp_nombre;

    @FXML
    private TextField txt_emp_rnc;

    @FXML
    void hB_emp_edit(ActionEvent event) {
        database.Update("empresa", "nombre='"+txt_emp_nombre.getText()+"',rnc='"+txt_emp_rnc.getText()+"'", "id", 1);
        clearFields();
        fillEmpresas();
        changeTxt();
     //   fillComboBox_emp_edit();
    }
    
    void changeTxt() {
        txt_emp_nombre.setText(arrayEmp.get(0).getNombre());
        txt_emp_rnc.setText(arrayEmp.get(0).getRnc());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        database.getConn();
        fillEmpresas();
        changeTxt();
//        fillComboBox_emp_edit();
//             
//        cmb_emp_id.valueProperty().addListener(new ChangeListener<Integer>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
//                txt_emp_nombre.setText(indiceDato(arrayEmp, newValue).getNombre());
//                txt_emp_rnc.setText(indiceDato(arrayEmp, newValue).getRnc());
//            }
//            
//        });
    }   
    
    public Empresa indiceDato(ArrayList <Empresa> arrayL, int search_id) {
        int j=0;
 
        for (int i=0 ;i < arrayL.size(); i++) {
            if(arrayL.get(i).getID()== search_id){
                j=i;
                break;
            }
        }
        return arrayL.get(j);
    }
    
    protected void fillEmpresas() {
        arrayEmp = new ArrayList<>();
 //       arrayEmp.clear();
        ResultSet rs = database.Select("*","empresa");
        
        try{
            while(rs.next()){
                arrayEmp.add(new Empresa(rs.getInt("id"), rs.getString("nombre"), rs.getString("rnc")));
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        
    }

    private void fillComboBox_emp_edit() {

        cmb_emp_id.setPromptText("Seleccione...");

        Iterator<Empresa> it = arrayEmp.iterator();
        while(it.hasNext()) {
            Empresa emp = it.next();
            cmb_emp_id.getItems().add(emp.getID());
        }
    }

    private void clearFields() {
        txt_emp_nombre.clear();
        txt_emp_rnc.clear();
        //cmb_emp_id.setPromptText("Seleccione...");
    }
   
    
}
