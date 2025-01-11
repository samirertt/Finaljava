package group11.group11.Controller;
import group11.group11.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    private Button searchMovie_cart;

    @FXML
    private Button backButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button movieSearch_windowMinimize_btn;

    @FXML
    private TableColumn<Cart.Product, Integer> payment_tableView_Quantity;

    @FXML
    private TableColumn<Cart.Product, String> payment_tableView_itemName;

    @FXML
    private TableColumn<Cart.Product, Double> payment_tableView_price;

    private Main mainApp;
    private Facade facade;
    private Movie selectedMovie;
    private Users currentUser;
    private Time sessionTime;
    private String previousPage;
    private Cart cart;
    private int session_id;

    public void setSelectedMovie(Movie selectedMovie)
    {
        this.selectedMovie = selectedMovie;
    }

    public void setSessionId(int session_id) {
        this.session_id = session_id;
        System.out.println("Session ID set to: " + session_id); // Debugging
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
            mainApp.ProductPurchase(session_id, selectedMovie, currentUser, previousPage);
        }
    }

    @FXML
    private void handleOpenCartPage() {
        System.out.println("Cart button clicked! movie");
        if (mainApp != null) {
            System.out.println("is not null");
            mainApp.showCartPage();
        }
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

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        this.facade = new Facade();
        initializeData(); // Load data after mainApp is set
    }

    @FXML
    private void initialize() {
        // Initialize TableView columns
        payment_tableView_itemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        payment_tableView_Quantity.setCellValueFactory(new PropertyValueFactory<>("stock"));
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

                if (backButton != null) {
                    backButton.setOnAction(this::handleBackButton);
                }

                if (logoutButton != null) {
                    logoutButton.setOnAction(this::handleLogoutButton);
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