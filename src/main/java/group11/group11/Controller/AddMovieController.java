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
    private Button menuButton;

    @FXML
    private Button backButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button movieSearch_windowMinimize_btn;

    @FXML
    private ImageView posterImageView;

    private String posterFilePath;

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
        choosePosterButton.setOnAction(event -> handleChoosePosterButtonAction());
        addMovieButton.setOnAction(event -> handleAddMovieButtonAction());
        menuButton.setOnAction(event -> handleMenuButtonAction());
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

    // /home/sam/IdeaProjects/Group11/src/main/resources/group11/group11/images/Intersteller.jpg
    // /group11/group11/images/Intersteller.jpg
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

    @FXML
    private void handleMenuButtonAction() {
        // Navigate back to the main page
        MainpageController.navigateToMainPage(menuButton);
    }

    private void clearFields() {
        movieNameField.clear();
        movieGenreField.clear();
        movieSummaryField.clear();
        posterImageView.setImage(null);
        posterFilePath = null;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}