package group11.group11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

public class Login_page extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/loginPage.fxml"));
            Parent root = loader.load();

            // Set up the stage and scene
            primaryStage.setTitle("Login Page");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // Show the stage
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the FXML file. Ensure the path and controller are correct.");
        }
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}
