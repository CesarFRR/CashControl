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
import javafx.scene.layout.AnchorPane;

import java.text.NumberFormat;
import java.util.Locale;

public class DashBoardController {
    public Button btLogout;
    @FXML
    private TableView tbTabla;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private Button btnSoporte; // Assuming you want this one as well
    @FXML
    private Label txtUsername;

    @FXML
    private Button buttonAgregar;
    @FXML
    private BarChart chartPromedioGastosDiariosMes;
    @FXML
    private BarChart chartResumenGastosMensualesPorCategoria;
    @FXML
    private Label txtIngresosMensuales;
    @FXML
    private Label txtExcedenteFinDeMes;
    @FXML
    private Label txtPresupuestoDiarioSegunPerfilConsumo;
    PersonaDAO personaDao = new PersonaDAO();
    PerfilesConsumo porcentajes = new PerfilesConsumo();

    public DashBoardController() {

    }

    public void initialize() {
        // You can access and modify the elements here before the view is shown.
        // For example, to set the text of txtUsername:

        String usuario = SesionUsuario.getUsuarioLog();
        String[] datosStr = personaDao.SeleccionarUnoDS(usuario);
        double[] datosDbl = personaDao.SeleccionarUnoDDouble(usuario);

        //String contraseña = datosStr[1];
        String nombre = datosStr[2];
        String apellido = datosStr[3];
        String email = datosStr[4];
        String metodopagomasusado = datosStr[5];
        double telefono = datosDbl[1];
        double ingresosmensuales = datosDbl[2];
        double transporte = datosDbl[3];
        double alimentacion = datosDbl[4];
        double servicios = datosDbl[5];
        double educacion = datosDbl[6];
        double entretenimiento = datosDbl[7];
        double personal = datosDbl[8];
        int perfilconsumo = (int) datosDbl[10];
        double gastosRecurrentes = datosDbl[3] + datosDbl[4] + datosDbl[5] + datosDbl[6] + datosDbl[7] + datosDbl[8];
        //double[] porcentajesAI = porcentajes.porcentajesAhorroEInversion();
        //double porcentajeAhorro = porcentajesAI[0];
        //double porcentajeInversion = porcentajesAI[1];

        double ExcedenteFinDeMes = ingresosmensuales - gastosRecurrentes;
        this.setTxtUsername(nombre + " " + apellido);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);

        String Fingresosmensuales = numberFormat.format(ingresosmensuales);
        String FExcedenteFinDeMes = numberFormat.format(ExcedenteFinDeMes);

        this.setTxtIngresosMensuales("$" + Fingresosmensuales);

        this.setTxtExcedenteFinDeMes("$" + FExcedenteFinDeMes);






    }


    public void setTxtIngresosMensuales(String txtIngresosMensuales) {
        this.txtIngresosMensuales.setText(txtIngresosMensuales);
    }

    public void setTxtExcedenteFinDeMes(String txtExcedenteFinDeMes) {
        this.txtExcedenteFinDeMes.setText(txtExcedenteFinDeMes);
    }


    public void cambioMisMetodosPago(ActionEvent actionEvent) {
        new App().mostrarVista("MetodosdePago/MetodosdePago.fxml");
    }

    public void setTxtUsername(String txtUsername) {
        this.txtUsername.setText(txtUsername);
    }

    public void cambioInformacionPersonal(ActionEvent actionEvent) {
        new App().mostrarVista("InformacionPersonal/InformacionPersonal.fxml");
    }

    public void cambioSobreNosotros(ActionEvent actionEvent) {

        new App().mostrarVista("SobreNosotros/SobreNosotros.fxml");

    }

    public void cambioSoporte(ActionEvent actionEvent) {
        new App().mostrarVista("SobreNosotros/CrearCuenta.fxml");
    }

    public void cambioActualizarInformacion(ActionEvent actionEvent) {
        if (SesionUsuario.session()) {
            new App().mostrarVista("Login/Soporte.fxml");

        }
    }

    public void cambioPerfilConsumo(ActionEvent actionEvent) {
        new App().mostrarVista("PerfilConsumo/PerfildeConsumo.fxml");
    }

    public void cambioAhorroInversion(ActionEvent actionEvent) {
        new App().mostrarVista("AhorroInversion/SeleccioneAhorroInversion.fxml");

    }

    public void cambioGastosRecurrentes(ActionEvent actionEvent) {
        new App().mostrarVista("Login/GastosRecurrentes.fxml");

    }

    public void cambioIngresosMensuales(ActionEvent actionEvent) {
        new App().mostrarVista("RegistroIngresosMensuales/IngresosMensuales.fxml");

    }

    public void Logout(ActionEvent actionEvent) {
        boolean success = SesionUsuario.Logout();
        if (success) {
            System.out.println("Logout exitoso!");
            new App().mostrarVista("Home.fxml");
        }
    }

    public void Agregar(ActionEvent actionEvent) {

        if (SesionUsuario.session()) {
            new App().mostrarVista("AgregarTransaccion/AgregarTransaccion.fxml");
        }
    }


}
