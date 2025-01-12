package group11.group11;

import group11.group11.Controller.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    public Stage primaryStage;
    private String orderNo; // Field to hold the order number

    private Movie selecetedMovie;
    private Date selectedDate;
    private Time selectedTime;
    private String selectedHall;
    private int selectedSession;
    private int numOfTicket;

    private ObservableList<Ticket> ticketList = FXCollections.observableArrayList();
    private List<String> selectedSeats = new ArrayList<>();
    private ObservableList<Product> selectedProducts = FXCollections.observableArrayList();
    private Users currentUser;

    public void setUser(Users user) {
        this.currentUser = user;
    }

    public Movie getSelectedMovie() {
        return selecetedMovie;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }
    public Users getCurrentUser(){ return currentUser; }

    public Time getSelectedTime() {
        return selectedTime;
    }

    public String getSelectedHall() {
        return selectedHall;
    }
    public int getSelectedSession()
    {
        return this.selectedSession;
    }
    public int getNumOfTicket()
    {
        return this.numOfTicket;
    }

    public List<String> getSelectedSeats() {
        return this.selectedSeats;
    }

    public void setNumOfTicket(int numOfTicket)
    {
        this.numOfTicket = numOfTicket;
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selecetedMovie = selectedMovie;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public void setSelectedTime(Time selectedTime) {
        this.selectedTime = selectedTime;
    }

    public void setSelectedSession(int selectedSession) {
        this.selectedSession = selectedSession;
    }

    public void setSelectedHall(String selectedHall) {
        this.selectedHall = selectedHall;
    }

    public void setSelectedSeats(List<String> selectedSeats) { this.selectedSeats = selectedSeats;}

    public void setTicketList(ObservableList<Ticket> ticketList)
    { this.ticketList = ticketList;}

    public ObservableList<Ticket> getTicketList()
    {
        return this.ticketList;
    }

    public ObservableList<Product> getSelectedProducts()
    {
        return this.selectedProducts;
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage; // Store the primary stage
        showLoginPage(); // Start with the login page
        ticketList = FXCollections.observableArrayList();
        selectedProducts  = FXCollections.observableArrayList();
    }

    public void logout() {
        showLoginPage();
    }

    // Generate a random order number and store it in the field
    public String generateRandomOrderNo() {
        int orderNo = new Random().nextInt(900000) + 100000; // 6-digit random number
        this.orderNo = String.valueOf(orderNo); // Store it in the field
        return this.orderNo;
    }

    // Get the generated order number
    public String getOrderNo() {
        return this.orderNo;
    }

    // Show the Login page
    public void showLoginPage() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/loginPage.fxml"));
            Parent root = loader.load();
            LoginPageController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setTitle("Login Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
            this.orderNo = generateRandomOrderNo();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Login FXML file.");
        }
    }

    // Show the Movie page
    public void showMoviePage(Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/moviesearch.fxml"));
            Parent root = loader.load();
            MoviePageController controller = loader.getController();
            controller.setCurrentUser(user);
            controller.setProfileDetails();
            controller.setMainApp(this);
            primaryStage.setTitle("Movie Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Movie FXML file.");
        }
    }
    public void showDaySelectionPage(Users user, Movie selectedMovie) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/daySessionHallSelection.fxml"));
            Parent root = loader.load();

            daySelectionController controller = loader.getController();
            controller.setMainApp(this);
            controller.setCurrentUser(user);
            controller.setProfileDetails();

            primaryStage.setTitle("Day Selection Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Day Selection FXML file.");
        }
    }


    public void showManagerPage(Users currentUser)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/manager.fxml"));
            Parent root = loader.load();

            managerController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setProfileDetails();
            controller.setMainApp(this);
            primaryStage.setTitle("manager Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" There was an error loading the page: " + e.getMessage());
        }
    }

    public void showAdminPage(Users currentUser)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AdminMainPage.fxml"));
            Parent root = loader.load();

            MainpageController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setProfileDetails();
            controller.setMainApp(this);
            primaryStage.setTitle("Admin Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" There was an error loading the Admin page: " + e.getMessage());
        }
    }

    public void showUpdateMoviePage(Users currentUser)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/UpdateMovie.fxml"));
            Parent root = loader.load();

            MainpageController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setProfileDetails();
            controller.setMainApp(this);
            primaryStage.setTitle("Update Movie Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" There was an error loading the Update Movie page: " + e.getMessage());
        }
    }

    public void showAddSchedule(Users currentUser)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AddSchedule.fxml"));
            Parent root = loader.load();

            AddScheduleController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setProfileDetails();
            controller.setMainApp(this);
            primaryStage.setTitle("Add Schedule Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" There was an error loading the Update Movie page: " + e.getMessage());
        }
    }

    public void showUpdateSchedule(Users currentUser)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/UpdateSchedule.fxml"));
            Parent root = loader.load();
            UpdateScheduleController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setProfileDetails();
            controller.setMainApp(this);
            primaryStage.setTitle("Update Schedule Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" There was an error loading the Update Schedule page: " + e.getMessage());
        }
    }

    public void showCancelAndRefund(Users currentUser)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/CancelAndRefund.fxml"));
            Parent root = loader.load();

            CancelAndRefundController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setProfileDetails();
            controller.setMainApp(this);
            primaryStage.setTitle("Update Schedule Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" There was an error loading the Update Schedule page: " + e.getMessage());
        }
    }

    public void showAddMovie(Users currentUser)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AddMovie.fxml"));
            Parent root = loader.load();
            AddMovieController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setProfileDetails();
            controller.setMainApp(this);
            primaryStage.setTitle("Add Movie Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error loading the Add Movie page: " + e.getMessage());
        }
    }

    public void handleProductButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/manager_product.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Day Selection Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Day Selection FXML file.");
        }
    }
    public void handleCinemaPriceButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Day Selection Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Day Selection FXML file.");
        }
    }

    public void handleDiscountsButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/manager_AgeDiscount.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Day Selection Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Day Selection FXML file.");
        }
    }

    public void handleRevenueAndTaxButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/manager_tax.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Day Selection Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Day Selection FXML file.");
        }
    }

    public void handleEmployeeButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/employee.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Day Selection Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Day Selection FXML file.");
        }
    }

    // Show the Cart page
    public void showCartPage(int session_id, Movie selectedMovie, Users user, String previousPage) {
        try {
            System.out.println("Opening cart page...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/cart.fxml"));
            Parent root = loader.load();

            // Ensure the correct controller is set
            CartPageController controller = loader.getController();
            controller.setSessionId(session_id);
            controller.setCurrentUser(user);
            controller.setProfileDetails();
            controller.setSelectedMovie(selectedMovie);
            controller.setPreviousPage(previousPage);

            System.out.println("Setting mainApp in CartPageController...");
            controller.setMainApp(this); // Pass the mainApp reference to the controller

            System.out.println("Initializing data in CartPageController...");
            controller.initializeData(); // Initialize data after mainApp is set

            primaryStage.setTitle("Cart Page"); // Use primaryStage
            primaryStage.setScene(new Scene(root)); // Set the scene for the primaryStage
            primaryStage.show(); // Show the primaryStage

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load the Cart FXML file.");
        }
    }
    public void openHallBPage(int session_id, Movie selectedMovie, Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/group11/group11/fxml/seatSelection.fxml"));
            Parent root = loader.load();

            seatSelectionController controller = loader.getController();
            controller.setCurrentUser(user);
            controller.setProfileDetails();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error occurred while loading Hall B page");
            e.printStackTrace();
        }
    }

    public void openHallAPage(int session_id,  Movie selectedMovie, Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/group11/group11/fxml/seatSelectionA.fxml"));
            Parent root = loader.load();
            seatSelectionAController controller = loader.getController();
            controller.setMainApp(this);
            controller.setCurrentUser(user);
            controller.setProfileDetails();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error occurred while loading Hall A page");
            e.printStackTrace();
        }
    }

    public void ProductPurchase(int session_id,  Movie selectedMovie, Users user, String previousPage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/customer_products.fxml"));
            Parent root = loader.load();
            customer_products controller = loader.getController();

            controller.setMainApp(this);
            controller.setCurrentUser(user);
            controller.setProfileDetails();
            controller.setPreviousPage(previousPage);
            System.out.println("Setting mainApp in customer_products controller..."); // Debug statement

            // Pass the Main instance to the controller
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error occurred while loading page");
            e.printStackTrace();
        }
    }

    public void btnPayScreen(int session_id, Users user, Movie selectedMovie, String previousPage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/payment.fxml"));
            Parent root = loader.load();
            payment controller = loader.getController();
            controller.setMainApp(this);
            controller.setCurrentUser(user);
            controller.setProfileDetails();
            controller.setPreviousPage(previousPage);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error occurred while loading page");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}