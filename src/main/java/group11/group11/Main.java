package group11.group11;

import group11.group11.Controller.MoviePageController;
import group11.group11.Controller.LoginPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage; // Store the primary stage
        showLoginPage(); // Start with the login page
    }

    public void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/loginPage.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Login Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();

            // Pass reference of this class to the controller
            LoginPageController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Login FXML file.");
        }
    }

    public void showMoviePage(Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/moviesearch.fxml"));
            Parent root = loader.load();
            MoviePageController controller = loader.getController();
            controller.setCurrentUser(user);
            controller.setProfileDetails();
            primaryStage.setTitle("Movie Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Movie FXML file.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
