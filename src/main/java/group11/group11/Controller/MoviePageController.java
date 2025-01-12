package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Movie;
import group11.group11.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MoviePageController implements Initializable {

    private final Facade facade = new Facade();
    public Button movieSearch_windowClose_btn;

    private Users currentUser;
    private int session_id;
    private String previousPage;
    private ObservableList<Movie> movieList;

    private Main mainApp;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    private RadioButton movieSearch_searchByFullName;

    @FXML
    private RadioButton movieSearch_searchByGenre;

    @FXML
    private RadioButton movieSearch_searchByPartialName;

    @FXML
    private TextField movieSearch_searchMovie;

    @FXML
    private TableView<Movie> movieSearchTableView;

    @FXML
    private TableColumn<Movie, String> movieSearch_tableViewMovieTitle;

    @FXML
    private TableColumn<Movie, String> movieSearch_tableViewMovieGenre;

    @FXML
    private TableColumn<Movie, String> movieSearch_tableViewMovieDuration;

    @FXML
    private TableColumn<Movie, String> movieSearch_tableViewMovieShowingDate;

    @FXML
    private ImageView movieSearch_Image;

    @FXML
    private Button logoutButton;

    @FXML
    private Label movieSearch_Name;

    @FXML
    private Label movieSearch_Genre;

    @FXML
    private Spinner<Integer> movieSearch_spinner;

    @FXML
    private Button movieSearch_windowMinimize_btn;

    @FXML
    private Label movieSearch_movieInformation;

    @FXML
    private Button searchMovie_cart;

    @FXML
    private Button movieSearch_ticketBuy_btn;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

    @FXML
    public void handleLogoutButton(ActionEvent event) {
        if (mainApp != null) {
            mainApp.logout();
        }
    }

    @FXML
    private void handleOpenCartPage() {
        System.out.println("Cart button clicked! movie");
        if (mainApp != null) {
            System.out.println("is not null");
            //mainApp.showCartPage(session_id, selectedMovie, currentUser, "moviePage");
        }
    }

    @FXML
    private void handleWindowClose() {
        System.exit(0);
    }

    public void movieSearch_windowClose_btn() {
        System.exit(0);
    }

    public void MovieSearch_windowMinimize() {
        Stage stage = (Stage) movieSearch_windowMinimize_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    public void setProfileDetails() {
        if (currentUser != null) {
            movieSearch_profileName.setText(currentUser.getUsername());
            movieSearch_profileRole.setText(currentUser.getrole());
        }
    }

    private void searchMovie(String searchType) {
        String searchQuery = movieSearch_searchMovie.getText().trim();
        if (searchQuery.isEmpty()) {
            movieList.clear();
            return;
        }

        List<Movie> movies = switch (searchType) {
            case "fullName" -> List.of(facade.searchMovieByFullName(searchQuery));
            case "genre" -> facade.searchMovieByGenre(searchQuery);
            case "partialName" -> facade.searchMovieByPartialName(searchQuery);
            default -> null;
        };

        if (movies != null) {
            updateTableWithMovies(movies);
        } else {
            movieList.clear();
        }
    }

    private void updateTableWithMovies(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
    }

    public void displayMovieDetails(Movie movie) {
        movieSearch_Name.setText(movie.getMovieName());
        movieSearch_Genre.setText(movie.getMovieGenre());
        movieSearch_movieInformation.setText(movie.getMovieSummary());
        System.out.println("path:");
        System.out.println(movie.getMovieImage());
        if (movie.getMovieImage() != null && !movie.getMovieImage().isEmpty()) {
            try {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(movie.getMovieImage())));
                movieSearch_Image.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                movieSearch_Image.setImage(null);
            }
        } else {
            movieSearch_Image.setImage(null);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the spinner
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1); // Step by 1
        movieSearch_spinner.setValueFactory(valueFactory);

        movieSearch_spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (mainApp != null) {
                mainApp.setNumOfTicket(newValue);
            }
        });

        // Initialize the rest of the UI components
        ToggleGroup searchToggleGroup = new ToggleGroup();
        movieSearch_searchByFullName.setToggleGroup(searchToggleGroup);
        movieSearch_searchByGenre.setToggleGroup(searchToggleGroup);
        movieSearch_searchByPartialName.setToggleGroup(searchToggleGroup);

        movieSearch_tableViewMovieTitle.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        movieSearch_tableViewMovieGenre.setCellValueFactory(new PropertyValueFactory<>("movieGenre"));
        movieSearch_tableViewMovieDuration.setCellValueFactory(new PropertyValueFactory<>("movieDuration"));
        movieSearch_tableViewMovieShowingDate.setCellValueFactory(new PropertyValueFactory<>("movieReleaseYear"));

        movieList = FXCollections.observableArrayList();
        movieSearchTableView.setItems(movieList);

        movieList.addAll(facade.initializeMovieTable());

        // Add a listener to the search TextField
        movieSearch_searchMovie.textProperty().addListener((observable, oldValue, newValue) -> {
            if (searchToggleGroup.getSelectedToggle() == movieSearch_searchByFullName) {
                searchMovie("fullName");
            } else if (searchToggleGroup.getSelectedToggle() == movieSearch_searchByGenre) {
                searchMovie("genre");
            } else if (searchToggleGroup.getSelectedToggle() == movieSearch_searchByPartialName) {
                searchMovie("partialName");
            }
        });

        movieSearchTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            displayMovieDetails(newSelection);
        });

        // Debugging button click handler
        searchMovie_cart.setOnAction(event -> {
            System.out.println("Cart button clicked!");
            handleOpenCartPage();
        });

        setProfileDetails();

        movieSearch_ticketBuy_btn.setOnAction(this::btnSelect);

        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
        }
    }

    @FXML
    public void btnSelect(ActionEvent event) {
        try {
            // Get the selected movie from the TableView
            mainApp.setSelectedMovie(movieSearchTableView.getSelectionModel().getSelectedItem());

            if (mainApp.getSelectedMovie() == null) {
                // Show an alert or message to the user to select a movie first
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Movie Selected");
                alert.setContentText("Please select a movie in the table.");
                alert.showAndWait();
                return;
            }

            if (mainApp != null) {
                mainApp.showDaySelectionPage(currentUser, mainApp.getSelectedMovie());
            }
        } catch (Exception e) {
            System.out.println("Error occurred while loading page");
            e.printStackTrace();
        }
    }
}