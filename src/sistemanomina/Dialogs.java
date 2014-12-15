/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author pc167
 */
public class Dialogs {
    Stage stage;
    Alert alert;
    
    public Dialogs(Stage stage) {
        this.stage = stage;
        alert = new Alert(AlertType.NONE);
        alert.initOwner(stage);
    }
    
    public Dialogs(ActionEvent event) {
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        alert = new Alert(AlertType.NONE);
        alert.initOwner(stage);
    }
    
    public Dialogs() {
        alert = new Alert(AlertType.NONE);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }
    
    
    
    public void informationDialog(String title, String header, String content) {
        alert.setAlertType(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public void informationWithoutHeaderDialog(String title, String content) {
        alert.setAlertType(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }
    
    public void warningDialog(String title, String header, String content) {
        alert.setAlertType(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    
    public void errorDialog(String title, String header, String content) {
        alert.setAlertType(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    
    public void longDialog(String title, String header, String content) {
        alert.setAlertType(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(null);


        Label label = new Label("El seguimiento del error fue:");

        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setContent(expContent);

        alert.showAndWait();
    }
    
    
    public void exceptionDialog(String title, String header, String content, Exception ex) {
        alert.setAlertType(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("El seguimiento del error fue:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
    
    public boolean confirmationDialog(String title, String header, String content) {
        boolean userSelectedOkay = false;
        alert.setAlertType(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        userSelectedOkay = result.get() == ButtonType.OK;
        return userSelectedOkay;
    }
    
    public int confirmationDialogWithCustomActions(String title, String header, String content, String btn1, String btn2, String btn3)  {
        int btnSelected = 0;
        
        alert.setAlertType(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType buttonTypeOne = new ButtonType(btn1);
        ButtonType buttonTypeTwo = new ButtonType(btn2);
        ButtonType buttonTypeThree = new ButtonType(btn3);
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            btnSelected = 1;
        } else if (result.get() == buttonTypeTwo) {
            btnSelected = 2;
        } else if (result.get() == buttonTypeThree) {
            btnSelected = 3;
        } else {
            btnSelected = 10;
        }

        return btnSelected;
        
    }
    

}
