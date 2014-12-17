/*
 * Sistema de nomina - Analisis y diseño de sistemas
 * Universidad Iberoamericana
 */
package sistemanomina;

import entidades.Empresa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mysql.MySqlDataBase;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * Clase controladora de la vista de Empresas del area de administrador
 *
 * @author SistemaNomina LJ
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

    /**
     * Boton de editar empresa
     * @param event Evento de la vista
     */
    @FXML
    void hB_emp_edit(ActionEvent event) {
        database.Update("empresa", "nombre='"+txt_emp_nombre.getText()+"',rnc='"+txt_emp_rnc.getText()+"'", "id", arrayEmp.get(0).getID());
        clearFields();
        fillEmpresas();
        changeTxt();
     //   fillComboBox_emp_edit();
    }
    
    /**
     * Metodo que hace el cambio de texto al seleccionar empresa
     */
    public void changeTxt() {
        txt_emp_nombre.setText(arrayEmp.get(0).getNombre());
        txt_emp_rnc.setText(arrayEmp.get(0).getRnc());
    }
    
    /**
     * Metodo que inicializa el controlador
     * @param url Por defecto del metodo
     * @param rb Por defecto del metodo
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
    
    /**
     * Metodo que encuentra la empresa de una ubicacion dentro del array
     * @param arrayL ArrayList de empresas para buscar
     * @param search_id Indice que se busca
     * @return Retorna la empresa que se buscaba
     */
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
    
    /**
     * Metodo que llena un arraylist de las empresas que se encuentra en la base de datos
     */
    public void fillEmpresas() {
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

    /**
     * @deprecated
     * Metodo que permite llenado de empresas a editar
     * Sin implementar por razones de trabajar con una unica empresa
     */
    public void fillComboBox_emp_edit() {

        cmb_emp_id.setPromptText("Seleccione...");

        Iterator<Empresa> it = arrayEmp.iterator();
        while(it.hasNext()) {
            Empresa emp = it.next();
            cmb_emp_id.getItems().add(emp.getID());
        }
    }

    /**
     * Metodo para la limpieza de campos
     */
    public void clearFields() {
        txt_emp_nombre.clear();
        txt_emp_rnc.clear();
        //cmb_emp_id.setPromptText("Seleccione...");
    }
   
    
}
