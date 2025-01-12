package group11.group11;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class orderItem {
    private final SimpleIntegerProperty orderItemId;
    private final SimpleIntegerProperty orderNo;
    private final SimpleStringProperty itemType;
    private final SimpleIntegerProperty itemId;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty pricePerItem;

    public orderItem(int orderItemId, int orderNo, String itemType, int itemId, int quantity, double pricePerItem) {
        this.orderItemId = new SimpleIntegerProperty(orderItemId);
        this.orderNo = new SimpleIntegerProperty(orderNo);
        this.itemType = new SimpleStringProperty(itemType);
        this.itemId = new SimpleIntegerProperty(itemId);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.pricePerItem = new SimpleDoubleProperty(pricePerItem);
    }

    public int getOrderItemId() {
        return orderItemId.get();
    }

    public int getOrderNo() {
        return orderNo.get();
    }

    public String getItemType() {
        return itemType.get();
    }

    public int getItemId() {
        return itemId.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getPricePerItem() {
        return pricePerItem.get();
    }
}