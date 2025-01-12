package group11.group11.Controller;

import group11.group11.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class CancelAndRefundController {

    private Main mainApp;
    private Users currentUser;

    @FXML
    private Button backButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button movieSearch_windowMinimize_btn;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Integer> orderNoColumn;

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order, Double> totalPriceColumn;

    @FXML
    private TableView<orderItem> orderItemsTable;

    @FXML
    private TableColumn<orderItem, Integer> orderItemIdColumn;

    @FXML
    private TableColumn<orderItem, String> itemTypeColumn;

    @FXML
    private TableColumn<orderItem, Integer> itemIdColumn;

    @FXML
    private TableColumn<orderItem, Integer> quantityColumn;

    @FXML
    private TableColumn<orderItem, Double> pricePerItemColumn;

    @FXML
    private TextField orderNoField;

    @FXML
    private Button fetchOrderItemsButton;

    @FXML
    private Button cancelAndRefundButton;

    @FXML
    private VBox orderItemsContainer;

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

    @FXML
    public void initialize() {
        orderNoColumn.setCellValueFactory(new PropertyValueFactory<>("orderNo"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderItemId"));
        itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        pricePerItemColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerItem"));

        fetchOrders();

        orderItemsContainer.setVisible(false);

        fetchOrderItemsButton.setOnAction(event -> handleFetchOrderItemsButtonAction());
        cancelAndRefundButton.setOnAction(event -> handleCancelAndRefundButtonAction());


        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
        }

        if (backButton != null) {
            backButton.setOnAction(this::handleBackButton);
        }
    }


    private void fetchOrders() {
        List<Order> orders = Facade.fetchAllOrders();
        ordersTable.setItems(FXCollections.observableArrayList(orders));
    }

    @FXML
    private void handleFetchOrderItemsButtonAction() {
        String orderNoText = orderNoField.getText();

        if (orderNoText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please enter an Order Number!");
            return;
        }

        try {
            int orderNo = Integer.parseInt(orderNoText);
            List<orderItem> orderItems = Facade.fetchOrderItemsByOrderId(orderNo);

            orderItemsTable.setItems(FXCollections.observableArrayList(orderItems));
            orderItemsContainer.setVisible(true);

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Order Number!");
        }
    }

    @FXML
    private void handleCancelAndRefundButtonAction() {
        String orderNoText = orderNoField.getText();

        if (orderNoText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please enter an Order Number!");
            return;
        }

        try {
            int orderNo = Integer.parseInt(orderNoText);
            boolean success = Facade.cancelAndRefundOrder(orderNo);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Order canceled and refund processed successfully!");
                clearFields();
                fetchOrders();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to cancel order and process refund!");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Order Number!");
        }
    }

    private void clearFields() {
        orderNoField.clear();
        orderItemsTable.getItems().clear();
        orderItemsContainer.setVisible(false);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}