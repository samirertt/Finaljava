package group11.group11.Controller;
import group11.group11.Cart;

import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class payment {

    @FXML
    private Label order_ID;

    @FXML
    private Button payButton;

    @FXML
    private TableView<Cart.Product> payment_TableView;

    @FXML
    private Label payment_movieDay;

    @FXML
    private Label payment_movieHall;

    @FXML
    private Label payment_movieName;

    @FXML
    private Label payment_movieTime;

    @FXML
    private TableColumn<Cart.Product, Integer> payment_tableView_Quantity;

    @FXML
    private TableColumn<Cart.Product, String> payment_tableView_itemName;

    @FXML
    private TableColumn<Cart.Product, Double> payment_tableView_price;

    private Main mainApp;
    private Facade facade;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        this.facade = new Facade();
        initializeData(); // Load data after mainApp is set
    }

    @FXML
    private void initialize() {
        // Initialize TableView columns
        payment_tableView_itemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        payment_tableView_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        payment_tableView_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void initializeData() {
        if (mainApp != null) {
            try {
                // Set order ID
                order_ID.setText("Order ID: " + mainApp.getOrderNo());

                // Load cart items into the TableView
                List<Cart.Product> products = facade.getProductsFromDb(mainApp.getOrderNo());
                if (products != null && !products.isEmpty()) {
                    payment_TableView.getItems().addAll(products);
                } else {
                    System.out.println("No products found for the order.");
                }

                // Set movie details
                Movie selectedMovie = mainApp.getSelectedMovie();
                Date selectedDate = mainApp.getSelectedDate();
                Time selectedTime = mainApp.getSelectedTime();
                String selectedHall = mainApp.getSelectedHall();

                if (selectedMovie != null) {
                    payment_movieName.setText("Movie: " + selectedMovie.getMovieName());
                } else {
                    payment_movieName.setText("Movie: Not Available");
                }

                if (selectedDate != null) {
                    payment_movieDay.setText("Day: " + selectedDate); // Format date if needed
                } else {
                    payment_movieDay.setText("Day: Not Available");
                }

                if (selectedTime != null) {
                    payment_movieTime.setText("Time: " + selectedTime); // Format time if needed
                } else {
                    payment_movieTime.setText("Time: Not Available");
                }

                if (selectedHall != null) {
                    payment_movieHall.setText("Hall: " + selectedHall);
                } else {
                    payment_movieHall.setText("Hall: Not Available");
                }
            } catch (Exception e) {
                System.err.println("Error initializing data: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("MainApp is null. Cannot initialize data.");
        }
    }
    @FXML
    private void handlePayButton() {
        // Handle payment logic here
        System.out.println("Payment processed for order: " + mainApp.getOrderNo());

    }
}