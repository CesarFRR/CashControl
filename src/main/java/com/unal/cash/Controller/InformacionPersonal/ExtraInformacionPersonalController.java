package com.unal.cash.Controller.InformacionPersonal;

import com.unal.cash.App;
import com.unal.cash.Model.Login.SesionUsuario;
import com.unal.cash.Model.PerfilesConsumo.PerfilesConsumo;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Map;

public class ExtraInformacionPersonalController {


    public TextField txt_ahorromensual;
    public TextField txt_inversionmensual;
    public TextField txt_lunes;
    public TextField txt_martes;
    public TextField txt_miercoles;
    public TextField txt_jueves;
    public TextField txt_viernes;
    public TextField txt_sabado;
    public TextField txt_domingo;
    public Button bt_volver;

    public void initialize() {
        // You can access and modify the elements here before the view is shown.
        // For example, to set the text of txtUsername:
        PerfilesConsumo perfilC = SesionUsuario.getPerfilStatus();
        Map<String, Double> distribucionSemanal = perfilC.getDistribucionSemanal();
        ArrayList<Double> distribucionSemanalArray = new ArrayList<>(distribucionSemanal.values());
        txt_lunes.setText(String.valueOf(distribucionSemanalArray.get(0)));
        txt_martes.setText(String.valueOf(distribucionSemanalArray.get(1)));
        txt_miercoles.setText(String.valueOf(distribucionSemanalArray.get(2)));
        txt_jueves.setText(String.valueOf(distribucionSemanalArray.get(3)));
        txt_viernes.setText(String.valueOf(distribucionSemanalArray.get(4)));
        txt_sabado.setText(String.valueOf(distribucionSemanalArray.get(5)));
        txt_domingo.setText(String.valueOf(distribucionSemanalArray.get(6)));

       txt_ahorromensual.setText(String.valueOf(perfilC.getAhorroMensual()));
       txt_inversionmensual.setText(String.valueOf(perfilC.getInversionMensual()));
    }

    public void cambioInterfaz(ActionEvent mouseEvent) {
        // Aquí va tu código para cambiar la interfaz
        if (SesionUsuario.session()){
            new App().mostrarVista("DashBoard/DashBoard.fxml");
        }else{
            new App().mostrarVista("Home.fxml");
        }
    }

    public void printMap(Map<String, Double> map) {
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}
