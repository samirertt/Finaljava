package group11.group11;

import group11.group11.Controller.CartPageController;
import group11.group11.Controller.MoviePageController;
import group11.group11.Controller.LoginPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Random;

public class Main extends Application {

    private static Stage primaryStage;
    private String orderNo; // Field to hold the order number

    @Override
    public void start(Stage stage) {
        primaryStage = stage; // Store the primary stage
        showLoginPage(); // Start with the login page
    }

    // Generate a random order number and store it in the field
    public String generateRandomOrderNo() {
        int orderNo = new Random().nextInt(900000) + 100000; // 6-digit random number
        this.orderNo = String.valueOf(orderNo); // Store it in the field
        return this.orderNo;
    }

    // Get the generated order number
    public String getOrderNo() {
        return this.orderNo;
    }

    // Show the Login page
    public void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/loginPage.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Login Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
            this.orderNo = "100001";
            // Pass reference of this class to the controller
            LoginPageController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Login FXML file.");
        }
    }

    // Show the Movie page
    public void showMoviePage(Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/moviesearch.fxml"));
            Parent root = loader.load();
            MoviePageController controller = loader.getController();
            controller.setCurrentUser(user);
            controller.setProfileDetails();
            controller.setMainApp(this);
            primaryStage.setTitle("Movie Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Movie FXML file.");
        }
    }
    public void showDaySelectionPage(Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/daySessionHallSelection.fxml"));
            Parent root = loader.load();
            MoviePageController controller = loader.getController();
            controller.setCurrentUser(user);
            controller.setProfileDetails();
            primaryStage.setTitle("Day Selection Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Movie FXML file.");
        }
    }


    // Show the Cart page
    public void showCartPage() {
        try {
            System.out.println("Opening cart page...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/cart.fxml"));
            Parent root = loader.load();

            // Ensure the correct controller is set
            CartPageController controller = loader.getController();
            System.out.println("Setting mainApp in CartPageController...");
            controller.setMainApp(this); // Pass the mainApp reference to the controller

            System.out.println("Initializing data in CartPageController...");
            controller.initializeData(); // Initialize data after mainApp is set

            primaryStage.setTitle("Cart Page"); // Use primaryStage
            primaryStage.setScene(new Scene(root)); // Set the scene for the primaryStage
            primaryStage.show(); // Show the primaryStage

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Cart FXML file.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}