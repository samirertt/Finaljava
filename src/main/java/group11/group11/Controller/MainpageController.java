package group11.group11.Controller;

import group11.group11.Main;
import group11.group11.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainpageController {

    private Main mainApp;
    private Users currentUser;

    @FXML
    private Button addMovieButton;

    @FXML
    private Button updateMovieButton;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    private Button logoutButton;

    @FXML
    private Button movieSearch_windowMinimize_btn;

    @FXML
    private Button addScheduleButton;

    @FXML
    private Button updateScheduleButton;

    @FXML
    private Button cancelRefundButton;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

    public void movieSearch_windowClose_btn() {
        System.exit(0);
    }


    public void setProfileDetails() {
        if (currentUser != null) {
            movieSearch_profileName.setText(currentUser.getUsername());
            movieSearch_profileRole.setText(currentUser.getrole());
        }
    }


    public void MovieSearch_windowMinimize() {
        Stage stage = (Stage) movieSearch_windowMinimize_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void handleLogoutButton(ActionEvent event) {
        if (mainApp != null) {
            mainApp.logout();
        }
    }



    @FXML
    public void initialize() {
        addMovieButton.setOnAction(event -> mainApp.showAddMovie(currentUser));
        updateMovieButton.setOnAction(event -> mainApp.showUpdateMoviePage(currentUser));
        addScheduleButton.setOnAction(event -> mainApp.showAddSchedule(currentUser));
        updateScheduleButton.setOnAction(event -> mainApp.showUpdateSchedule(currentUser));
        cancelRefundButton.setOnAction(event -> mainApp.showCancelAndRefund(currentUser));

        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
        }
    }

    public static void navigateToMainPage(Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(MainpageController.class.getResource("/com/example/admin/Mainpage.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Main Page");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            Stage currentStage = (Stage) button.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error loading the main page: " + e.getMessage());
        }
    }
}