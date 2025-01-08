package group11.group11.Controller;

import group11.group11.Main;
import group11.group11.Movie;
import group11.group11.Users;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import group11.group11.Facade;

import java.sql.Time;
import java.util.*;
import java.sql.Date;


public class daySelectionController extends Application
{

    private Movie selectedMovie;
    private Users currentUser;
    private Time sessionTime;

    public void setSessionTime(Time sessionTime)
    {
        this.sessionTime = sessionTime;
    }
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

    private Main mainApp;

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
                mainApp.setSelectedDate(selectedDay);
            }
        });

        sessionComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Time selectedTime = Time.valueOf(newSelection); // Convert String to SQL Time
                setSessionTime(selectedTime);
                List<String> halls = Facade.getSessionHalls(selectedTime);
                hallComboBox.getItems().clear();
                hallComboBox.getItems().addAll(halls);
                mainApp.setSelectedTime(selectedTime);
            }
        });
    }



    @FXML
    public void btnSelectedMoveOn() {
        String selectedHall = hallComboBox.getValue();
        mainApp.setSelectedHall(selectedHall);
        int session_id = Facade.getSessionId(sessionTime,selectedMovie.getMovie_id());
        if (selectedHall != null) {
            switch (selectedHall) {
                case "A":
                    mainApp.openHallAPage(session_id, selectedMovie);
                    break;
                case "B":
                    mainApp.openHallBPage(session_id, selectedMovie);
                    break;
                default:
                    showAlert("Invalid selection!");
                    break;
            }
        } else {
            showAlert("Please select a hall.");
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
        // You can now use this.selectedMovie to access the movie details
        // For example, you can load the session times from the database based on this movie
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
}

