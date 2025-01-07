package group11.group11.Controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class customer_products {

    @FXML
    private TableView<Pair<String, Pair<Integer, Double>>> productTable;
    @FXML
    private TableColumn<Pair<String, Pair<Integer, Double>>, String> productColumn;
    @FXML
    private TableColumn<Pair<String, Pair<Integer, Double>>, Integer> quantityColumn;
    @FXML
    private TableColumn<Pair<String, Pair<Integer, Double>>, Double> totalColumn;
    @FXML
    private ObservableList<Pair<String, Pair<Integer, Double>>> productList;
    @FXML
    private Map<String, Integer> productQuantities;
    @FXML
    private Map<String, Double> productPrices;

    @FXML
    private Label totalAmountLabel;

    @FXML
    private Button inc_drink1; // Sprite
    @FXML
    private Button dec_drink1;
    @FXML
    private Label qty_drink1;

    @FXML
    private Button inc_drink2; // Coca-Cola
    @FXML
    private Button dec_drink2;
    @FXML
    private Label qty_drink2;

    @FXML
    private Button inc_drink3; // Ice-tea
    @FXML
    private Button dec_drink3;
    @FXML
    private Label qty_drink3;

    @FXML
    private Button inc_drink4; // Water
    @FXML
    private Button dec_drink4;
    @FXML
    private Label qty_drink4;


    @FXML
    private Button inc_food1;  // Popcorn
    @FXML
    private Button dec_food1;
    @FXML
    private Label qty_food1;

    @FXML
    private Button inc_food2;  // Snickers
    @FXML
    private Button dec_food2;
    @FXML
    private Label qty_food2;

    @FXML
    private Button inc_food3;  // Hanımeller
    @FXML
    private Button dec_food3;
    @FXML
    private Label qty_food3;

    @FXML
    private Button inc_food4;  // Falım
    @FXML
    private Button dec_food4;
    @FXML
    private Label qty_food4;

    @FXML
    private Button inc_toy3;
    @FXML
    private Button dec_toy3;
    @FXML
    private Label qty_toy3;

    @FXML
    private Button inc_toy4;
    @FXML
    private Button dec_toy4;
    @FXML
    private Label qty_toy4;

    @FXML
    private Button inc_toy1;
    @FXML
    private Button dec_toy1;
    @FXML
    private Label qty_toy1;

    @FXML
    private Button inc_toy2;
    @FXML
    private Button dec_toy2;
    @FXML
    private Label qty_toy2;
    @FXML
    private Button PayScreenBtn;

    @FXML
    private void initialize() {
        // Initialize the labels with the starting counts
        qty_drink1.setText("0");
        qty_drink2.setText("0");
        qty_drink3.setText("0");
        qty_drink4.setText("0");
        qty_food1.setText("0");
        qty_food2.setText("0");
        qty_food3.setText("0");
        qty_food4.setText("0");
        qty_toy1.setText("0");
        qty_toy2.setText("0");
        qty_toy3.setText("0");
        qty_toy4.setText("0");

        // Initialize the TableView and its columns
        productList = FXCollections.observableArrayList();
        productColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValue().getKey()).asObject());
        totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValue().getValue()).asObject());
        productTable.setItems(productList);

        // Initialize product quantities and prices
        productQuantities = new HashMap<>();
        productPrices = new HashMap<>();

        productPrices.put("Sprite", 60.0);
        productPrices.put("Coca-Cola", 60.0);
        productPrices.put("Ice-tea", 60.0);
        productPrices.put("Water", 30.0);
        productPrices.put("Popcorn", 115.0);
        productPrices.put("Snickers", 25.0);
        productPrices.put("Hanımeller", 15.0);
        productPrices.put("Falım", 35.0);
        productPrices.put("Toy1", 100.0);
        productPrices.put("Toy2", 100.0);
        productPrices.put("Toy3", 100.0);
        productPrices.put("Toy4", 1000.0);
    }

    @FXML
    private void increment(ActionEvent event) {
        Button button = (Button) event.getSource();
        Label label = getLabelForButton(button);
        if (label != null) {
            int count = Integer.parseInt(label.getText());
            count++;
            label.setText(Integer.toString(count));
            updateTable(button, count);
        }
    }

    @FXML
    private void decrement(ActionEvent event) {
        Button button = (Button) event.getSource();
        Label label = getLabelForButton(button);
        if (label != null) {
            int count = Integer.parseInt(label.getText());
            if (count > 0) {
                count--;
                label.setText(Integer.toString(count));
                updateTable(button, count);
            }
        }
    }

    private Label getLabelForButton(Button button) {
        if (button == inc_drink1 || button == dec_drink1) {
            return qty_drink1;
        } else if (button == inc_drink2 || button == dec_drink2) {
            return qty_drink2;
        } else if (button == inc_drink3 || button == dec_drink3) {
            return qty_drink3;
        } else if (button == inc_drink4 || button == dec_drink4) {
            return qty_drink4;
        } else if (button == inc_food1 || button == dec_food1) {
            return qty_food1;
        } else if (button == inc_food2 || button == dec_food2) {
            return qty_food2;
        } else if (button == inc_food3 || button == dec_food3) {
            return qty_food3;
        } else if (button == inc_food4 || button == dec_food4) {
            return qty_food4;
        } else if (button == inc_toy1 || button == dec_toy1) {
            return qty_toy1;
        } else if (button == inc_toy2 || button == dec_toy2) {
            return qty_toy2;
        }else if (button == inc_toy3 || button == dec_toy3) {
            return qty_toy3;
        }else if (button == inc_toy4 || button == dec_toy4) {
            return qty_toy4;
        }

        return null;
    }

    private void updateTable(Button button, int count) {
        String productName = "";
        double productPrice = 0.0;

        if (button == inc_drink1 || button == dec_drink1) {
            productName = "Sprite";
        } else if (button == inc_drink2 || button == dec_drink2) {
            productName = "Coca-Cola";
        } else if (button == inc_drink3 || button == dec_drink3) {
            productName = "Ice-tea";
        } else if (button == inc_drink4 || button == dec_drink4) {
            productName = "Water";
        } else if (button == inc_food1 || button == dec_food1) {
            productName = "Popcorn";
        } else if (button == inc_food2 || button == dec_food2) {
            productName = "Snickers";
        } else if (button == inc_food3 || button == dec_food3) {
            productName = "Hanımeller";
        } else if (button == inc_food4 || button == dec_food4) {
            productName = "Falım";
        } else if (button == inc_toy1 || button == dec_toy1) {
            productName = "Toy1";
        } else if (button == inc_toy2 || button == dec_toy2) {
            productName = "Toy2";
        } else if (button == inc_toy3 || button == dec_toy3) {
            productName = "Toy3";
        } else if (button == inc_toy4 || button == dec_toy4) {
            productName = "Toy4";
        }

        productPrice = productPrices.get(productName);
        productQuantities.put(productName, count);

        boolean found = false;
        for (int i = 0; i < productList.size(); i++) {
            Pair<String, Pair<Integer, Double>> product = productList.get(i);
            if (product.getKey().equals(productName)) {
                productList.set(i, new Pair<>(productName, new Pair<>(count, count * productPrice)));
                found = true;
                break;
            }
        }
        if (!found) {
            productList.add(new Pair<>(productName, new Pair<>(count, count * productPrice)));
        }
        productTable.refresh();
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        double totalAmount = 0.0;
        for (Pair<String, Pair<Integer, Double>> product : productList) {
            totalAmount += product.getValue().getValue();
        }
        totalAmountLabel.setText(String.format("Total: %.2f₺ ", totalAmount));
    }


    @FXML
    public void btnPayScreen(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/group11/group11/fxml/payment.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) PayScreenBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error occurred while loading page");
            e.printStackTrace();
        }

    }
}
