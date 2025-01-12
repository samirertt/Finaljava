package group11.group11.Controller;

import group11.group11.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class DiscountController {
    Facade facade = new Facade();

    @FXML private TextField minAgeField;
    @FXML private TextField maxAgeField;
    @FXML private TextField discountRateField;

    @FXML private TableView<Discount> discountTable;
    @FXML private TableColumn<Discount, Integer> minAgeColumn;
    @FXML private TableColumn<Discount, Integer> maxAgeColumn;
    @FXML private TableColumn<Discount, Double> discountRateColumn;

    @FXML private Button addDiscountButton;
    @FXML private Button deleteDiscountButton;
    @FXML private Button updateDiscountButton;
    @FXML private Button menuButton;

    private Main mainApp;
    private Users currentUser;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML private Button movieSearch_windowMinimize_btn;

    public void setProfileDetails() {
        if (currentUser != null) {
            movieSearch_profileName.setText(currentUser.getUsername());
            movieSearch_profileRole.setText(currentUser.getrole());
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

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }


    @FXML
    public void initialize() {
        setupTable();
        loadDiscounts();

        addDiscountButton.setOnAction(event -> addDiscount());
        deleteDiscountButton.setOnAction(event -> deleteDiscount());
        updateDiscountButton.setOnAction(event -> updateDiscount());
        menuButton.setOnAction(event -> mainApp.showManagerPage(currentUser));
        discountTable.setOnMouseClicked(event -> selectRow());
    }

    private void setupTable() {
        minAgeColumn.setCellValueFactory(new PropertyValueFactory<>("minAge"));
        maxAgeColumn.setCellValueFactory(new PropertyValueFactory<>("maxAge"));
        discountRateColumn.setCellValueFactory(new PropertyValueFactory<>("discountRate"));
    }
    private void openPage(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx1/"+pageName+".fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(pageName+" Page");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) menuButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(pageName +" sayfası yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void loadDiscounts() {
        discountTable.getItems().clear();
        ObservableList<Discount> DiscountsList = FXCollections.observableArrayList();
        discountTable.setItems(DiscountsList);
        DiscountsList.addAll(facade.load_Discounts());

    }

    private void addDiscount() {
        try {

            int minAge;
            try {
                minAge = Integer.parseInt(minAgeField.getText());
                if (minAge < 0) {
                    showAlert(Alert.AlertType.WARNING, "Warning", "Minimum age cannot be negative!");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a valid minimum age!");
                return;
            }

            int maxAge;
            try {
                maxAge = Integer.parseInt(maxAgeField.getText());
                if (maxAge < 0) {
                    showAlert(Alert.AlertType.WARNING, "Warning", "Maximum age cannot be negative!");
                    return;
                }
                if (maxAge < minAge) {
                    showAlert(Alert.AlertType.WARNING, "Warning", "Maximum age cannot be less than minimum age!");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a valid maximum age!");
                return;
            }

            double discountRate;
            try {
                discountRate = Double.parseDouble(discountRateField.getText());
                if (discountRate < 0 || discountRate > 100) {
                    showAlert(Alert.AlertType.WARNING, "Warning", "Discount rate must be between 0 and 100!");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a valid discount rate!");
                return;
            }

            if (facade.isAgeRangeOverlap(minAge, maxAge)) {
                showAlert(Alert.AlertType.WARNING, "Warning", "This age range overlaps with an existing discount!");
                return;
            }

            try {
                facade.addDiscounts(minAge,maxAge,discountRate);
                loadDiscounts();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Discount added successfully!");
                clearFields();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Error adding discount: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + e.getMessage());
        }
    }

    private void deleteDiscount() {
        Discount selectedDiscount = discountTable.getSelectionModel().getSelectedItem();

        if (selectedDiscount != null)
        {
            try{
                facade.delete_Discount(selectedDiscount);
                loadDiscounts();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Discount deleted successfully!");
            }
            catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting discount: " + e.getMessage());
            }

        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a discount to delete!");
        }
    }

    private void updateDiscount() {
        try {
            Discount selectedDiscount = discountTable.getSelectionModel().getSelectedItem();
            if (selectedDiscount == null) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select a discount to update!");
                return;
            }


            int minAge;
            try {
                minAge = Integer.parseInt(minAgeField.getText());
                if (minAge < 0) {
                    showAlert(Alert.AlertType.WARNING, "Warning", "Minimum age cannot be negative!");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a valid minimum age!");
                return;
            }

            int maxAge;
            try {
                maxAge = Integer.parseInt(maxAgeField.getText());
                if (maxAge < 0) {
                    showAlert(Alert.AlertType.WARNING, "Warning", "Maximum age cannot be negative!");
                    return;
                }
                if (maxAge < minAge) {
                    showAlert(Alert.AlertType.WARNING, "Warning", "Maximum age cannot be less than minimum age!");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a valid maximum age!");
                return;
            }


            double discountRate;
            try {
                discountRate = Double.parseDouble(discountRateField.getText());
                if (discountRate < 0 || discountRate > 100) {
                    showAlert(Alert.AlertType.WARNING, "Warning", "Discount rate must be between 0 and 100!");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a valid discount rate!");
                return;
            }


            if (isAgeRangeOverlapForUpdate(minAge, maxAge, selectedDiscount)) {
                showAlert(Alert.AlertType.WARNING, "Warning", "This age range overlaps with an existing discount!");
                return;
            }

            try {
                if (facade.update_Discount(selectedDiscount,minAge,maxAge,discountRate)> 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Discount updated successfully!");
                    loadDiscounts();
                    clearFields();
                } else {
                    showAlert(Alert.AlertType.WARNING, "Warning", "No matching discount found to update!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Error updating discount: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + e.getMessage());
        }
    }

    private boolean isAgeRangeOverlapForUpdate(int minAge, int maxAge, Discount selectedDiscount) {

        try {
            facade.isAgeRangeOverlapForUpdate(minAge,maxAge,selectedDiscount);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error checking overlapping age ranges: " + e.getMessage());
        }
        return false;
    }


    private void selectRow() {
        Discount selectedDiscount = discountTable.getSelectionModel().getSelectedItem();
        if (selectedDiscount != null) {
            minAgeField.setText(String.valueOf(selectedDiscount.getMinAge()));
            maxAgeField.setText(String.valueOf(selectedDiscount.getMaxAge()));
            discountRateField.setText(String.valueOf(selectedDiscount.getDiscountRate()));
        }
    }

    private void clearFields() {
        minAgeField.clear();
        maxAgeField.clear();
        discountRateField.clear();
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}