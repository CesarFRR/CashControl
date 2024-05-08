package com.unal.cash.Controller.Login;

import com.unal.cash.App;
import com.unal.cash.Database.datos.PersonaDAO;
import com.unal.cash.Database.domain.Persona;
import com.unal.cash.Model.Login.SesionUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.Objects;

public class RegisterController {

    public TextField tfNumeroDocumento;
    @FXML
    private Button btnContinueSignup;

    @FXML
    private PasswordField pfNewPassword;

    @FXML
    private TextField tfNewUser;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfApellido;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TextField tfIngresosmensuales;

    @FXML
    private ChoiceBox<String> cbMetoPago;

    private String cbMetoPagoSelected;
    private PersonaDAO personaDao = new PersonaDAO();

    private final String[] metodosPago = {"Tarjeta de Débito", "Tarjeta de Crédito", "Efectivo", "Transacción por aplicación"};


    @FXML
    private ChoiceBox<String> cbPerfilConsumo;

    private String cbPerfilConsumoSelected;

    private final String[] perfilesConsumo = {
            "Gasto dinero todos los dias entre 5.000 - 20.000",
            "Gasto dinero todos los dias entre 20.000 - 50.000",
            "Gasto dinero todos los dias entre 50.000 - 90.000",
            "Gasto dinero todos los dias entre 90.000 - 150.000",
            "Todos los días gasto más de 150.000",
            "Gasto más dinero el fin de semana"};


    @FXML
    private TextField tfAlimentacion;

    @FXML
    private TextField tfEducacion;

    @FXML
    private TextField tfEntretenimiento;

    @FXML
    private TextField tfPersonal;

    @FXML
    private TextField tfServicios;

    @FXML
    private TextField tfTransporte;

    private static Object[] part1;
    public void initialize() {
        if (this.cbMetoPago == null) this.cbMetoPago = new ChoiceBox<String>();

        this.cbMetoPago.getItems().addAll(this.metodosPago);
        this.cbMetoPago.setOnAction(this::getMetoPago);

        if (this.cbPerfilConsumo == null) this.cbPerfilConsumo = new ChoiceBox<String>();
        this.cbPerfilConsumo.getItems().addAll(this.perfilesConsumo);
        this.cbPerfilConsumo.setOnAction(this::getPerfilConsumo);

        if (SesionUsuario.session()){

            Persona P = this.personaDao.getPersona(SesionUsuario.getUsuarioLog());

            this.tfNombre.setText(P.getNombre());
            this.tfApellido.setText(P.getApellido());
            this.tfNewUser.setText(P.getUsuario());
            this.tfEmail.setText(P.getEmail());
            this.tfNumeroDocumento.setText(Double.toString(P.getIdPersona()));
            this.tfTelefono.setText(Double.toString(P.getTelefono()));
            this.tfIngresosmensuales.setText(Double.toString(P.getIngresosmensuales()));




        }

    }

    private void getPerfilConsumo(ActionEvent actionEvent) {
        this.cbPerfilConsumoSelected = this.cbPerfilConsumo.getValue();
    }

    public void getMetoPago(ActionEvent e) {
        this.cbMetoPagoSelected = this.cbMetoPago.getValue();
    }

    @FXML
    void cambioInterfaz(ActionEvent event) {
        // Implementar la lógica para cambiar la interfaz aquí
        // Aquí va tu código para cambiar la interfaz
        if (SesionUsuario.session()) {
            new App().mostrarVista("DashBoard/DashBoard.fxml");
        } else {
            new App().mostrarVista("Home.fxml");
        }
    }

    @FXML
    void cambioSoporte(ActionEvent event) {
        // Implementar la lógica para cambiar al soporte aquí
        // Aquí va tu código para cambiar la interfaz

        new App().mostrarVista("SobreNosotros/Soporte.fxml");


    }

