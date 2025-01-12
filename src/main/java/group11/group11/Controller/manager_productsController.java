package group11.group11.Controller;


import group11.group11.Employee;
import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Product;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class manager_productsController
{
    Facade facade = new Facade();
    @FXML private TextField idField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;


    @FXML private Button updateProductButton;
    @FXML private Button menuButton;

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<?, ?> idColumn;
    @FXML private TableColumn<?, ?> nameColumn;
    @FXML private TableColumn<?, ?> priceColumn;
    @FXML private TableColumn<?, ?> stockColumn;

    private Product selectedProduct;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        setupTable();
        loadProducts();
        productTable.setOnMouseClicked(this::selectRow);
        updateProductButton.setOnAction(event -> updateProduct());
        menuButton.setOnAction(event -> mainApp.showManagerPage());
    }

    private void selectRow(javafx.scene.input.MouseEvent mouseEvent) {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null)
        {
            idField.setText(String.valueOf(selectedProduct.getProductId()));
            priceField.setText(String.valueOf(selectedProduct.getPrice()));
            stockField.setText(String.valueOf(selectedProduct.getStock()));
        }
        else
        {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a product!");
        }
    }

    private void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    private void loadProducts()
    {
        productTable.getItems().clear();
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        productTable.setItems(productsList);
        productsList.addAll(facade.getProductsFromDatabase());

    }

    private void updateProduct()
    {
        try
        {
            int id;
            try
            {
                id = Integer.parseInt(idField.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a valid product ID!");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a valid price!");
                return;
            }

            int stock;
            try {
                stock = Integer.parseInt(stockField.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a valid stock quantity!");
                return;
            }

            String name = selectedProduct.getName();
            facade.updateStock(name,stock);
            facade.updateProductPrice(name,price);
            facade.updateProductTaxedPrice(name,price);

            loadProducts();
            clearFields();

        }
        catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + e.getMessage());
        }
    }


    private void clearFields() {
        idField.clear();
        priceField.clear();
        stockField.clear();
    }

    private void openPage(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx1/" + pageName + ".fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(pageName + " Page");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) menuButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(pageName + " There was an error loading the page: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}