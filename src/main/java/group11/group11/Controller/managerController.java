package group11.group11.Controller;

import group11.group11.Main;
import group11.group11.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class managerController {

    @FXML
    private Button productButton;

    @FXML
    private Button discountsButton;

    @FXML
    private Button revenueAndTaxButton;

    @FXML
    private Button employeeButton;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    private Main mainApp;
    private Users currentUser;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

    public void setProfileDetails() {
        if (currentUser != null) {
            movieSearch_profileName.setText(currentUser.getUsername());
            movieSearch_profileRole.setText(currentUser.getrole());
        }
    }

    @FXML
    public void initialize() {
        productButton.setOnAction(event -> mainApp.handleProductButton());
        discountsButton.setOnAction(event -> mainApp.handleDiscountsButton());
        revenueAndTaxButton.setOnAction(event -> mainApp.handleRevenueAndTaxButton());
        employeeButton.setOnAction(event -> mainApp.handleEmployeeButton());
    }

}