    @FXML
    void continuarRegistro(ActionEvent event) {
        if (this.cbMetoPagoSelected == null) {
            this.cbMetoPagoSelected = "Efectivo";
        }
        if (Objects.equals(this.tfNewUser.getText(), "") || this.cbMetoPagoSelected == "" || this.pfNewPassword.getText() == "" || this.tfEmail.getText() == "" || this.tfNombre.getText() == "" || this.tfApellido.getText() == "" || this.tfTelefono.getText() == "" || this.tfIngresosmensuales.getText() == "") {
            return;
        } else {
            String nombre = this.tfNombre.getText(), apellido = this.tfApellido.getText(), usuario = this.tfNewUser.getText(), contraseña = this.pfNewPassword.getText(), email = this.tfEmail.getText(), metodopagomasusado = this.cbMetoPagoSelected;
            String telefono = this.tfTelefono.getText(), ingresosmensuales = this.tfIngresosmensuales.getText(), NumeroDocumento = this.tfNumeroDocumento.getText();
            part1 = new Object[]{nombre, apellido, usuario, contraseña, email, telefono, ingresosmensuales, metodopagomasusado, NumeroDocumento};
            new App().mostrarVista("Login/GastosRecurrentes.fxml");
        }

    }

    @FXML
    void cambioCrearCuenta(ActionEvent event) {
        if (SesionUsuario.session()){
            new App().mostrarVista("DashBoard/DashBoard.fxml");
        }else{
            new App().mostrarVista("Login/CrearCuenta.fxml");
        }

    }

    @FXML
    void guardarGastos(ActionEvent event) {
        if (SesionUsuario.session()){

            int index = Arrays.asList(this.perfilesConsumo).indexOf(this.cbPerfilConsumoSelected);
            String transporte = this.tfTransporte.getText(),
                    alimentacion = this.tfAlimentacion.getText(),
                    servicios = this.tfServicios.getText(),
                    educacion = this.tfEducacion.getText(),
                    entretenimiento = this.tfEntretenimiento.getText(),
                    personal = this.tfPersonal.getText(),
                    perfilconsumo = Integer.toString(index);
            try {
                Persona P = this.personaDao.getPersona(SesionUsuario.getUsuarioLog());
                String[] datosStrToDbl = new String[]{ transporte, alimentacion, servicios, educacion, entretenimiento, personal, perfilconsumo};

                double[] d = Arrays.stream(datosStrToDbl)
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                P.setTransporte(d[0]);
                P.setAlimentacion(d[1]);
                P.setServicios(d[2]);
                P.setEducacion(d[3]);
                P.setEntretenimiento(d[4]);
                P.setPersonal(d[5]);
                P.setPerfilconsumo(d[6]);
                this.personaDao.actualizar(P);
                System.out.println("Gastos actualizados con exito!");
                new App().mostrarVista("DashBoard/DashBoard.fxml");
            } catch (Exception e) {
                System.out.println(e);
            }

        }else{
            String[] datosStr = new String[]{part1[2].toString(), part1[3].toString(), part1[0].toString(), part1[1].toString(), part1[4].toString(), part1[7].toString()};
            int index = Arrays.asList(this.perfilesConsumo).indexOf(this.cbPerfilConsumoSelected);
            String telefono = part1[5].toString(),
                    ingresosmensuales = part1[6].toString(),
                    transporte = this.tfTransporte.getText(),
                    alimentacion = this.tfAlimentacion.getText(),
                    servicios = this.tfServicios.getText(),
                    educacion = this.tfEducacion.getText(),
                    entretenimiento = this.tfEntretenimiento.getText(),
                    personal = this.tfPersonal.getText(),
                    perfilconsumo = Integer.toString(index),
                    NumeroDocumento = part1[8].toString();
            String[] datosStrToDbl = new String[]{telefono, ingresosmensuales, transporte, alimentacion, servicios, educacion, entretenimiento, personal, perfilconsumo, NumeroDocumento};
            System.out.println("datosStrToDbl: " + Arrays.toString(datosStrToDbl));
            double[] datosDbl = Arrays.stream(datosStrToDbl)
                    .mapToDouble(Double::parseDouble)
                    .toArray();

            try {
                Persona personaNueva = new Persona(datosStr[0], datosStr[1], datosStr[2], datosStr[3], datosStr[4], datosDbl[0],datosDbl[1],datosDbl[2],datosDbl[3],datosDbl[4],datosDbl[5],datosDbl[6],datosDbl[7],0.0,datosDbl[8],datosStr[5], datosDbl[datosDbl.length - 1]);

                this.personaDao.insertar(personaNueva);

                System.out.println("¡Registro de datos exitoso!");
                new App().mostrarVista("Login/InicioSesion.fxml");
            } catch (Exception e) {
                System.out.println(e);
            }
        }



    }
}