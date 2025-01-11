package group11.group11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Cart {
    private final String orderNo; // Unique order number
    private final List<Product> items; // List of products in the cart

    public Cart(String orderNo) {
        this.orderNo = orderNo;
        this.items = new ArrayList<>();
    }

    public void addProduct(Product item) {
        items.add(item); // Use the constructor with taxedPrice
    }

    public boolean removeItem(String itemName) {
        return items.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public List<Product> getItems() {
        return new ArrayList<>(items); // Return a copy to preserve encapsulation
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getTaxedPrice() * item.getQuantity())
                .sum();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public List<Product> findProductsByName(String partialName) {
        return items.stream()
                .filter(product -> product.getName().toLowerCase().contains(partialName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void clearCart() {
        items.clear();
    }

    @Override
    public String toString() {
        return String.format("Cart{orderNo='%s', items=%s, totalPrice=%.2f}", orderNo, items, getTotalPrice());
    }
}