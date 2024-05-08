package com.unal.cash.Controller.AgregarTransaccion;

import java.net.URL;
import java.util.ResourceBundle;

import com.unal.cash.App;
import com.unal.cash.Database.datos.PersonaDAO;
import com.unal.cash.Database.datos.TransaccionesDAO;
import com.unal.cash.Database.domain.Persona;
import com.unal.cash.Database.domain.Transaccion;
import com.unal.cash.Model.Login.SesionUsuario;
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

    private final String[] metodosPago = {"Tarjeta de Débito", "Tarjeta de Crédito", "Efectivo", "Transacción por aplicación"};
    private String cbMetodoPagoSelected;

    private final String[] tiposTransaccion = {"Ingresos", "Egresos"};
    private String cbTipoTransaccionSelected;

    private PersonaDAO personaDao = new PersonaDAO();
    private TransaccionesDAO transaccionesDAO = new TransaccionesDAO();
    @FXML
    void initialize() {
        assert btGuardarTransaccion != null : "fx:id=\"btGuardarTransaccion\" was not injected: check your FXML file 'AgregarTransaccion.fxml'.";
        assert txtMonto != null : "fx:id=\"txtMonto\" was not injected: check your FXML file 'AgregarTransaccion.fxml'.";
        assert txtNuevoPresupuesto != null : "fx:id=\"txtNuevoPresupuesto\" was not injected: check your FXML file 'AgregarTransaccion.fxml'.";

        if (this.cbMetodoPago == null) this.cbMetodoPago = new ChoiceBox<String>();

        this.cbMetodoPago.getItems().addAll(this.metodosPago);
        this.cbMetodoPago.setOnAction(this::getMetodoPago);

        if (this.cbTipoTransaccion == null) this.cbTipoTransaccion = new ChoiceBox<String>();
        this.cbTipoTransaccion.getItems().addAll(this.tiposTransaccion);
        this.cbTipoTransaccion.setOnAction(this::getTipoTransaccion);
    }

    private void getTipoTransaccion(ActionEvent actionEvent) {
        this.cbTipoTransaccionSelected = this.cbTipoTransaccion.getValue();

    }

    private void getMetodoPago(ActionEvent actionEvent) {
        this.cbMetodoPagoSelected = this.cbMetodoPago.getValue();

    }

    @FXML
    public void cambioInterfaz(ActionEvent mouseEvent) {
        // Aquí va tu código para cambiar la interfaz
        if (SesionUsuario.session()){
            new App().mostrarVista("DashBoard/DashBoard.fxml");
        }else{
            new App().mostrarVista("Home.fxml");
        }
    }

    public void GuardarTransaccion(ActionEvent actionEvent) {
        //TODO: guardar transaccion en una tabla posiblemente distinta a la de usuarios

        try {

            Transaccion ntransaccion = new Transaccion(this.transaccionesDAO.P.getId(), this.txtMonto.getText(), this.cbMetodoPagoSelected, this.cbTipoTransaccionSelected, this.txtNuevoPresupuesto.getText());

            this.transaccionesDAO.insertar(ntransaccion);

            System.out.println("¡Registro de datos exitoso!");
            new App().mostrarVista("DashBoard/DashBoard.fxml");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
