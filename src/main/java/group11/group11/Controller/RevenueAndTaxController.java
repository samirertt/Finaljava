package group11.group11.Controller;

import group11.group11.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RevenueAndTaxController {

    @FXML
    private Button menuButton;

    @FXML
    private TextField totalRevenueField;

    @FXML
    private TextField totalTaxField;

    private Main mainApp; // Reference to the Main class

    // Setter for the Main reference
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        // Set action for the menuButton
        menuButton.setOnAction(event -> {
            if (mainApp != null) {
                mainApp.showManagerPage(); // Navigate back to the manager page
            }
        });
    }
}