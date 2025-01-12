package group11.group11.Controller;

import group11.group11.*;
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

import java.sql.Time;
import java.util.List;

public class CartPageController {

    private Main mainApp;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    private Label cart_orderNo;

    @FXML
    private TableView<Product> cart_orderTable;

    @FXML
    private TableColumn<Product, String> cart_productName;

    @FXML
    private TableColumn<Product, Double> cart_productPrice;

    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Product, Integer> cart_productQuantity;

    @FXML
    private Cart cart;

    @FXML
    private ObservableList<Product> cartData;

    @FXML
    private Button movieSearch_windowMinimize_btn;

    private int session_id;
    private Movie selectedMovie;
    private Users currentUser;
    private Time sessionTime;
    private String previousPage;

    private Facade facade = new Facade();

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        System.out.println("mainApp set in CartPageController: " + (mainApp != null));
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

    public void setSessionId(int session_id) {
        this.session_id = session_id;
        System.out.println("Session ID set to: " + session_id); // Debugging
    }

    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

    public void movieSearch_windowClose_btn() {
        System.exit(0);
    }

    public void MovieSearch_windowMinimize() {
        Stage stage = (Stage) movieSearch_windowMinimize_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void initialize()
    {
        Facade.fixCart();
        // Set up the TableView columns
        cart_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cart_productPrice.setCellValueFactory(new PropertyValueFactory<>("taxedPrice"));
        cart_productQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
        }

        if (backButton != null) {
            backButton.setOnAction(this::handleBackButton);
        }

        // Initialize data if mainApp is already set
        if (mainApp != null) {
            initializeData();
        }
    }

    @FXML
    public void handleLogoutButton(ActionEvent event) {
        if (mainApp != null) {
            mainApp.logout();
        }
    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        if (mainApp != null && previousPage != null) {
            switch (previousPage) {
                case "moviePage":
                    mainApp.showMoviePage(currentUser);
                    break;
                case "B":
                    mainApp.openHallBPage(session_id, selectedMovie, currentUser);
                    break;
                case "A":
                    mainApp.openHallAPage(session_id, selectedMovie, currentUser);
                    break;
                case "customerProducts":
                    mainApp.ProductPurchase(session_id, selectedMovie, currentUser, previousPage);
                    break;
                case "daySelection":
                    mainApp.showDaySelectionPage(currentUser, selectedMovie);
                    break;
                case "payment":
                    mainApp.btnPayScreen(session_id, currentUser, selectedMovie);
                    break;
                default:
                    System.out.println("No specific page to go back to!"); // Debug
                    break;
            }
        } else {
            System.out.println("mainApp or previousPage is null!"); // Debug
        }
    }

    public void initializeData() {
        if (mainApp != null) {
            String orderNo = mainApp.getOrderNo();
            if (orderNo != null && !orderNo.isEmpty()) {
                cart_orderNo.setText(orderNo);

                // Fetch products from the database
                List<Product> products = facade.getItemsFromDb(orderNo);

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
}