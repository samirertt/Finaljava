package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Objects;

public class LoginPageController {

    private Main mainApp;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    private final Facade facade = new Facade();

    @FXML
    public void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Users user= facade.validateLogin(username, password);

        if (user != null) {
            if (Objects.equals(user.getrole(), "cashier")){
                // Proceed with successful login actions
                errorLabel.setText("Login successful!");
                mainApp.showMoviePage(user);
                errorLabel.setDisable(false);
            }

            else if (Objects.equals(user.getrole(), "manager")){
                // Proceed with successful login actions
                errorLabel.setText("Login successful!");
                mainApp.showManagerPage();
                errorLabel.setDisable(false);
            }
            else {
                // Proceed with successful login actions
                errorLabel.setText("Login successful!");
                mainApp.showAdminPage();
                errorLabel.setDisable(false);
            }
        } else {
            // Display error message for invalid credentials
            errorLabel.setText("Invalid username or password. Please try again.");
            errorLabel.setDisable(false);
        }
    }
}