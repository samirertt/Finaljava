package group11.group11.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;


public class MoviePageController {

    @FXML
    private Button movieSearch_windowClose_btn;

    @FXML
    private Button movieSearch_windowMinimize_btn;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_movieName;

    @FXML
    private Label movieSearch_movieGenre;

    @FXML
    private RadioButton movieSearch_searchByGenre;

    @FXML
    private RadioButton movieSearch_searchByPartialName;

    @FXML
    private RadioButton movieSearch_searchByFullName;

    @FXML
    private TextField movieSearch_searchMovie;

    @FXML
    private TableView<?> movieSearch_tableView;

    @FXML
    private TableColumn<?, ?> movieSearch_tableViewMovieTitle;

    @FXML
    private TableColumn<?, ?> movieSearch_tableViewMovieGenre;

    @FXML
    private TableColumn<?, ?> movieSearch_tableViewMovieDuration;

    @FXML
    private TableColumn<?, ?> movieSearch_tableViewMovieShowingDate;

    @FXML
    private Button movieSearch_movieInfo_btn;

    @FXML
    private Spinner<Integer> classSpinner1;

    @FXML
    private Spinner<Integer> classSpinner2;

    @FXML
    private Button movieSearch_ticketClear_btn;

    @FXML
    private Button movieSearch_ticketReceipt_btn;

    @FXML
    private Button movieSearch_ticketBuy_btn;

    @FXML
    private Label totalLabel;

    @FXML
    private ImageView movieImage;

    // Initialize method for setting up the UI and adding listeners
    @FXML
    public void initialize() {
        // Group RadioButtons
        ToggleGroup searchOptions = new ToggleGroup();
        movieSearch_searchByGenre.setToggleGroup(searchOptions);
        movieSearch_searchByPartialName.setToggleGroup(searchOptions);
        movieSearch_searchByFullName.setToggleGroup(searchOptions);

        // Configure Spinners
        classSpinner1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        classSpinner2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));

        // Set up TableView columns if needed
    }

    // Event handler for closing the window
    @FXML
    private void handleCloseWindow() {
        System.exit(0);
    }

    // Event handler for minimizing the window
    @FXML
    private void handleMinimizeWindow() {
        // Add logic to minimize the window
    }

    // Event handler for searching movies
    @FXML
    private void handleSearchMovie() {
        String query = movieSearch_searchMovie.getText();
        // Add logic to perform movie search based on selected RadioButton
    }

    // Event handler for displaying movie info
    @FXML
    private void handleMovieInfo() {
        // Add logic to display selected movie info
    }

    // Event handler for clearing ticket selection
    @FXML
    private void handleClearTickets() {
        classSpinner1.getValueFactory().setValue(1);
        classSpinner2.getValueFactory().setValue(1);
        totalLabel.setText("0TL");
    }

    // Event handler for generating a receipt
    @FXML
    private void handleGenerateReceipt() {
        // Add logic to generate and display a receipt
    }

    // Event handler for buying tickets
    @FXML
    private void handleBuyTickets() {
        int quantity1 = classSpinner1.getValue();
        int quantity2 = classSpinner2.getValue();
        // Calculate total and display it
    }
}
