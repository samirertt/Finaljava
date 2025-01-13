/**
 * The Order class represents an order entity with details such as order number, order date, and total price.
 * It uses JavaFX properties to allow binding with UI components.
 */
package group11.group11;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Order {
    private final SimpleIntegerProperty orderNo;
    private final SimpleStringProperty orderDate;
    private final SimpleDoubleProperty totalPrice;

    /**
     * Constructs an Order object with the specified order number, order date, and total price.
     *
     * @param orderNo    The unique number of the order.
     * @param orderDate  The date when the order was placed.
     * @param totalPrice The total price of the order.
     */
    public Order(int orderNo, String orderDate, double totalPrice) {
        this.orderNo = new SimpleIntegerProperty(orderNo);
        this.orderDate = new SimpleStringProperty(orderDate);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
    }

    /**
     * Returns the order number.
     *
     * @return The order number.
     */
    public int getOrderNo() {
        return orderNo.get();
    }

    /**
     * Returns the order date.
     *
     * @return The order date.
     */
    public String getOrderDate() {
        return orderDate.get();
    }

    /**
     * Returns the total price of the order.
     *
     * @return The total price.
     */
    public double getTotalPrice() {
        return totalPrice.get();
    }
}
