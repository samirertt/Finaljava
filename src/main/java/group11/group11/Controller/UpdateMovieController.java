package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Movie;
import group11.group11.Users;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.layout.VBox;

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

        @FXML
        private Button menuButton;

        private String posterFilePath;

        @FXML
        public void initialize() {
            movieIdColumn.setCellValueFactory(new PropertyValueFactory<>("movieId"));
            moviesNameColumn.setCellValueFactory(new PropertyValueFactory<>("moviesName"));
            moviesGenreColumn.setCellValueFactory(new PropertyValueFactory<>("moviesGenre"));
            moviesSummaryColumn.setCellValueFactory(new PropertyValueFactory<>("moviesSummary"));
            moviesImageColumn.setCellValueFactory(new PropertyValueFactory<>("moviesImage"));

            fetchMovies();

            updateFieldsContainer.setVisible(false);
            movieIdField.setEditable(false);

            fetchMovieButton.setOnAction(event -> handleFetchMovieButtonAction());
            choosePosterButton.setOnAction(event -> handleChoosePosterButtonAction());
            updateMovieButton.setOnAction(event -> handleUpdateMovieButtonAction());
            menuButton.setOnAction(event -> handleMenuButtonAction());
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

        @FXML
        private void handleMenuButtonAction() {
            // Navigate back to the main page
            MainpageController.navigateToMainPage(menuButton);
        }

        private void fetchMovies() {
            List<Movie> moviesList = Facade.fetchAllMovies();
            moviesTable.setItems(FXCollections.observableArrayList(moviesList));
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
            File baseDirectory = new File("/home/sam/IdeaProjects/Group11/src/main/resources/group11/group11/images/");

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Poster Image");
            fileChooser.setInitialDirectory(baseDirectory);
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );

            File selectedFile = fileChooser.showOpenDialog(choosePosterButton.getScene().getWindow());

            if (selectedFile != null) {
                String absolutePath = selectedFile.getAbsolutePath();

                if (absolutePath.startsWith(baseDirectory.getAbsolutePath())) {
                    String relativePath = absolutePath.substring(baseDirectory.getAbsolutePath().length());

                    posterFilePath = "/group11/group11/images" + relativePath;

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

