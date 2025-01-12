package group11.group11.Controller;


import group11.group11.Employee;
import group11.group11.Facade;
import group11.group11.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EmployeeController {
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

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        setupTable();
        loadEmployee();
        addEmployeeButton.setOnAction(event -> addEmployee());
        deleteEmployeeButton.setOnAction(event -> deleteEmployee());
        updateEmployeeButton.setOnAction(event -> updateEmployee());
        menuButton.setOnAction(event -> mainApp.showManagerPage());
        employeeTable.setOnMouseClicked(event -> selectRow());
    }

    private void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
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
                showAlert(Alert.AlertType.WARNING, "Warning", "Last name cannot be empty!");
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

            facade.addEmployee(id, firstName,lastName,password,role);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + e.getMessage());
        }
    }


    private void deleteEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        facade.deleteEmployee(selectedEmployee.getId());
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
                showAlert(Alert.AlertType.WARNING, "Warning", "Last name cannot be empty!");
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

            facade.UpdateEmployee(firstName,lastName,password,role,id);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + e.getMessage());
        }
    }


    private void selectRow() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            idField.setText(String.valueOf(selectedEmployee.getId()));
            firstNameField.setText(selectedEmployee.getFirstName());
            lastNameField.setText(selectedEmployee.getLastName());
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
