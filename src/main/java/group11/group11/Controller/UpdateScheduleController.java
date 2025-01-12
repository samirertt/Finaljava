package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Schedule;
import group11.group11.Users;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UpdateScheduleController {

    private Main mainApp;
    private Users currentUser;

    @FXML
    private TableView<Schedule> scheduleTable;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    private TableColumn<Schedule, Integer> sessionIdColumn;

    @FXML
    private TableColumn<Schedule, Integer> movieIdColumn;

    @FXML
    private TableColumn<Schedule, String> hallColumn;

    @FXML
    private TableColumn<Schedule, String> dayColumn;

    @FXML
    private TableColumn<Schedule, String> timeColumn;

    @FXML
    private TextField sessionIdField;

    @FXML
    private TextField movieIdField;

    @FXML
    private TextField hallField;

    @FXML
    private DatePicker dayPicker;

    @FXML
    private TextField timeField;

    @FXML
    private Button fetchScheduleButton;

    @FXML
    private Button updateScheduleButton;

    @FXML
    private VBox updateFieldsContainer;

    @FXML
    private Button menuButton;

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
    public void initialize() {
        sessionIdColumn.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        movieIdColumn.setCellValueFactory(new PropertyValueFactory<>("movieId"));
        hallColumn.setCellValueFactory(new PropertyValueFactory<>("hall"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        fetchSchedules();

        updateFieldsContainer.setVisible(false);
        sessionIdField.setEditable(false);

        fetchScheduleButton.setOnAction(event -> handleFetchScheduleButtonAction());
        updateScheduleButton.setOnAction(event -> handleUpdateScheduleButtonAction());
        menuButton.setOnAction(event -> handleMenuButtonAction());
    }

    @FXML
    private void handleMenuButtonAction() {
        MainpageController.navigateToMainPage(menuButton);
    }

    private void fetchSchedules() {
        List<Schedule> schedules = Facade.fetchAllSchedules();
        scheduleTable.setItems(FXCollections.observableArrayList(schedules));
    }

    @FXML
    private void handleFetchScheduleButtonAction() {
        String sessionIdText = sessionIdField.getText();

        if (sessionIdText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a Session ID!");
            return;
        }

        try {
            int sessionId = Integer.parseInt(sessionIdText);
            Schedule schedule = Facade.fetchScheduleById(sessionId);

            if (schedule != null) {
                sessionIdField.setText(String.valueOf(schedule.getSessionId()));
                movieIdField.setText(String.valueOf(schedule.getMovieId()));
                hallField.setText(schedule.getHall());
                dayPicker.setValue(LocalDate.parse(schedule.getDay()));
                timeField.setText(schedule.getTime());

                updateFieldsContainer.setVisible(true);
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "No schedule found with the given ID!");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Session ID!");
        }
    }


    @FXML
    private void handleUpdateScheduleButtonAction() {
        String sessionIdText = sessionIdField.getText();
        String movieIdText = movieIdField.getText();
        String hall = hallField.getText();
        LocalDate day = dayPicker.getValue();
        String time = timeField.getText();

        if (sessionIdText.isEmpty() || movieIdText.isEmpty() || hall.isEmpty() || day == null || time.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all fields!");
            return;
        }

        try {
            int sessionId = Integer.parseInt(sessionIdText);
            int movieId = Integer.parseInt(movieIdText);
            String formattedDay = day.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            boolean success = Facade.updateSchedule(sessionId, movieId, hall, formattedDay, time);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Schedule updated successfully!");
                clearFields();
                fetchSchedules();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update schedule!");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid input for Session ID or Movie ID!");
        }
    }

    private void clearFields() {
        sessionIdField.clear();
        movieIdField.clear();
        hallField.clear();
        dayPicker.setValue(null);
        timeField.clear();
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

