package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

public class RevenueAndTaxController {

    @FXML
    private Button menuButton;

    @FXML
    private TextField totalRevenueField;

    @FXML
    private TextField totalTaxField;

    private Main mainApp;

    private Users currentUser;


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

    @FXML
    private Label movieSearch_profileName;
    @FXML
    public void handleLogoutButton(ActionEvent event) {
        if (mainApp != null) {
            mainApp.logout();
        }
    }
    @FXML
    private Label movieSearch_profileRole;

    @FXML private Button movieSearch_windowMinimize_btn;

    public void movieSearch_windowClose_btn() {
        System.exit(0);
    }


    public void MovieSearch_windowMinimize() {
        Stage stage = (Stage) movieSearch_windowMinimize_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    public void setProfileDetails() {
        if (currentUser != null) {
            movieSearch_profileName.setText(currentUser.getUsername());
            movieSearch_profileRole.setText(currentUser.getrole());
        }
    }

    @FXML
    public void initialize() {
        menuButton.setOnAction(event -> {
            if (mainApp != null) {
                mainApp.showManagerPage(mainApp.getCurrentUser());
            }
        });
        displayRevenueAndTax();
    }

    private void displayRevenueAndTax() {
        // Call the method to calculate total revenue and tax
        Map<String, Double> result = Facade.calculateTaxAmount();


        double totalRevenue = result.get("revenue");
        double totalTax = result.get("totalTax");

        totalRevenueField.setText(String.format("%.2f", totalRevenue));
        totalTaxField.setText(String.format("%.2f", totalTax));
    }
}