package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

    public class AddScheduleController {

        private Main mainApp;
        private Users currentUser;

        @FXML
        private Label movieSearch_profileName;

        @FXML
        private Label movieSearch_profileRole;

        @FXML
        private TextField movieIdField;

        @FXML
        private TextField hallField;

        @FXML
        private DatePicker dayPicker;

        @FXML
        private TextField timeField;

        @FXML
        private Button backButton;

        @FXML
        private Button logoutButton;

        @FXML
        private Button movieSearch_windowMinimize_btn;

        @FXML
        private Button addScheduleButton;


        @FXML
        public void initialize() {
            addScheduleButton.setOnAction(event -> handleAddScheduleButtonAction());

            if (logoutButton != null) {
                logoutButton.setOnAction(this::handleLogoutButton);
            }

            if (backButton != null) {
                backButton.setOnAction(this::handleBackButton);
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
        private void handleAddScheduleButtonAction() {
            String movieIdText = movieIdField.getText();
            String hall = hallField.getText();
            LocalDate day = dayPicker.getValue();
            String time = timeField.getText();

            if (movieIdText.isEmpty() || hall.isEmpty() || day == null || time.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all fields!");
                return;
            }

            if (!hall.equalsIgnoreCase("A") && !hall.equalsIgnoreCase("B")) {
                showAlert(Alert.AlertType.ERROR, "Error", "Hall must be either 'A' or 'B'!");
                return;
            }

            try {
                int movieId = Integer.parseInt(movieIdText);
                String formattedDay = day.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                boolean success = Facade.addSchedule(movieId, hall.toUpperCase(), formattedDay, time);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Schedule added successfully!");
                    clearFields();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to add schedule!");
                }

            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid movie ID!");
            }
        }



        private void clearFields() {
            movieIdField.clear();
            hallField.clear();
            dayPicker.setValue(null);
            timeField.clear();
        }

        private void showAlert(Alert.AlertType alertType, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

