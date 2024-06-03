package com.unal.cash.Controller.Login;

import com.unal.cash.App;
import com.unal.cash.Model.JSON.JsonCRUD;
import com.unal.cash.Model.Login.SesionUsuario;
import com.unal.cash.Model.PerfilesConsumo.PerfilesConsumo;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.unal.cash.Database.datos.PersonaDAO;

import java.util.Map;

public class AfterLoginController {

    public Button bt_cancelar;
    public TextField txt_porcAhorro;
    public TextField txt_porcInversion;

    private PerfilesConsumo porcentajes, objExc;                          // NUEVA INSTANCIA DE OBJETO
    private PersonaDAO personaDao;

    private double ingresosmensuales, gastosRecurrentes, excedenteMensual;
    private int perfilconsumo;
    private static final String[] DIAS_SEMANA = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

    public void initialize() {
        this.personaDao = new PersonaDAO();

        String usuario = SesionUsuario.getUsuarioLog();

        String[] datosStr = personaDao.SeleccionarUnoDS(usuario);
        double[] datosDbl = personaDao.SeleccionarUnoDDouble(usuario);


        this.porcentajes = new PerfilesConsumo();
        this.objExc = new PerfilesConsumo();

        this.ingresosmensuales = datosDbl[2];
        this.gastosRecurrentes = datosDbl[3]+datosDbl[4]+datosDbl[5]+datosDbl[6]+datosDbl[7]+datosDbl[8];


        try {
            this.excedenteMensual = objExc.getExcedenteMensual(ingresosmensuales, gastosRecurrentes);
        } catch (Exception e) {
            this.excedenteMensual = 0;
        }


        this.perfilconsumo = (int) datosDbl[10] + 1;

    }


    public void CambiarContinuar(ActionEvent actionEvent) {

        if(this.txt_porcAhorro.getText().isEmpty() || this.txt_porcInversion.getText().isEmpty()){
            System.out.println("Error: Debe ingresar un porcentaje de ahorro e inversión.");
            return;
        }
        double porcentajeAhorro = convertStringToDouble(this.txt_porcAhorro.getText());
        double porcentajeInversion = convertStringToDouble(this.txt_porcInversion.getText());

        double[] porcentajesAI = porcentajes._porcentajesAhorroEInversion(porcentajeAhorro, porcentajeInversion);

        porcentajeAhorro = porcentajesAI[0];
        porcentajeInversion = porcentajesAI[1];


        // Crear instancia de PerfilesConsumo
        PerfilesConsumo perfilGlobal = new PerfilesConsumo (ingresosmensuales, gastosRecurrentes, porcentajeAhorro, porcentajeInversion, this.perfilconsumo);
        // Calcular distribución semanal y obtener presupuesto por día
        Map<String, Double> distribucionSemanal = perfilGlobal.calcularDistribucionSemanal();
        System.out.println("MAPA DE DISTRIBUCION SEMANAL:\n");
        printMap(distribucionSemanal);

        System.out.println("PERFILES DE CONSUMO:\n" + perfilGlobal);
        perfilGlobal.setDistribucionSemanal(distribucionSemanal);
        perfilGlobal.setExcedenteMensual(excedenteMensual);

        SesionUsuario.setPerfilStatus(perfilGlobal);// NUEVO SETTER

        // Mostrar distribución semanal de gastos
        System.out.println("\nDistribucion semanal de gastos (presupuesto diario):");

        for (String dia : DIAS_SEMANA) {
            System.out.println(dia + ": $" + Math.round(distribucionSemanal.get(dia)));
        }

    new App().mostrarVista("DashBoard/DashBoard.fxml");

    }

    public void logoutAndExit(ActionEvent actionEvent) {
        SesionUsuario.Logout();
        new App().mostrarVista("Home.fxml");
    }

    public static Double convertStringToDouble(String str) {
        // Reemplaza las comas por puntos
        String replacedStr = str.replace(',', '.');

        try {
            // Intenta convertir el string a double
            return Double.parseDouble(replacedStr);
        } catch (NumberFormatException e) {
            // Imprime un mensaje de error si el string no puede ser convertido a double
            System.out.println("Error: El string contiene caracteres no numéricos o no es un número válido.");
            return null;
        }
    }

    public void printMap(Map<String, Double> map) {
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}
