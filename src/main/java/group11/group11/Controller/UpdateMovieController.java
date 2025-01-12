package group11.group11.Controller;

import group11.group11.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class UpdateMovieController {

    private Main mainApp;
    private Users currentUser;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    private TableView<Movie> moviesTable;

    @FXML
    private TableColumn<Movie, Integer> movieIdColumn;

    @FXML
    private TableColumn<Movie, String> moviesNameColumn;

    @FXML
    private TableColumn<Movie, String> moviesGenreColumn;

    @FXML
    private TableColumn<Movie, String> moviesSummaryColumn;

    @FXML
    private TableColumn<Movie, String> moviesImageColumn;

    @FXML
    private TextField movieIdField;

    @FXML
    private TextField moviesNameField;

    @FXML
    private TextField moviesGenreField;

    @FXML
    private TextArea moviesSummaryField;

    @FXML
    private Button choosePosterButton;

    @FXML
    private Button updateMovieButton;

    @FXML
    private ImageView posterImageView;

    @FXML
    private TextField fetchMovieIdField;

    @FXML
    private Button fetchMovieButton;

    @FXML
    private VBox updateFieldsContainer;

    private String posterFilePath;

    @FXML
    private Button backButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button movieSearch_windowMinimize_btn;

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
            mainApp.showAdminPage(currentUser);
        }
    }

    @FXML
    public void initialize() {
        movieIdColumn.setCellValueFactory(new PropertyValueFactory<>("movie_id"));
        moviesNameColumn.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        moviesGenreColumn.setCellValueFactory(new PropertyValueFactory<>("movieGenre"));
        moviesSummaryColumn.setCellValueFactory(new PropertyValueFactory<>("movieSummary"));
        moviesImageColumn.setCellValueFactory(new PropertyValueFactory<>("movieImage"));

        fetchMovies();

        updateFieldsContainer.setVisible(false);
        movieIdField.setEditable(false);

        moviesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fetchMovieIdField.setText(String.valueOf(newSelection.getMovie_id()));
                handleFetchMovieButtonAction();
            }
        });

        fetchMovieButton.setOnAction(event -> handleFetchMovieButtonAction());
        choosePosterButton.setOnAction(event -> handleChoosePosterButtonAction());
        updateMovieButton.setOnAction(event -> handleUpdateMovieButtonAction());

        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
        }

        if (backButton != null) {
            backButton.setOnAction(this::handleBackButton);
        }
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
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

    private void fetchMovies() {
        ObservableList<Movie> moviesList =  FXCollections.observableArrayList();
        moviesTable.setItems(moviesList);
        moviesList.addAll(Facade.fetchAllMovies());

    }

    @FXML
    private void handleFetchMovieButtonAction() {
        String movieIdText = fetchMovieIdField.getText();

        if (movieIdText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a Movie ID!");
            return;
        }

        try {
            int movieId = Integer.parseInt(movieIdText);
            Movie movie = Facade.fetchMovieById(movieId);

            if (movie != null) {
                movieIdField.setText(String.valueOf(movie.getMovie_id()));
                moviesNameField.setText(movie.getMovieName());
                moviesGenreField.setText(movie.getMovieGenre());
                moviesSummaryField.setText(movie.getMovieSummary());
                posterFilePath = movie.getMovieImage();

                if (posterFilePath != null && !posterFilePath.isEmpty()) {
                    Image image = new Image(new File(posterFilePath).toURI().toString());
                    posterImageView.setImage(image);
                } else {
                    posterImageView.setImage(null);
                }

                updateFieldsContainer.setVisible(true);
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "No movie found with the given ID!");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Movie ID!");
        }
    }

    @FXML
    private void handleChoosePosterButtonAction() {
        File baseDirectory = new File(getClass().getResource("/group11/group11/images").getFile());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Poster Image");
        fileChooser.setInitialDirectory(baseDirectory);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(choosePosterButton.getScene().getWindow());

        if (selectedFile != null) {
            if (selectedFile.getAbsolutePath().startsWith(baseDirectory.getAbsolutePath())) {
                // Construct the relative path
                String relativePath = selectedFile.getAbsolutePath().substring(baseDirectory.getAbsolutePath().length());
                posterFilePath = "/group11/group11/images" + relativePath.replace("\\", "/");

                // Load and display the selected image
                Image image = new Image(selectedFile.toURI().toString());
                posterImageView.setImage(image);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "The selected file must be within the images directory.");
            }
        }
    }

    @FXML
    private void handleUpdateMovieButtonAction() {
        String movieIdText = movieIdField.getText();
        String moviesName = moviesNameField.getText();
        String moviesGenre = moviesGenreField.getText();
        String moviesSummary = moviesSummaryField.getText();

        if (movieIdText.isEmpty() || moviesName.isEmpty() || moviesGenre.isEmpty() || moviesSummary.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all fields!");
            return;
        }

        try {
            int movieId = Integer.parseInt(movieIdText);
            boolean success = Facade.updateMovie(movieId, moviesName, moviesGenre, moviesSummary, posterFilePath);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Movie updated successfully!");
                clearFields();
                fetchMovies();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update movie!");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Movie ID!");
        }
    }

    private void clearFields() {
        fetchMovieIdField.clear();
        movieIdField.clear();
        moviesNameField.clear();
        moviesGenreField.clear();
        moviesSummaryField.clear();

        posterImageView.setImage(null);
        posterFilePath = null;
        updateFieldsContainer.setVisible(false);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}