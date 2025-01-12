package group11.group11.Controller;

import group11.group11.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class managerController {


    @FXML
    private Button productButton;

    @FXML
    private Button discountsButton;

    @FXML
    private Button revenueAndTaxButton;

    @FXML
    private Button employeeButton;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    public void initialize() {
        productButton.setOnAction(event -> mainApp.handleProductButton());
        discountsButton.setOnAction(event -> mainApp.handleDiscountsButton());
        revenueAndTaxButton.setOnAction(event -> mainApp.handleRevenueAndTaxButton());
        employeeButton.setOnAction(event -> mainApp.handleEmployeeButton());
    }

}
