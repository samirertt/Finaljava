package group11.group11.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class seatSelectionAController {
    @FXML
    private Button A1;

    @FXML
    private Button A2;

    @FXML
    private Button A3;

    @FXML
    private Button A4;

    @FXML
    private Button B1;

    @FXML
    private Button B2;

    @FXML
    private Button B3;

    @FXML
    private Button B4;

    @FXML
    private Button C1;

    @FXML
    private Button C2;

    @FXML
    private Button C3;

    @FXML
    private Button C4;

    @FXML
    private Button D1;

    @FXML
    private Button D2;

    @FXML
    private Button D3;

    @FXML
    private Button D4;
    
    @FXML
    private Button productPurchase;


    @FXML
    private GridPane seatGrid;

    private List<String> selectedSeats = new ArrayList<>();

    @FXML
    public void onSeatClicked(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String seatName = clickedButton.getText();
        selectedSeats.add(seatName);
        System.out.println("Seat " + seatName + " selected.");

        clickedButton.setStyle("-fx-background-color: #808080; "
                + "-fx-border-color: black; "
                + "-fx-border-width: 3px; "
                + "-fx-text-fill: white; "
                + "-fx-font-weight: bold;");
    }

    @FXML
    public void btnProductPurchase(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/group11/group11/fxml/customer_products.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) productPurchase.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error occurred while loading page");
            e.printStackTrace();
        }

    }

}
