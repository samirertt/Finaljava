package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class seatSelectionController {
    Facade facade = new Facade();
    @FXML
    private Button A1;

    @FXML
    private Button A2;

    @FXML
    private Button A3;

    @FXML
    private Button A4;

    @FXML
    private Button A5;

    @FXML
    private Button A6;

    @FXML
    private Button A7;

    @FXML
    private Button A8;

    @FXML
    private Button B1;

    @FXML
    private Button B2;

    @FXML
    private Button B3;

    @FXML
    private Button B4;

    @FXML
    private Button B5;

    @FXML
    private Button B6;

    @FXML
    private Button B7;

    @FXML
    private Button B8;

    @FXML
    private Button C1;

    @FXML
    private Button C2;

    @FXML
    private Button C3;

    @FXML
    private Button C4;

    @FXML
    private Button C5;

    @FXML
    private Button C6;

    @FXML
    private Button C7;

    @FXML
    private Button C8;

    @FXML
    private Button D1;

    @FXML
    private Button D2;

    @FXML
    private Button D3;

    @FXML
    private Button D4;

    @FXML
    private Button D5;

    @FXML
    private Button D6;

    @FXML
    private Button D7;

    @FXML
    private Button D8;

    @FXML
    private Button E1;

    @FXML
    private Button E2;

    @FXML
    private Button E3;

    @FXML
    private Button E4;

    @FXML
    private Button E5;

    @FXML
    private Button E6;

    @FXML
    private Button E7;

    @FXML
    private Button E8;

    @FXML
    private Button F1;

    @FXML
    private Button F2;

    @FXML
    private Button F3;

    @FXML
    private Button F4;

    @FXML
    private Button F5;

    @FXML
    private Button F6;

    @FXML
    private Button F7;

    @FXML
    private Button F8;

    @FXML
    private Button productPurchase;

    @FXML
    private GridPane seatGrid;

    @FXML
    private Main mainApp;
    private Movie selectedMovie;

    public void setSelectedMovie(Movie selectedMovie)
    {
        this.selectedMovie = selectedMovie;
    }
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
        System.out.println("Session ID set to: " + sessionId); // Debugging
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
            case "5": return A5;
            case "6": return A6;
            case "7": return A7;
            case "8": return A8;
            case "9": return B1;
            case "10": return B2;
            case "11": return B3;
            case "12": return B4;
            case "13": return B5;
            case "14": return B6;
            case "15": return B7;
            case "16": return B8;
            case "17": return C1;
            case "18": return C2;
            case "19": return C3;
            case "20": return C4;
            case "21": return C5;
            case "22": return C6;
            case "23": return C7;
            case "24": return C8;
            case "25": return D1;
            case "26": return D2;
            case "27": return D3;
            case "28": return D4;
            case "29": return D5;
            case "30": return D6;
            case "31": return D7;
            case "32": return D8;
            case "33": return E1;
            case "34": return E2;
            case "35": return E3;
            case "36": return E4;
            case "37": return E5;
            case "38": return E6;
            case "39": return E7;
            case "40": return E8;
            case "41": return F1;
            case "42": return F2;
            case "43": return F3;
            case "44": return F4;
            case "45": return F5;
            case "46": return F6;
            case "47": return F7;
            case "48": return F8;
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

        int number = facade.productExistsInCart(mainApp.getOrderNo(), selectedMovie.getMovieName());
        // Update the database
        if (number > 0) {
            facade.updateProductQuantity(mainApp.getOrderNo(), selectedMovie.getMovieName(), number+1);
        } else {
            facade.addProductToCart(mainApp.getOrderNo(), selectedMovie.getMovieName(), 200, 1);
        }
    }


    // check if seats are selected
    @FXML
    public void btnProductPurchase(ActionEvent event) {
        mainApp.ProductPurchase();
    }
}