package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Movie;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class daySelectionController extends Application
{
    private Movie selectedMovie;

    @Override
        public void start(Stage primaryStage) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/group11/group11/fxml/daySessionHallSelection.fxml"));
                Parent root = loader.load();

                primaryStage.setTitle("Login Page");
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);

                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error: Unable to load the FXML file. Ensure the path and controller are correct.");
            }
        }

    // private Users currentUser;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    public AnchorPane field;



    @FXML
    public Label firstLabel;

    @FXML
    public Button moveOnToSeatSelection;

    @FXML
    public AnchorPane dayAnchor;

    @FXML
    public AnchorPane sessionAnchor;

    @FXML
    public AnchorPane hallAnchor;

    @FXML
    public ComboBox<String> dayComboBox;

    @FXML
    public ComboBox<String> sessionComboBox;

    @FXML
    public ComboBox<String> hallComboBox;

    private final Map<String, List<String>> dayToSessionsMap = new HashMap<>();
    private final Map<String, List<String>> sessionToHallsMap = new HashMap<>();

    /*public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

    public void setProfileDetails() {
        if (currentUser != null) {
            movieSearch_profileName.setText(currentUser.getUsername());
            movieSearch_profileRole.setText(currentUser.getrole());
        }
    }
*/

    @FXML
    public void initialize() {

        field.setVisible(true);
        field.setManaged(true);

        String stylesheetPath = getClass().getResource("/resources/style.css") != null
                ? getClass().getResource("/resources/style.css").toExternalForm()
                : null;

        if (stylesheetPath != null) {
            field.getStylesheets().add(stylesheetPath);
        } else {
            System.err.println("CSS file not found!");
        }

        dayComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Date selectedDay = Date.valueOf(newSelection); // Convert String to SQL Date
                List<Time> sessionTimes = Facade.getSessionTimes(selectedDay);
                sessionComboBox.getItems().clear();
                for (Time time : sessionTimes) {
                    sessionComboBox.getItems().add(time.toString());
                }
            }
        });

        sessionComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Time selectedTime = Time.valueOf(newSelection); // Convert String to SQL Time
                List<String> halls = Facade.getSessionHalls(selectedTime);
                hallComboBox.getItems().clear();
                hallComboBox.getItems().addAll(halls);
            }
        });

        moveOnToSeatSelection.setOnAction(this::btnSelectedMoveOn);
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
        loadSessionTimes();
    }

    private void loadSessionTimes() {
        if (selectedMovie != null) {
            List<Date> sessionDays = Facade.getSessionDays(Facade.getMovieIdByName(selectedMovie.getMovieName()));
            for (Date day : sessionDays) {
                dayComboBox.getItems().add(day.toString());
            }
        }
    }



    @FXML
    public void btnSelectedMoveOn(ActionEvent event) {
        String selectedHall = hallComboBox.getValue();
        if (selectedHall != null) {
            switch (selectedHall) {
                case "Hall A":
                    openHallAPage();
                    break;
                case "Hall B":
                    openHallBPage();
                    break;
                default:
                    showAlert("Invalid selection!");
                    break;
            }
        } else {
            showAlert("Please select a hall.");
        }
    }

    private void openHallBPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/group11/group11/fxml/seatSelection.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) moveOnToSeatSelection.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error occurred while loading Hall B page");
            e.printStackTrace();
        }
    }

    private void openHallAPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/group11/group11/fxml/seatSelectionA.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) moveOnToSeatSelection.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error occurred while loading Hall A page");
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


