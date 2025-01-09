package group11.group11.Controller;

import group11.group11.Cart;
import group11.group11.Main;
import group11.group11.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class CartPageController {

    private Main mainApp;

    @FXML
    private Button cart_Decrease;

    @FXML
    private Button cart_Increase;

    @FXML
    private Button cart_Remove;

    @FXML
    private Label cart_orderNo;

    @FXML
    private TableView<Cart.Product> cart_orderTable;

    @FXML
    private TableColumn<Cart.Product, String> cart_productName;

    @FXML
    private TableColumn<Cart.Product, Double> cart_productPrice;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<Cart.Product, Integer> cart_productQuantity;
    @FXML
    private Cart cart;
    @FXML
    private ObservableList<Cart.Product> cartData;
    @FXML
    private Button movieSearch_windowMinimize_btn;

    // Method to set the main application instance
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        System.out.println("mainApp set in CartPageController: " + (mainApp != null));
    }
    public void movieSearch_windowClose_btn() {
        System.exit(0);
    }

    public void MovieSearch_windowMinimize() {
        Stage stage = (Stage) movieSearch_windowMinimize_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    Facade facade = new Facade();
    // Initialize data that depends on mainApp
    public void initializeData() {
        if (mainApp != null) {
            String orderNo = mainApp.getOrderNo();
            if (orderNo != null && !orderNo.isEmpty()) {
                cart_orderNo.setText(orderNo);

                // Fetch products from the database
                List<Cart.Product> products = facade.getProductsFromDb(orderNo);

                // Convert the list to an ObservableList
                cartData = FXCollections.observableArrayList(products);

                // Set the ObservableList as the data source for the TableView
                cart_orderTable.setItems(cartData);
            } else {
                cart_orderNo.setText("No Order Found");
                cartData = FXCollections.observableArrayList(); // Empty list
                cart_orderTable.setItems(cartData);
            }
        } else {
            System.err.println("Error: mainApp is null in CartPageController.");
        }
    }

    @FXML
    public void handleLogoutButton(ActionEvent event) {
        if (mainApp != null) {
            mainApp.logout();
        }
    }

    @FXML
    public void initialize() {
        // Set up the TableView columns
        cart_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cart_productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        cart_productQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Add event handlers for button actions
        addEventHandlers();

        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
        }
    }

    // Adding event handlers for cart actions
    private void addEventHandlers() {
        cart_Increase.setOnAction(event -> increaseQuantity());
        cart_Decrease.setOnAction(event -> decreaseQuantity());
        cart_Remove.setOnAction(event -> removeSelectedProduct());
    }

    // Increase the quantity of the selected product
    private void increaseQuantity() {
        Cart.Product selectedProduct = cart_orderTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            selectedProduct.setQuantity(selectedProduct.getQuantity() + 1);

        }
        facade.updateProductQuantity(mainApp.getOrderNo(), selectedProduct.getName(), selectedProduct.getQuantity());
        refreshTable();
    }

    // Decrease the quantity of the selected product
    private void decreaseQuantity() {
        Cart.Product selectedProduct = cart_orderTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null && selectedProduct.getQuantity() > 1) {
            selectedProduct.setQuantity(selectedProduct.getQuantity() - 1);
            refreshTable();
        }
    }

    // Remove the selected product from the cart
    private void removeSelectedProduct() {
        Cart.Product selectedProduct = cart_orderTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            cart.removeProduct(selectedProduct.getName());
            cartData.remove(selectedProduct);
            refreshTable();
        }
    }

    // Refresh the TableView after changes
    private void refreshTable() {
        cart_orderTable.refresh();
    }
}