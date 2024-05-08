package com.unal.cash.Controller;
import com.unal.cash.App;
import com.unal.cash.Database.datos.PersonaDAO;
import com.unal.cash.Model.Login.SesionUsuario;
import com.unal.cash.Model.PerfilesConsumo.PerfilesConsumo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
public class InformacionPersonalController {
    public Label txtName;
    public Label txtTipoUsuario;
    public Label txtEmail;
    public Label txtNumeroID;
    PersonaDAO personaDao = new PersonaDAO();

    public void initialize() {
        // You can access and modify the elements here before the view is shown.
        // For example, to set the text of txtUsername:

        String usuario = SesionUsuario.getUsuarioLog();
        String[] datosStr = personaDao.SeleccionarUnoDS(usuario);
        double[] datosDbl = personaDao.SeleccionarUnoDDouble(usuario);
        this.txtName.setText(datosStr[2] + datosStr[3]);
        this.txtEmail.setText(datosStr[4]);
        this.txtTipoUsuario.setText("Cliente");
        Double ID = datosDbl[0];
        this.txtNumeroID.setText(Integer.toString((ID.intValue())));
    }

    public void cambioInterfaz(MouseEvent mouseEvent) {
        // Aquí va tu código para cambiar la interfaz
        if (SesionUsuario.session()){
            new App().mostrarVista("DashBoard/DashBoard.fxml");
        }else{
            new App().mostrarVista("Home.fxml");
        }
    }
}
