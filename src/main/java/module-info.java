module com.unal.cash {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics; // **Agrega javafx.graphics**


    exports com.unal.cash.Controller to javafx.fxml; // Provide access for FXML
    exports com.unal.cash to javafx.graphics;


    opens com.unal.cash.Controller to javafx.fxml;
    // Open specific packages for FXML controllers:
    opens com.unal.cash.Controller.Login to javafx.fxml;  // Open Login controller package
    // You can add similar opens statements for other controller packages if needed
    opens com.unal.cash.Controller.DashBoard to javafx.fxml, javafx.base;
    opens com.unal.cash.Controller.AhorroInversion to javafx.fxml, javafx.base;
    opens com.unal.cash.Controller.PerfilConsumo to javafx.fxml, javafx.base;
    opens com.unal.cash.Controller.AgregarTransaccion to javafx.fxml, javafx.base;
    opens com.unal.cash.Controller.MetodosdePago to javafx.fxml, javafx.base;
    opens com.unal.cash.Controller.RegistroIngresosMensuales to javafx.fxml, javafx.base;
    opens com.unal.cash.Controller.SobreNosotros to javafx.fxml, javafx.base;


}
