package group11.group11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

    /**
     * Constructs a new Cart with a unique order number.
     *
     * @param orderNo The unique order number for the cart.
     */
public class Cart {
    private final String orderNo; // Unique order number
    private final List<Product> items; // List of products in the cart

    public Cart(String orderNo) {
        this.orderNo = orderNo;
        this.items = new ArrayList<>();
    }

    /**
     * Adds a product to the cart.
     *
     * @param item The product to be added to the cart.
     */
    public void addProduct(Product item) {
        items.add(item); // Use the constructor with taxedPrice
    }

    /**
     * Removes a product from the cart by its name.
     *
     * @param itemName The name of the product to be removed.
     * @return True if the product was removed, false otherwise.
     */
    public boolean removeItem(String itemName) {
        return items.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }

    /**
     * Returns a copy of the list of products in the cart.
     *
     * @return A list of products in the cart.
     */
    public List<Product> getItems() {
        return new ArrayList<>(items); // Return a copy to preserve encapsulation
    }

    /**
     * Calculates the total price of all products in the cart.
     *
     * @return The total price of the cart.
     */
    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getTaxedPrice() * item.getQuantity())
                .sum();
    }

    /**
     * Returns the unique order number of the cart.
     *
     * @return The order number of the cart.
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * Finds products in the cart by a partial name match.
     *
     * @param partialName The partial name to search for.
     * @return A list of products that match the partial name.
     */
    public List<Product> findProductsByName(String partialName) {
        return items.stream()
                .filter(product -> product.getName().toLowerCase().contains(partialName.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Clears all products from the cart.
     */
    public void clearCart() {
        items.clear();
    }

    /**
     * Returns a string representation of the cart.
     *
     * @return A string representation of the cart, including the order number, items, and total price.
     */
    @Override
    public String toString() {
        return String.format("Cart{orderNo='%s', items=%s, totalPrice=%.2f}", orderNo, items, getTotalPrice());
    }
}
