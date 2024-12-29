package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login_page {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private final Facade facade = new Facade();

    @FXML
    public void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Users users= facade.validateLogin(username, password);

        if (users != null) {
            // Proceed with successful login actions
            errorLabel.setText("Login successful!");
            errorLabel.setDisable(false);
        } else {
            // Display error message for invalid credentials
            errorLabel.setText("Invalid username or password. Please try again.");
            errorLabel.setDisable(false);
        }
    }
}