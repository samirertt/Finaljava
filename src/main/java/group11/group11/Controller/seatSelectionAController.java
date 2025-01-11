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

public class seatSelectionAController {

    private Movie selectedMovie;
    private Users currentUser;
    private Time sessionTime;
    private int sessionId;
    private String previousPage;

    Facade facade = new Facade();

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    private Button A1, A2, A3, A4, B1, B2, B3, B4, C1, C2, C3, C4, D1, D2, D3, D4;

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
            mainApp.showDaySelectionPage(currentUser, selectedMovie);
        }
    }

    @FXML
    private void handleOpenCartPage() {
        System.out.println("Cart button clicked! movie");
        if (mainApp != null) {
            System.out.println("is not null");
            mainApp.showCartPage(sessionId, selectedMovie, currentUser, "A");
        }
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
        if (mainApp != null) {
            mainApp.setSelectedMovie(selectedMovie);
        }
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
        initializeSeatAvailability();
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

        if (sessionId != 0) {
            initializeSeatAvailability();

            if (!Facade.isEnoughSeat(mainApp.getNumOfTicket(), mainApp.getSession_id())) {
                showAlert("Not enough seats available!");
                return;
            }
        }

        if (backButton != null) {
            backButton.setOnAction(this::handleBackButton);
        }

        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
        }
    }
    private void initializeAfterMainApp() {
        if (mainApp.getSelectedSeats().size() == mainApp.getNumOfTicket()) {
            productPurchase.setDisable(false);
        } else {
            productPurchase.setDisable(true);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void initializeSeatAvailability()
    {

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
    private Button getSelectedSeatButton(String seatName) {
        switch (seatName) {
            case "A1": return A1;
            case "A2": return A2;
            case "A3": return A3;
            case "A4": return A4;
            case "B1": return B1;
            case "B2": return B2;
            case "B3": return B3;
            case "B4": return B4;
            case "C1": return C1;
            case "C2": return C2;
            case "C3": return C3;
            case "C4": return C4;
            case "D1": return D1;
            case "D2": return D2;
            case "D3": return D3;
            case "D4": return D4;
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

            // Remove the ticket from the cart
            Facade.removeProductFromCart(mainApp.getOrderNo(), selectedMovie.getMovieName());
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

            /// Update the database
            Facade.addTicketToCart(mainApp.getOrderNo(), selectedMovie.getMovieName(), Facade.getTicketPriceFromDB(), 1);

        }
    }


    @FXML
    public void btnProductPurchase(ActionEvent event) {
        mainApp.ProductPurchase(sessionId, selectedMovie, currentUser, "A");
    }
}