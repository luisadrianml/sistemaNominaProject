/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemanomina;

import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author pc167
 */
public class Validation {
    

    /**
     * Metodo para asignar a los TextField del sistema diferentes validaciones con CSS.
     * @param tx
     * Parametro tx es el TextField al que se le aplicara las validaciones
     * @param tipo_operacion
     * Parametro tipo_operaciones permite especificar que validaciones darle al TextField
     * @param extra 
     * Parametro extra sirve como indicar de algo adicional que puede realizarse en alguna operacion, como especificar tamaño fijo
     */
    public static void asignEventHandler(TextField tx, int tipo_operacion, int extra) {
        tx.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent> () {

            @Override
            public void handle(KeyEvent event) {
                tx.getStylesheets().add("/css/validation.css");
                switch(tipo_operacion) {
                    case 1: { // Textfield que solo acepte letras
                        if (!event.getCharacter().matches("[A-Za-zÑñáóúéí\b\\s\t]")) {
                            tx.getStyleClass().add("error");
                            event.consume();
                        } else{
                            tx.getStyleClass().remove("error");
                        }
                        break;
                    }
                    
                    case 2: { // Textfield no puede estar vacio
                        if (event.getCharacter().isEmpty()) {
                            tx.getStyleClass().add("error");
                        } else {
                            tx.getStyleClass().remove("error");
                        }
                        break;
                    }
                    
                    case 3: { //Textfield solo coje numeros y tiene un tamano fijo
                        if (!event.getCharacter().matches("[0-9\b\t]")) {
                            tx.getStyleClass().add("error");
                            event.consume();
                        } else {
                            tx.getStyleClass().remove("error");          
                            if (tx.getText().length() == extra && extra != 0) {
                                event.consume();
                            }
                        }
                        break;
                    }
                    
                    case 4: {
                        if (event.getCharacter().matches("^[_A-Za-z0-9-]+(\\\\.[_A-Za-z0-9-]+)*@\n" +
"                   [A-Za-z0-9]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$\t")) {
                            tx.getStyleClass().add("error");
                            event.consume();
                        } else {
                            tx.getStyleClass().remove("error");
                        }

                        break;
                    }
                    
                    case 5: { // Textfield que acepte numeros y letras
                        if (!event.getCharacter().matches("[0-9A-Za-zÑñáóúéí \b\\s\t]")) {
                            tx.getStyleClass().add("error");
                            event.consume();
                        } else {
                            tx.getStyleClass().remove("error");
                        }
                        break;
                    }
                    
                    case 6: { // Textfield con cualquier cosa pero tamano limitado
                        if (!event.getCharacter().matches("[0-9A-Za-zÑñáóúéí \b\\s\t]")) {
                            tx.getStyleClass().add("error");
                            event.consume();
                        } else {
                            tx.getStyleClass().remove("error");
                            if (tx.getText().length() == extra) {
                                event.consume();
                            }
                        }
                        break;
                    }
                    
                    case 7: { // TextField para decimales y numeros 
                        if (!event.getCharacter().matches("[0-9\b\t\\.]")) {
                            tx.getStyleClass().add("error");
                            event.consume();
                        } else {
                            tx.getStyleClass().remove("error");
                            if (tx.getText().length() == extra) {
                                event.consume();
                            }
                        }
                        break;
                    }
                }            
            }
            
        });
    }
}
