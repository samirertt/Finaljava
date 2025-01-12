package group11.group11;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
    private final SimpleIntegerProperty orderNo;
    private final SimpleStringProperty orderDate;
    private final SimpleDoubleProperty totalPrice;

    public Order(int orderNo, String orderDate, double totalPrice) {
        this.orderNo = new SimpleIntegerProperty(orderNo);
        this.orderDate = new SimpleStringProperty(orderDate);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
    }

    public int getOrderNo() {
        return orderNo.get();
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }
}
