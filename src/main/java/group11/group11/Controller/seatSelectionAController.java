package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Movie;
import group11.group11.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    Facade facade = new Facade();

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

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
            mainApp.showCartPage();
        }
    }

    private List<String> selectedSeats = new ArrayList<>();
    private int sessionId; // Add this field to store the session ID

    public void setSelectedMovie(Movie selectedMovie)
    {
        this.selectedMovie = selectedMovie;
        mainApp.setSelectedMovie(selectedMovie);
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
        // Call initializeSeatAvailability here if sessionId is already set

        searchMovie_cart.setOnAction(event -> {
            System.out.println("Cart button clicked!");
            handleOpenCartPage();
        });

        if (sessionId != 0) {
            initializeSeatAvailability();
        }

        if (backButton != null) {
            backButton.setOnAction(this::handleBackButton);
        }

        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
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

        int number = facade.productExistsInCart(mainApp.getOrderNo(), selectedMovie.getMovieName());
        // Update the database
        if (number > 0) {
            facade.updateProductQuantity(mainApp.getOrderNo(), selectedMovie.getMovieName(), number+1);
        } else {
            facade.addProductToCart(mainApp.getOrderNo(), selectedMovie.getMovieName(), 200, 1);
        }
    }

    @FXML
    public void btnProductPurchase(ActionEvent event) {
        mainApp.ProductPurchase(sessionId, selectedMovie, currentUser, "A");
    }
}