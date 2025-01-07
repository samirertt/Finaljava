package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Main;
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

    @FXML
    private Main mainApp;

    @FXML
    private Button movieSearch_windowMinimize_btn;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void movieSearch_windowClose_btn() {
        System.exit(0);
    }

    public void MovieSearch_windowMinimize() {
        Stage stage = (Stage) movieSearch_windowMinimize_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    private List<String> selectedSeats = new ArrayList<>();
    private int sessionId; // Add this field to store the session ID

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
        initializeSeatAvailability();
    }

    @FXML
    public void initialize() {
        // Call initializeSeatAvailability here if sessionId is already set
        if (sessionId != 0) {
            initializeSeatAvailability();
        }
    }

    private void initializeSeatAvailability() {
        List<String> unavailableSeats = Facade.getUnavailableSeats(sessionId);
        System.out.println("Unavailable seats: " + unavailableSeats); // Debugging

        for (String seat : unavailableSeats) {
            Button seatButton = getSeatButton(seat);
            if (seatButton != null) {
                System.out.println("Disabling seat: " + seat); // Debugging
                seatButton.setStyle("-fx-background-color: #808080; "
                        + "-fx-border-color: black; "
                        + "-fx-border-width: 3px; "
                        + "-fx-text-fill: white; "
                        + "-fx-font-weight: bold;");
                seatButton.setDisable(true);
            } else {
                System.out.println("Seat button not found for: " + seat); // Debugging
            }
        }
    }

    private Button getSeatButton(String seatNumber) {
        // Map integer seat numbers to seat button IDs
        switch (seatNumber) {
            case "1": return A1;
            case "2": return A2;
            case "3": return A3;
            case "4": return A4;
            case "5": return B1;
            case "6": return B2;
            case "7": return B3;
            case "8": return B4;
            case "9": return C1;
            case "10": return C2;
            case "11": return C3;
            case "12": return C4;
            case "13": return D1;
            case "14": return D2;
            case "15": return D3;
            case "16": return D4;

            default: return null;
        }
    }

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
    public void btnProductPurchase(ActionEvent event) {
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