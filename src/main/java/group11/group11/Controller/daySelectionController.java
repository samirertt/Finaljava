package group11.group11.Controller;

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



        dayToSessionsMap.put("17.01.2025", Arrays.asList("Morning (10:00)", "Afternoon (13:00)"));
        dayToSessionsMap.put("18.01.2025", Arrays.asList("Evening (17:00)", "Night (21:00)"));
        dayToSessionsMap.put("19.01.2025", Arrays.asList("Morning (09:00)", "Afternoon (12:00)", "Evening (16:00)"));
        dayToSessionsMap.put("20.01.2025", Arrays.asList("Morning (08:00)", "Afternoon (12:00)", "Night (20:00)"));
        dayToSessionsMap.put("21.01.2025", Arrays.asList("Morning (10:00)", "Afternoon (14:00)", "Evening (18:00)"));
        dayToSessionsMap.put("22.01.2025", Arrays.asList("Morning (09:00)", "Afternoon (13:00)", "Night (19:00)", "Late Night (23:00)"));
        dayToSessionsMap.put("23.01.2025", Arrays.asList("Morning (08:00)", "Afternoon (12:00)"));
        dayToSessionsMap.put("24.01.2025", Arrays.asList("Morning (10:00)", "Afternoon (14:00)", "Night (19:00)"));
        dayToSessionsMap.put("25.01.2025", Arrays.asList("Morning (09:00)", "Afternoon (13:00)", "Evening (17:00)"));
        dayToSessionsMap.put("26.01.2025", Arrays.asList("Afternoon (12:00)", "Evening (16:00)", "Night (20:00)"));
        dayToSessionsMap.put("27.01.2025", Arrays.asList("Morning (08:00)", "Afternoon (12:00)", "Evening (16:00)", "Night (20:00)"));
        dayToSessionsMap.put("28.01.2025", Arrays.asList("Morning (10:00)", "Afternoon (13:00)"));
        dayToSessionsMap.put("29.01.2025", Arrays.asList("Morning (09:00)", "Afternoon (12:00)", "Evening (15:00)", "Night (19:00)"));
        dayToSessionsMap.put("30.01.2025", Arrays.asList("Morning (08:00)", "Afternoon (12:00)", "Evening (16:00)"));

        sessionToHallsMap.put("Morning (10:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Afternoon (13:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Evening (17:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Night (21:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Morning (09:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Afternoon (12:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Evening (16:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Late Night (23:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Morning (08:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Afternoon (14:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Night (20:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Evening (18:00)", Arrays.asList("Hall A", "Hall B"));
        sessionToHallsMap.put("Afternoon (19:00)", Arrays.asList("Hall A", "Hall B"));




        dayComboBox.getItems().addAll(dayToSessionsMap.keySet());
        dayComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<String> sessions = dayToSessionsMap.get(newSelection);
                sessionComboBox.getItems().clear();
                sessionComboBox.getItems().addAll(sessions);
            }
        });

        sessionComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<String> halls = sessionToHallsMap.get(newSelection);
                hallComboBox.getItems().clear();
                hallComboBox.getItems().addAll(halls);
            }
        });

        moveOnToSeatSelection.setOnAction(this::btnSelectedMoveOn);

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

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
        // You can now use this.selectedMovie to access the movie details
        // For example, you can load the session times from the database based on this movie
        loadSessionTimes();
    }

    private void loadSessionTimes() {
        // Implement the logic to load session times from the database based on selectedMovie
        // For example:
        // List<Session> sessions = facade.getSessionsByMovieId(selectedMovie.getMovieId());
        // Update the UI with the session times
    }
}

