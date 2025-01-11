package group11.group11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a shopping cart associated with a unique order number.
 */
public class Cart {
    private final String orderNo; // Unique order number
    private final List<Product> products; // List of products in the cart

    public Cart(String orderNo) {
        this.orderNo = orderNo;
        this.products = new ArrayList<>();
    }

    /**
     * Adds a product to the cart.
     *
     * @param name      the name of the product
     * @param price     the price of the product
     * @param quantity  the quantity of the product
     */
    public void addProduct(String name, double price,int quantity)
    {
        products.add(new Product(name, price, quantity));
    }

    /**
     * Removes a product from the cart by name.
     *
     * @param productName the name of the product to remove
     * @return true if the product was removed, false otherwise
     */
    public boolean removeProduct(String productName) {
        return products.removeIf(product -> product.getName().equalsIgnoreCase(productName));
    }

    /**
     * Calculates the total price of all products in the cart (excluding tax).
     *
     * @return the total price
     */
    public double getTotalPrice() {
        return products.stream()
                .mapToDouble(product -> product.getPrice() * product.getStock())
                .sum();
    }

    /**
     * Calculates the total price of all products in the cart (including tax).
     *
     * @return the total price with tax
     */
    public double getTotalPriceWithTax() {
        return products.stream()
                .mapToDouble(product -> product.getTaxedPrice() * product.getStock())
                .sum();
    }

    /**
     * Retrieves all products in the cart.
     *
     * @return a list of products in the cart
     */
    public List<Product> getProducts() {
        return new ArrayList<>(products); // Return a copy to preserve encapsulation
    }

    /**
     * Retrieves the unique order number for the cart.
     *
     * @return the order number
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * Finds products by a partial match on the name.
     *
     * @param partialName the partial name to search for
     * @return a list of matching products
     */
    public List<Product> findProductsByName(String partialName) {
        return products.stream()
                .filter(product -> product.getName().toLowerCase().contains(partialName.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Updates the quantity of a product in the cart.
     *
     * @param productName the name of the product to update
     * @param newQuantity the new quantity of the product
     */
    public void updateProductQuantity(String productName, int newQuantity) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                product.setStock(newQuantity);
                return;
            }
        }
        // If the product is not found, add it to the cart
        addProduct(productName, 0, newQuantity); // Assuming productId and price are not needed here
    }

    /**
     * Checks if a product exists in the cart.
     *
     * @param productName the name of the product to check
     * @return true if the product exists, false otherwise
     */
    public boolean containsProduct(String productName) {
        return products.stream()
                .anyMatch(product -> product.getName().equalsIgnoreCase(productName));
    }

    /**
     * Clears all products from the cart.
     */
    public void clearCart() {
        products.clear();
    }

    @Override
    public String toString() {
        return String.format("Cart{orderNo='%s', products=%s, totalPrice=%.2f, totalPriceWithTax=%.2f}",
                orderNo, products, getTotalPrice(), getTotalPriceWithTax());
    }
}