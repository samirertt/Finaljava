package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddMovieController {

    private Main mainApp;
    private Users currentUser;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    private TextField movieNameField;

    @FXML
    private TextField movieGenreField;

    @FXML
    private TextArea movieSummaryField;

    @FXML
    private Button choosePosterButton;

    @FXML
    private Button addMovieButton;


    @FXML
    private Button backButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button movieSearch_windowMinimize_btn;

    @FXML
    private ImageView posterImageView;

    private String posterFilePath;

    /**
     * Sets the main application instance.
     *
     * @param mainApp The main application instance.
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Sets the current logged-in user.
     *
     * @param user The current logged-in user.
     */
    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

    /**
     * Displays the profile details of the current user.
     */
    public void setProfileDetails() {
        if (currentUser != null) {
            movieSearch_profileName.setText(currentUser.getUsername());
            movieSearch_profileRole.setText(currentUser.getrole());
        }
    }

    /**
     * Closes the application.
     */
    public void movieSearch_windowClose_btn() {
        System.exit(0);
    }

    /**
     * Minimizes the application window.
     */
    public void MovieSearch_windowMinimize() {
        Stage stage = (Stage) movieSearch_windowMinimize_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Handles the logout button action.
     *
     * @param event The action event triggered by the logout button.
     */
    @FXML
    public void handleLogoutButton(ActionEvent event) {
        if (mainApp != null) {
            mainApp.logout();
        }
    }

    /**
     * Handles the back button action.
     *
     * @param event The action event triggered by the back button.
     */
    @FXML
    public void handleBackButton(ActionEvent event) {
        if (mainApp != null && currentUser != null) {
            mainApp.showAdminPage(currentUser);
        }

    }

    /**
     * Initializes the controller and sets up event handlers for buttons.
     */
    @FXML
    public void initialize() {
        choosePosterButton.setOnAction(event -> handleChoosePosterButtonAction());
        addMovieButton.setOnAction(event -> handleAddMovieButtonAction());

        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
        }

        if (backButton != null) {
            backButton.setOnAction(this::handleBackButton);
        }
    }

    /**
     * Handles the action for choosing a poster image.
     */
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
                String relativePath = selectedFile.getAbsolutePath().substring(baseDirectory.getAbsolutePath().length());

                posterFilePath = "/group11/group11/images" + relativePath.replace("\\", "/");

                Image image = new Image(selectedFile.toURI().toString());
                posterImageView.setImage(image);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "The selected file must be within the images directory.");
            }
        }
    }

    /**
     * Handles the action for adding a movie.
     */
    @FXML
    private void handleAddMovieButtonAction() {
        String name = movieNameField.getText();
        String genre = movieGenreField.getText();
        String summary = movieSummaryField.getText();

        if (name.isEmpty() || genre.isEmpty() || summary.isEmpty() || posterFilePath == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all fields and choose a poster!");
            return;
        }

        boolean success = Facade.addMovie(name, genre, summary, posterFilePath);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Movie added successfully!");
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add movie!");
        }
    }


    /**
     * Clears all input fields and resets the poster image.
     */
    private void clearFields() {
        movieNameField.clear();
        movieGenreField.clear();
        movieSummaryField.clear();
        posterImageView.setImage(null);
        posterFilePath = null;
    }

    /**
     * Displays an alert dialog with the specified type, title, and message.
     *
     * @param alertType The type of alert (e.g., ERROR, WARNING, INFORMATION).
     * @param title     The title of the alert dialog.
     * @param message   The message to display in the alert dialog.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
