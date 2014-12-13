/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author pc167
 */
public class Validation {
    public static void asignEventHandler(TextField tx, int tipo_operacion, int extra) {
        tx.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent> () {

            @Override
            public void handle(KeyEvent event) {
                tx.getStylesheets().add("/css/validation.css");
                switch(tipo_operacion) {
                    case 1: { // Textfield que solo acepte letras
                        if (!event.getCharacter().matches("[A-Za-zÑñáóúéí]")) {
                            tx.getStyleClass().add("error");
                            event.consume();
                        } else{
                            tx.getStyleClass().remove("error");
                        }
                        break;
                    }
                }            }
            
        });
    }
}
