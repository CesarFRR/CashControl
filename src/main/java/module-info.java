module com.unal.cash {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics; // **Agrega javafx.graphics**



    exports com.unal.cash.Controller to javafx.fxml;
    exports com.unal.cash to javafx.graphics;
    opens com.unal.cash.Controller to javafx.fxml;
    exports com.unal.cash.Controller.Login to javafx.fxml;
    exports com.unal.cash.Controller.AhorroInversion to javafx.fxml;
    exports com.unal.cash.Controller.MetodosdePago to javafx.fxml;
    exports com.unal.cash.Controller.PerfilConsumo to javafx.fxml;
    exports com.unal.cash.Controller.RegistroIngresosMensuales to javafx.fxml;
    exports com.unal.cash.Controller.SobreNosotros to javafx.fxml;
    exports com.unal.cash.Controller.AgregarTransaccion to javafx.fxml;



    opens com.unal.cash.Controller.Login to javafx.fxml;
    opens com.unal.cash.Controller.AhorroInversion to javafx.fxml;
    opens com.unal.cash.Controller.MetodosdePago to javafx.fxml;
    opens com.unal.cash.Controller.PerfilConsumo to javafx.fxml;
    opens com.unal.cash.Controller.RegistroIngresosMensuales to javafx.fxml;
    opens com.unal.cash.Controller.SobreNosotros to javafx.fxml;
    opens com.unal.cash.Controller.AgregarTransaccion to javafx.fxml;


}
