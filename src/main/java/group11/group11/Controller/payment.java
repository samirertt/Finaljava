package group11.group11.Controller;
import group11.group11.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.Time;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class payment {

    @FXML
    private Label order_ID;

    @FXML
    private Button payButton;

    @FXML
    private TableView<Ticket> payment_TableView;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private DatePicker birthdatePicker;

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
    private Button EnterButton;

    @FXML
    private TableView<Product> product_TableView;

    @FXML
    private TableColumn<Ticket, String> payment_tableView_Quantity;

    @FXML
    private TableColumn<Ticket, String> payment_tableView_itemName;

    @FXML
    private TableColumn<Ticket, Double> payment_tableView_price;

    @FXML
    private TableColumn<Product, String> cartProduct;

    @FXML
    private TableColumn<Product, Integer> cartQuantity;

    @FXML
    private TableColumn<Product, Double> cartPrice;

    @FXML
    private Label totalLabel;

    private List<Ticket> tickets;

    private Main mainApp;
    private Facade facade;
    private Movie selectedMovie;
    private Users currentUser;
    private Time sessionTime;
    private String previousPage;
    private Cart cart;
    private int session_id;
    private Ticket selectedTicket;

    public void setSelectedMovie(Movie selectedMovie)
    {
        this.selectedMovie = selectedMovie;
    }

    public void setSession_Id(int session_id) {
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
            mainApp.ProductPurchase(session_id, selectedMovie, currentUser,"seatSelection");
        }
    }

    @FXML
    private void handleOpenCartPage() {
        System.out.println("Cart button clicked! movie");
        if (mainApp != null) {
            System.out.println("is not null");
            mainApp.showCartPage(session_id, selectedMovie, currentUser, "payment");
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

    public void initialize_product_table()
    {
        product_TableView.getItems().clear();
        cartProduct.setCellValueFactory(new PropertyValueFactory<>("name")); // Example property
        cartQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity")); // Example property
        cartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTaxedPrice() * cellData.getValue().getQuantity()).asObject());
        ObservableList<Product> ProductsList = FXCollections.observableArrayList();
        product_TableView.setItems(ProductsList);
        ProductsList.addAll(facade.getItemsFromDb(mainApp.getOrderNo()));

        double totalPrice = Facade.calculateOrderPrice(mainApp.getOrderNo());
        totalLabel.setText(String.format("TOTAL: %.2f TL", totalPrice));
    }
    @FXML
    private void initialize()
    {
        // Initialize TableView columns for Ticket
        payment_tableView_itemName.setCellValueFactory(new PropertyValueFactory<>("movieName")); // Example property
        payment_tableView_Quantity.setCellValueFactory(new PropertyValueFactory<>("seatNumber")); // Example property
        payment_tableView_price.setCellValueFactory(new PropertyValueFactory<>("ticketPrice")); // Example property

        payment_TableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedTicket = newSelection;
            }
        });



    }


    private void initializeData() {
        if (mainApp != null) {
            try {
                initialize_product_table();
                // Debugging: Print the session_id before creating tickets
                System.out.println("Session ID in initializeData: " + session_id);

                tickets = new ArrayList<>();
                order_ID.setText("Order ID: " + mainApp.getOrderNo());

                int ticketCount = mainApp.getNumOfTicket();
                for (int i = 0; i < ticketCount; i++) {

                    Ticket ticket = new Ticket(
                            i+1, // Ticket index
                            session_id, // Session ID
                            mainApp.getSelectedHall(),
                            mainApp.getSelectedSeats().get(i),
                            "name",
                            "surname",
                            0,
                            mainApp.getOrderNo(),
                            mainApp.getSelectedMovie().getMovieName()
                    );
                    tickets.add(ticket);

                    // Debugging: Print the session_id from the created Ticket
                    System.out.println("Ticket session_id: " + ticket.getSessionId());
                }

                if (tickets != null && !tickets.isEmpty()) {
                    payment_TableView.getItems().addAll(tickets);
                } else {
                    System.out.println("No tickets found for the order.");
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
            } catch(Exception e){
                System.err.println("Error initializing data: " + e.getMessage());
                e.printStackTrace();
            }
        }
        else
        {
            System.err.println("MainApp is null. Cannot initialize data.");
        }
    }

    @FXML
    private void handleEnterButton(ActionEvent event) {
        if (selectedTicket == null) {
            showAlert("No ticket selected!");
            return;
        }

        String name = nameField.getText();
        String surname = surnameField.getText();
        LocalDate birthdate = birthdatePicker.getValue();

        // Validate input fields
        if (name == null || name.trim().isEmpty()) {
            showAlert("Name cannot be empty!");
            return;
        }

        if (surname == null || surname.trim().isEmpty()) {
            showAlert("Surname cannot be empty!");
            return;
        }

        if (!name.matches("[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+")) {
            showAlert("Name can only contain letters and spaces!");
            return;
        }

        if (!surname.matches("[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+")) {
            showAlert("Surname can only contain letters and spaces!");
            return;
        }

        if (birthdate == null) {
            showAlert("Birthdate cannot be empty!");
            return;
        }

        LocalDate today = LocalDate.now();
        if (birthdate.isAfter(today)) {
            showAlert("Birthdate cannot be in the future!");
            return;
        }

        // Update the selected ticket
        selectedTicket.setName(name);
        selectedTicket.setSurname(surname);
        selectedTicket.setAge(calculateAge(birthdate));
        selectedTicket.calculateTicketPrice();

        Facade.deleteTicketsFromCart(mainApp.getOrderNo());
        Facade.addTicketToCart(mainApp.getOrderNo(), selectedTicket.getMovieName(), selectedTicket.getTicketPrice(),1);

        // Update the ticket in the tickets list
        int selectedIndex = payment_TableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tickets.size()) {
            tickets.set(selectedIndex, selectedTicket);
        }

        // Refresh the TableView to reflect the changes
        payment_TableView.refresh();
        initialize_product_table();
        product_TableView.refresh();
        // Debugging: Print ticket details
        System.out.println("Updated Ticket: " + selectedTicket.getName() + " " + selectedTicket.getSurname() + ", Age: " + selectedTicket.getAge());
    }

    @FXML
    private void handlePayButton() {
        if (!areAllTicketsValid(tickets)) {
            showAlert("Please fill in all ticket information before proceeding with payment.");
            return;
        }

        // Get the list of products from the cart
        List<Product> products = facade.getItemsFromDb(mainApp.getOrderNo());

        for(Product product : products)
        {
            facade.decrementStock(product.getName(), product.getQuantity());
        }

        // Generate tickets and invoice
        try {
            // Generate an HTML file for each ticket
            for (int i = 0; i < tickets.size(); i++) {
                String ticketFilePath = "Ticket_" + mainApp.getOrderNo() + "_" + (i + 1) + ".html";
                HTMLGenerator.generateTicketHTML(tickets.get(i), ticketFilePath);
                System.out.println("Generated ticket: " + ticketFilePath);
            }

            // Generate an HTML invoice for the entire order
            String invoiceFilePath = "Invoice_" + mainApp.getOrderNo() + ".html";
            double totalPrice = Facade.calculateOrderPrice(mainApp.getOrderNo());
            HTMLGenerator.generateInvoiceHTML(mainApp.getOrderNo(), totalPrice, tickets, products, invoiceFilePath);
            System.out.println("Generated invoice: " + invoiceFilePath);

            showAlert("Payment processed successfully! Tickets and invoice generated.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error generating tickets or invoice. Please contact support.");
        }

        // Complete the order
        completeOrder(mainApp.getOrderNo(), tickets);
        System.out.println("Payment processed for order: " + mainApp.getOrderNo());

        if (mainApp != null && currentUser != null) {
            mainApp.showMoviePage(currentUser);
        }
    }

    private int calculateAge(LocalDate birthdate) {
        LocalDate today = LocalDate.now();
        return Period.between(birthdate, today).getYears();
    }


    public static void completeOrder(String orderNo, List<Ticket> tickets)
    {
        double price = Facade.calculateOrderPrice(orderNo);
        Facade.createOrder(orderNo,price);
        Facade.buySeats(tickets);
        Facade.addCartItemsToOrderItems(orderNo);
        Facade.addTicketsToTicketsTable(orderNo, tickets);
        Facade.clearCart();
    }

    private boolean areAllTicketsValid(List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            return false; // No tickets to validate
        }

        for (Ticket ticket : tickets) {
            // Check if name, surname, and age are filled in
            if (ticket.getName() == null || ticket.getName().trim().isEmpty() ||
                    ticket.getSurname() == null || ticket.getSurname().trim().isEmpty() ||
                    ticket.getAge() <= 0) {
                return false; // At least one ticket is missing required information
            }
        }

        return true; // All tickets are valid
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}