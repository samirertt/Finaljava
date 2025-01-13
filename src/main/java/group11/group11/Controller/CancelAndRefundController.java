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
    private Button confirmCancelAndRefundButton;

    @FXML
    private VBox orderItemsContainer;

    private Order selectedOrder;

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

        ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedOrder = newSelection;
                handleFetchOrderItemsButtonAction();
            }
        });

        confirmCancelAndRefundButton.setOnAction(event -> handleConfirmCancelAndRefundButtonAction());

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
        if (selectedOrder == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an order from the table!");
            return;
        }

        int orderNo = selectedOrder.getOrderNo();
        List<orderItem> orderItems = Facade.fetchOrderItemsByOrderId(orderNo);

        orderItemsTable.setItems(FXCollections.observableArrayList(orderItems));
        orderItemsContainer.setVisible(true);
    }

    @FXML
    private void handleConfirmCancelAndRefundButtonAction() {
        if (selectedOrder == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an order from the table!");
            return;
        }

        int orderNo = selectedOrder.getOrderNo();

        boolean success = Facade.cancelAndRefundOrder(orderNo);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Order canceled and refund processed successfully!");
            clearFields();
            fetchOrders();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to cancel order and process refund!");
        }
    }

    private void clearFields() {
        selectedOrder = null;
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