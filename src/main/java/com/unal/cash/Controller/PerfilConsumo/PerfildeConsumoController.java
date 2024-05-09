package com.unal.cash.Controller.PerfilConsumo;

import com.unal.cash.App;
import com.unal.cash.Database.datos.PersonaDAO;
import com.unal.cash.Database.domain.Persona;
import com.unal.cash.Model.Login.SesionUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class PerfildeConsumoController {
    public Label labConsumoActual;
    @FXML
    private Button bt_150_Mas;

    @FXML
    private Button bt_20_50;

    @FXML
    private Button bt_50_90;

    @FXML
    private Button bt_5_20;

    @FXML
    private Button bt_90_150;

    @FXML
    private Button bt_MasDineroFinDe;

    private Persona P;

    private Button selected;
    private final String[] perfilesConsumo = {
            "Gasto dinero todos los dias entre 5.000 - 20.000",
            "Gasto dinero todos los dias entre 20.000 - 50.000",
            "Gasto dinero todos los dias entre 50.000 - 90.000",
            "Gasto dinero todos los dias entre 90.000 - 150.000",
            "Todos los días gasto más de 150.000",
            "Gasto más dinero el fin de semana"};

    private ArrayList<Button> btlist ;
    private PersonaDAO personaDao= new PersonaDAO();

    @FXML
    void initialize() {
        this.P = this.personaDao.getPersona(SesionUsuario.getUsuarioLog());
        this.btlist = new ArrayList<>();
        btlist.add(bt_150_Mas);
        btlist.add(bt_20_50);
        btlist.add(bt_50_90);
        btlist.add(bt_5_20);
        btlist.add(bt_90_150);
        btlist.add(bt_MasDineroFinDe);

        int indexConsumoActual = (int)P.getPerfilconsumo();
        labConsumoActual = new Label("Perfil de consumo actual: "+ this.perfilesConsumo[indexConsumoActual]);
//        labConsumoActual.setText();
    }

    @FXML
    void setGasto_5_20(ActionEvent event) {
        this.selected = this.bt_5_20;
        this.Colorbt(event);

    }
    @FXML
    void setGasto_20_50(ActionEvent event) {
        this.selected = this.bt_20_50;
        this.Colorbt(event);
    }
    @FXML
    void setGasto_50_90(ActionEvent event) {
        this.selected = this.bt_50_90;
        this.Colorbt(event);
    }

    @FXML
    void setGasto_90_150(ActionEvent event) {
        this.selected = this.bt_90_150;
        this.Colorbt(event);
    }

    @FXML
    void setGasto_150_Mas(ActionEvent event) {
        this.selected = this.bt_150_Mas;
        this.Colorbt(event);
    }

    @FXML
    void setGasto_MasDineroFinDe(ActionEvent event) {
        this.selected = this.bt_MasDineroFinDe;
        this.Colorbt(event);
    }

    void Colorbt(ActionEvent event) {
        // Cambiar el color del texto del botón seleccionado a rojo
        this.selected.setTextFill(Color.RED);
        System.out.print("el seleccionado es: " + this.selected.getText());

        // Cambiar el color del texto de todos los demás botones a negro
        for (Button otherBt : btlist) {
            if (otherBt != this.selected) {
                otherBt.setTextFill(Color.BLACK);
            }
        }
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

    @FXML
    void CambiarContinuar(ActionEvent event) {
        if(this.selected ==null) return;
        int index = Arrays.asList(this.perfilesConsumo).indexOf(this.selected.getText());
        Persona P =this.personaDao.getPersona(SesionUsuario.getUsuarioLog());
        double data = Double.parseDouble(Integer.toString(index));
        P.setPerfilconsumo(data);
        System.out.println("data: " + data);
        System.out.println("P: \n" + P.toString());
        this.personaDao.actualizar(P);
        if (SesionUsuario.session()){
            new App().mostrarVista("PerfilConsumo/PerfildeConsumoActualizado.fxml");
        }else{
            new App().mostrarVista("Home.fxml");
        }
    }

}
