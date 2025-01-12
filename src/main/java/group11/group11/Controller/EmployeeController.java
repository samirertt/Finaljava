package group11.group11.Controller;
import group11.group11.Employee;
import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EmployeeController
{
    Facade facade = new Facade();
    @FXML private TextField idField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField passwordField;
    @FXML private TextField roleField;
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, Integer> idColumn;
    @FXML private TableColumn<Employee, String> firstNameColumn;
    @FXML private TableColumn<Employee, String> lastNameColumn;
    @FXML private TableColumn<Employee, String> passwordColumn;
    @FXML private TableColumn<Employee, String> roleColumn;

    @FXML private Button addEmployeeButton;
    @FXML private Button deleteEmployeeButton;
    @FXML private Button updateEmployeeButton;
    @FXML private Button menuButton;

    private Main mainApp;

    private Employee user;

    private Users currentUser;


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }


    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

/*
    public void setProfileDetails() {
        if (currentUser != null) {
            movieSearch_profileName.setText(currentUser.getUsername());
            movieSearch_profileRole.setText(currentUser.getrole());
        }
    }
*/
    @FXML
    public void initialize() {
        setupTable();
        loadEmployee();
        addEmployeeButton.setOnAction(event -> addEmployee());
        deleteEmployeeButton.setOnAction(event -> deleteEmployee());
        updateEmployeeButton.setOnAction(event -> updateEmployee());
        menuButton.setOnAction(event -> mainApp.showManagerPage(currentUser));
        employeeTable.setOnMouseClicked(event -> selectRow());
    }

    private void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    private void loadEmployee()
    {
        employeeTable.getItems().clear();
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();

        employeeTable.setItems(employeeList);
        employeeList.addAll(facade.loadEmployees());

    }
    private void addEmployee() {


        try {
            // Validate First Name

            String firstName = firstNameField.getText();
            if (firstName == null || firstName.trim().isEmpty())
            {
                showAlert(Alert.AlertType.WARNING, "Warning", "Name cannot be empty!");
                return;
            }
            if (!firstName.matches("[a-zA-Z]+")) {
                showAlert(Alert.AlertType.WARNING, "Warning", "First name must contain only letters!");
                return;
            }


            String username = lastNameField.getText();
            if (username == null || username.trim().isEmpty())
            {
                showAlert(Alert.AlertType.WARNING, "Warning", "Username cannot be empty!");
                return;
            }
            else if(facade.usernameCheck(username))
            {
                showAlert(Alert.AlertType.WARNING, "Warning", "Username already exists!");
                return;
            }


            // Validate Last Name
            String lastName = lastNameField.getText();
            if (lastName == null || lastName.trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Last name cannot be empty!");
                return;
            }
            if (!lastName.matches("[a-zA-Z]+")) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Last name must contain only letters!");
                return;
            }

            // Validate Password

            String password = passwordField.getText();
            if (password == null || password.trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Password cannot be empty!");
                return;
            }
            if (password.length() < 8) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Password must be at least 8 characters long!");
                return;
            }
            if (!password.matches(".*[A-Z].*")) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Password must contain at least one uppercase letter!");
                return;
            }
            if (!password.matches(".*[a-z].*")) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Password must contain at least one lowercase letter!");
                return;
            }
            if (!password.matches(".*\\d.*")) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Password must contain at least one digit!");
                return;
            }
            if (!password.matches(".*[@#$%^&+=].*")) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Password must contain at least one special character (@#$%^&+=)!");
                return;
            }

            // Validate Role
            String role = roleField.getText();
            if (role == null || role.trim().isEmpty())
            {
                showAlert(Alert.AlertType.WARNING, "Warning", "Role cannot be empty!");
                return;
            }

            else if (!role.equals("manager") && !role.equals("admin") && !role.equals("cashier")) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Enter a valid role!");
                return;
            }

            facade.addEmployee(firstName,username,password,role);

            loadEmployee();
            clearFields();

            if (!role.matches("admin|employee|manager")) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Role must be one of: admin, employee, manager!");
                return;
            }

            // If all validations pass, add the employee
            facade.addEmployee(firstName, lastName, password, role);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Employee added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        facade.deleteEmployee(selectedEmployee.getId());
        loadEmployee();
        clearFields();
    }

    private void updateEmployee() {
        try {

            Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
            if (selectedEmployee == null) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select an employee to update!");
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idField.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a valid employee ID!");
                return;
            }


            String firstName = firstNameField.getText();
            if (firstName == null || firstName.trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning", "First name cannot be empty!");
                return;
            }


            String lastName = lastNameField.getText();
            if (lastName == null || lastName.trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Username cannot be empty!");
                return;
            }


            String password = passwordField.getText();
            if (password == null || password.trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Password cannot be empty!");
                return;
            }


            String role = roleField.getText();
            if (role == null || role.trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Role cannot be empty!");
                return;
            }
            else if (!role.equals("manager") && !role.equals("admin") && !role.equals("cashier")) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Enter a valid role!");
                return;
            }

            facade.UpdateEmployee(firstName,lastName,password,role,id);
            loadEmployee();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + e.getMessage());
        }
    }


    private void selectRow() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            idField.setText(String.valueOf(selectedEmployee.getId()));
            firstNameField.setText(selectedEmployee.getFullname());
            lastNameField.setText(selectedEmployee.getUsername());
            passwordField.setText(selectedEmployee.getPassword());
            roleField.setText(selectedEmployee.getRole());
        }
    }
    private void clearFields() {
        idField.clear();
        firstNameField.clear();
        lastNameField.clear();
        passwordField.clear();
        roleField.clear();
    }

    private void openPage(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx1/" + pageName + ".fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(pageName + " Page");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) menuButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(pageName + " sayfası yüklenirken hata oluştu: " + e.getMessage());
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
