package group11.group11.Controller;

import group11.group11.Main;
import group11.group11.Movie;
import group11.group11.Users;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import group11.group11.Facade;
import javafx.event.ActionEvent;
import java.sql.Time;
import java.util.*;
import java.sql.Date;


public class daySelectionController
{

    private Movie selectedMovie;
    private Users currentUser;
    private Time sessionTime;

    public void setSessionTime(Time sessionTime)
    {
        this.sessionTime = sessionTime;
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
    private Button backButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button movieSearch_windowMinimize_btn;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void movieSearch_windowClose_btn() {
        System.exit(0);
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
            mainApp.showMoviePage(currentUser);
        }
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

        if (backButton != null) {
            backButton.setOnAction(this::handleBackButton);
        }

        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
        }
    }


    @FXML
    public void btnSelectedMoveOn()
    {

        if(sessionTime == null)
        {
            showAlert("PLease select a session!");
        }
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

