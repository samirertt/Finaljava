package group11.group11.Controller;
import group11.group11.*;
import group11.group11.Controller.CartPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class seatSelectionController {

    private Users currentUser;
    Facade facade = new Facade();

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    private Button A1, A2, A3, A4, A5, A6, A7, A8;
    @FXML
    private Button B1, B2, B3, B4, B5, B6, B7, B8;
    @FXML
    private Button C1, C2, C3, C4, C5, C6, C7, C8;
    @FXML
    private Button D1, D2, D3, D4, D5, D6, D7, D8;
    @FXML
    private Button E1, E2, E3, E4, E5, E6, E7, E8;
    @FXML
    private Button F1, F2, F3, F4, F5, F6, F7, F8;

    @FXML
    private Button searchMovie_cart;

    @FXML
    private Button backButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button productPurchase;

    @FXML
    private GridPane seatGrid;

    @FXML
    private Main mainApp;

    @FXML
    private Button movieSearch_windowMinimize_btn;


    public void setMainApp(Main mainApp)
    {
        this.mainApp = mainApp;
        initializeAfterMainApp();
    }

    public void movieSearch_windowClose_btn() {
        System.exit(0);
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
    public void handleBackButton(ActionEvent event) {
        if (mainApp != null && currentUser != null) {
            mainApp.showDaySelectionPage(currentUser, mainApp.getSelectedMovie());
        }
    }

    @FXML
    private void handleOpenCartPage() {
        System.out.println("Cart button clicked! movie");
        if (mainApp != null) {
            System.out.println("is not null");
            //mainApp.showCartPage(sessionId, selectedMovie, currentUser, "A");
        }
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

        productPurchase.setDisable(true); // Default state

        searchMovie_cart.setOnAction(event -> {
            System.out.println("Cart button clicked!");
            handleOpenCartPage();
        });

        if (backButton != null) {
            backButton.setOnAction(this::handleBackButton);
        }

        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
        }
    }
    private void initializeAfterMainApp()
    {
        if (mainApp.getSelectedSession()!= 0)
        {
            initializeSeatAvailability();

            /*
            if (!Facade.isEnoughSeat(mainApp.getNumOfTicket(), mainApp.getSelectedSession())) {
                showAlert("Not enough seats available!");
                return;
            }
            */
        }

        if(mainApp.getSelectedSeats().size() == mainApp.getNumOfTicket()) {
            productPurchase.setDisable(false);
        } else {
            productPurchase.setDisable(true);
        }
    }


    private void initializeSeatAvailability()
    {

        List<String> unavailableSeats = Facade.getUnavailableSeats(mainApp.getSelectedSession());
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

        if (mainApp != null && mainApp.getSelectedSeats() != null) {
            for (String seat : mainApp.getSelectedSeats()) {
                Button seatButton = getSelectedSeatButton(seat);
                if (seatButton != null)
                {
                    seatButton.setStyle("-fx-background-color: #808080; "
                            + "-fx-border-color: black; "
                            + "-fx-border-width: 3px; "
                            + "-fx-text-fill: white; "
                            + "-fx-font-weight: bold;");
                    seatButton.setDisable(false); // Ensure the seat is enabled
                } else {
                    System.out.println("Seat button not found for: " + seat); // Debugging
                }
            }
        } else {
            System.err.println("mainApp or selectedSeats is null. Cannot restore selected seats.");
        }

    }

    private Button getSeatButton(String seatNumber) {
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

    private Button getSelectedSeatButton(String seatName) {
        switch (seatName) {
            case "A1": return A1;
            case "A2": return A2;
            case "A3": return A3;
            case "A4": return A4;
            case "A5": return A5;
            case "A6": return A6;
            case "A7": return A7;
            case "A8": return A8;
            case "B1": return B1;
            case "B2": return B2;
            case "B3": return B3;
            case "B4": return B4;
            case "B5": return B5;
            case "B6": return B6;
            case "B7": return B7;
            case "B8": return B8;
            case "C1": return C1;
            case "C2": return C2;
            case "C3": return C3;
            case "C4": return C4;
            case "C5": return C5;
            case "C6": return C6;
            case "C7": return C7;
            case "C8": return C8;
            case "D1": return D1;
            case "D2": return D2;
            case "D3": return D3;
            case "D4": return D4;
            case "D5": return D5;
            case "D6": return D6;
            case "D7": return D7;
            case "D8": return D8;
            case "E1": return E1;
            case "E2": return E2;
            case "E3": return E3;
            case "E4": return E4;
            case "E5": return E5;
            case "E6": return E6;
            case "E7": return E7;
            case "E8": return E8;
            case "F1": return F1;
            case "F2": return F2;
            case "F3": return F3;
            case "F4": return F4;
            case "F5": return F5;
            case "F6": return F6;
            case "F7": return F7;
            case "F8": return F8;
            default: return null;
        }
    }

    @FXML
    public void onSeatClicked(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String seatName = clickedButton.getText();

        if (mainApp.getSelectedSeats().contains(seatName)) {
            // Deselect the seat
            mainApp.getSelectedSeats().remove(seatName);
            clickedButton.setStyle(""); // Reset the button style
            System.out.println("Seat " + seatName + " deselected.");

        } else {
            // Check if the number of selected seats has reached the limit
            if (mainApp.getSelectedSeats().size() >= mainApp.getNumOfTicket()) {
                showAlert("You have already selected the maximum number of seats (" + mainApp.getNumOfTicket() + ").");
                return;
            }

            // Select the seat
            mainApp.getSelectedSeats().add(seatName);
            productPurchase.setDisable(mainApp.getSelectedSeats().isEmpty());
            clickedButton.setStyle("-fx-background-color: #808080; "
                    + "-fx-border-color: black; "
                    + "-fx-border-width: 3px; "
                    + "-fx-text-fill: white; "
                    + "-fx-font-weight: bold;");
            System.out.println("Seat " + seatName + " selected.");

        }
    }

    @FXML
    public void btnProductPurchase(ActionEvent event)
    {
        mainApp.getTicketList().clear();
        for(String seat: mainApp.getSelectedSeats())
        {
            Ticket ticket = new Ticket(mainApp.getSelectedSession(), mainApp.getSelectedHall(), seat, mainApp.getOrderNo(), mainApp.getSelectedMovie().getMovieName());
            mainApp.getTicketList().add(ticket);
        }

        mainApp.ProductPurchase(mainApp.getSelectedSession(), mainApp.getSelectedMovie(),currentUser, "B");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}