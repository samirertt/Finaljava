package group11.group11.Controller;

import group11.group11.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class customer_products {
    Facade facade = new Facade();
    private Users currentUser;
    private String previousPage;
    private Main mainApp;

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Double> totalColumn;

    private List<Product> products;

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
    private Label totalAmountLabel;

    @FXML
    private Button inc_drink1; // Sprite
    @FXML
    private Button dec_drink1;
    @FXML
    private Label qty_drink1;
    @FXML
    private Label price_drink1;

    @FXML
    private Button inc_drink2; // Coca-Cola
    @FXML
    private Button dec_drink2;
    @FXML
    private Label qty_drink2;
    @FXML
    private Label price_drink2;

    @FXML
    private Button inc_drink3; // Ice-tea
    @FXML
    private Button dec_drink3;
    @FXML
    private Label qty_drink3;
    @FXML
    private Label price_drink3;

    @FXML
    private Button inc_drink4; // Water
    @FXML
    private Button dec_drink4;
    @FXML
    private Label qty_drink4;
    @FXML
    private Label price_drink4;

    @FXML
    private Button inc_food1;  // Popcorn
    @FXML
    private Button dec_food1;
    @FXML
    private Label qty_food1;
    @FXML
    private Label price_food1;

    @FXML
    private Button inc_food2;  // Snickers
    @FXML
    private Button dec_food2;
    @FXML
    private Label qty_food2;
    @FXML
    private Label price_food2;

    @FXML
    private Button inc_food3;  // Hanımeller
    @FXML
    private Button dec_food3;
    @FXML
    private Label qty_food3;
    @FXML
    private Label price_food3;

    @FXML
    private Button inc_food4;  // Falım
    @FXML
    private Button dec_food4;
    @FXML
    private Label qty_food4;
    @FXML
    private Label price_food4;

    @FXML
    private Button inc_toy3;
    @FXML
    private Button dec_toy3;
    @FXML
    private Label qty_toy3;
    @FXML
    private Label price_toy3;

    @FXML
    private Button inc_toy4;
    @FXML
    private Button dec_toy4;
    @FXML
    private Label qty_toy4;
    @FXML
    private Label price_toy4;

    @FXML
    private Button inc_toy1;
    @FXML
    private Button dec_toy1;
    @FXML
    private Label qty_toy1;
    @FXML
    private Label price_toy1;

    @FXML
    private Button inc_toy2;
    @FXML
    private Button dec_toy2;
    @FXML
    private Label qty_toy2;
    @FXML
    private Label price_toy2;

    @FXML
    private Button PayScreenBtn;


    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    public void movieSearch_windowClose_btn() {
        System.exit(0);
    }

    @FXML
    public void handleLogoutButton(ActionEvent event) {
        if (mainApp != null) {
            mainApp.logout();
        }
    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        if (mainApp != null && currentUser != null && mainApp.getSelectedMovie() != null) {
            if ("seatSelection".equals(previousPage)) {
                mainApp.openHallAPage(mainApp.getSelectedSession(), mainApp.getSelectedMovie(), currentUser);
            } else if ("A".equals(previousPage)) {
                mainApp.openHallAPage(mainApp.getSelectedSession(), mainApp.getSelectedMovie(), currentUser);
            } else if ("B".equals(previousPage)) {
                mainApp.openHallBPage(mainApp.getSelectedSession(), mainApp.getSelectedMovie(), currentUser);
            } else {
                System.out.println("No previous page specified!");
            }
        }
    }

    @FXML
    private void handleOpenCartPage() {
        System.out.println("Cart button clicked! movie");
        if (mainApp != null) {
            System.out.println("is not null");
            //mainApp.showCartPage(mainApp.getSession_id(), selectedMovie, currentUser, "customerProducts");
        }
    }

    public void MovieSearch_windowMinimize() {
        Stage stage = (Stage) movieSearch_windowMinimize_btn.getScene().getWindow();
        stage.setIconified(true);
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
        System.out.println("Setting mainApp: " + mainApp);
        this.mainApp = mainApp;
        this.facade = new Facade();
        initializeAfter();
    }

    @FXML
    private void initialize() {


        searchMovie_cart.setOnAction(event -> {
            System.out.println("Cart button clicked!");
            handleOpenCartPage();
        });

        if (backButton != null) {
            backButton.setOnAction(this::handleBackButton);
        }

        if (logoutButton != null) {
            logoutButton.setOnAction(this::handleLogoutButton);
        }
    }

    private void initializeAfter()
    {
        products = facade.getProductsFromDatabase();

        if (mainApp != null) {
            // Initialize quantities based on selected product
            if (mainApp.getSelectedProducts() != null)
            {
                for (Product cartProduct : mainApp.getSelectedProducts()) {
                    Product matchingProduct = findProductByName(cartProduct.getName());
                    if (matchingProduct != null) {
                        matchingProduct.setQuantity(cartProduct.getQuantity());
                    }
                }
            }

            updateLabelsFromProducts();


            //productTable.refresh();
            productColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTaxedPrice() * cellData.getValue().getQuantity()).asObject());
            productTable.setItems(mainApp.getSelectedProducts());

        } else {
            System.out.println("mainApp is null!");
        }
    }

    @FXML
    private void increment(ActionEvent event)
    {
        Button button = (Button) event.getSource();
        String productName = getProductNameForButton(button);
        Product product = findProductByName(productName);

        if (product != null) {
            int quantity = product.getQuantity();
            int currentStock = Facade.checkStock(productName);

            if (currentStock > quantity) {
                quantity++;
                product.setQuantity(quantity);
                updateLabelsFromProducts();
                updateTable(productName, quantity);
            } else {
                showAlert("Not enough stock for " + productName);
            }
        }
    }

    @FXML
    private void decrement(ActionEvent event) {
        Button button = (Button) event.getSource();
        String productName = getProductNameForButton(button);
        Product product = findProductByName(productName);

        if (product != null) {
            int quantity = product.getQuantity();
            if (quantity > 0)
            {
                quantity--;
                product.setQuantity(quantity);
                updateLabelsFromProducts();
                updateTable(productName, quantity);
            }
        }
    }

    private Product findProductByName(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }


    private String getProductNameForButton(Button button) {
        if (button == inc_drink1 || button == dec_drink1) {
            return "Sprite";
        } else if (button == inc_drink2 || button == dec_drink2) {
            return "Coca-Cola";
        } else if (button == inc_drink3 || button == dec_drink3) {
            return "Ice-tea";
        } else if (button == inc_drink4 || button == dec_drink4) {
            return "Water";
        } else if (button == inc_food1 || button == dec_food1) {
            return "Popcorn";
        } else if (button == inc_food2 || button == dec_food2) {
            return "Snickers";
        } else if (button == inc_food3 || button == dec_food3) {
            return "Hanımeller";
        } else if (button == inc_food4 || button == dec_food4) {
            return "Falım";
        } else if (button == inc_toy1 || button == dec_toy1) {
            return "3D Glasses";
        } else if (button == inc_toy2 || button == dec_toy2) {
            return "Teddy Bear";
        } else if (button == inc_toy3 || button == dec_toy3) {
            return "Action Figure";
        } else if (button == inc_toy4 || button == dec_toy4) {
            return "Lego Set";
        }
        return null;
    }

    private void updateTable(String productName, int count) {
        Product product = findProductByName(productName);

        if (product != null) {
            product.setQuantity(count); // Update the quantity in the products list

            Product existingProduct = findProductInSelectedProducts(productName);
            if (existingProduct != null) {
                existingProduct.setQuantity(count);
            } else {
                mainApp.getSelectedProducts().add(new Product(product.getName(), product.getTaxedPrice(), count));
            }

            productTable.refresh();
            updateLabelsFromProducts();
            updateTotalAmount();
        }
    }

    private void updateTotalAmount() {
        double totalAmount = 0.0;
        for (Product product : mainApp.getSelectedProducts()) {
            totalAmount += product.getTaxedPrice() * product.getQuantity();
        }
        totalAmountLabel.setText(String.format("Total: %.2f₺", totalAmount));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void btnPayScreen(ActionEvent event)
    {
        mainApp.setSelectedMovie(mainApp.getSelectedMovie());
        mainApp.btnPayScreen(mainApp.getSelectedSession(), currentUser, mainApp.getSelectedMovie(), previousPage);
    }

    private void updateLabelsFromProducts() {
        for (Product product : products) {
            switch (product.getName()) {
                case "Sprite":
                    qty_drink1.setText(Integer.toString(product.getQuantity()));
                    price_drink1.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "Coca-Cola":
                    qty_drink2.setText(Integer.toString(product.getQuantity()));
                    price_drink2.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "Ice-tea":
                    qty_drink3.setText(Integer.toString(product.getQuantity()));
                    price_drink3.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "Water":
                    qty_drink4.setText(Integer.toString(product.getQuantity()));
                    price_drink4.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "Popcorn":
                    qty_food1.setText(Integer.toString(product.getQuantity()));
                    price_food1.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "Snickers":
                    qty_food2.setText(Integer.toString(product.getQuantity()));
                    price_food2.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "Hanımeller":
                    qty_food3.setText(Integer.toString(product.getQuantity()));
                    price_food3.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "Falım":
                    qty_food4.setText(Integer.toString(product.getQuantity()));
                    price_food4.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "3D Glasses":
                    qty_toy1.setText(Integer.toString(product.getQuantity()));
                    price_toy1.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "Teddy Bear":
                    qty_toy2.setText(Integer.toString(product.getQuantity()));
                    price_toy2.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "Action Figure":
                    qty_toy3.setText(Integer.toString(product.getQuantity()));
                    price_toy3.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "Lego Set":
                    qty_toy4.setText(Integer.toString(product.getQuantity()));
                    price_toy4.setText(String.format("%.2f₺", product.getPrice()));
                    break;
                case "Ticket":
                    break;
                default:
                    System.out.println("Unknown product: ");
                    break;
            }
        }
    }
    private Product findProductInSelectedProducts(String productName) {
        for (Product product : mainApp.getSelectedProducts()) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }
}