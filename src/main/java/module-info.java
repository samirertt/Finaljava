module group11.group11 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    requires java.sql;
    requires mysql.connector.j;
    requires fontawesomefx;

    // Open the Controller package to javafx.fxml for reflection
    opens group11.group11.Controller to javafx.fxml;

    // Export the Controller package if needed elsewhere
    exports group11.group11.Controller;

    // Keep the main package open and exported as needed
    opens group11.group11 to javafx.fxml;
    exports group11.group11;
}
