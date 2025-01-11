package group11.group11.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class managerController {


    @FXML
    private Button productButton;

    @FXML
    private Button cinemaPriceButton;

    @FXML
    private Button discountsButton;

    @FXML
    private Button revenueAndTaxButton;

    @FXML
    private Button employeeButton;

    @FXML
    public void initialize() {
        productButton.setOnAction(event -> openPage("product"));
        employeeButton.setOnAction(event -> openPage("employee"));
        cinemaPriceButton.setOnAction(event -> openPage("cinemaPrice"));
        discountsButton.setOnAction(event -> openPage("AgeBasedDiscount"));
        revenueAndTaxButton.setOnAction(event -> openPage("revenueAndTax"));
    }

    private void openPage(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx1/"+pageName+".fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(pageName+" Page");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) productButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(pageName +" There was an error loading the page: " + e.getMessage());
        }
    }
}
