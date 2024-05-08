package com.unal.cash.Controller.AgregarTransaccion;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AgregarTransaccionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btGuardarTransaccion;

    @FXML
    private ChoiceBox<String> cbMetodoPago;

    @FXML
    private ChoiceBox<String> cbTipoTransaccion;

    @FXML
    private TextField txtMonto;

    @FXML
    private TextField txtNuevoPresupuesto;

    @FXML
    void initialize() {
        assert btGuardarTransaccion != null : "fx:id=\"btGuardarTransaccion\" was not injected: check your FXML file 'AgregarTransaccion.fxml'.";
        assert cbMetodoPago != null : "fx:id=\"cbMetodoPago\" was not injected: check your FXML file 'AgregarTransaccion.fxml'.";
        assert cbTipoTransaccion != null : "fx:id=\"cbTipoTransaccion\" was not injected: check your FXML file 'AgregarTransaccion.fxml'.";
        assert txtMonto != null : "fx:id=\"txtMonto\" was not injected: check your FXML file 'AgregarTransaccion.fxml'.";
        assert txtNuevoPresupuesto != null : "fx:id=\"txtNuevoPresupuesto\" was not injected: check your FXML file 'AgregarTransaccion.fxml'.";

    }

    @FXML
    void cambioInterfaz(ActionEvent event) {

    }

}
