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

    /**
     * Represents an individual product in the cart.
     */
    public static class Product {
        private String name;       // Name of the product
        private double price;      // Price of the product
        private int quantity;      // Quantity of the product

        public Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return String.format("Product{name='%s', price=%.2f, quantity=%d}", name, price, quantity);
        }
    }

    /**
     * Constructs a Cart with a unique order number.
     *
     * @param orderNo the unique order number for the cart
     */
    public Cart(String orderNo) {
        this.orderNo = orderNo;
        this.products = new ArrayList<>();
    }

    /**
     * Adds a product to the cart.
     *
     * @param name     the name of the product
     * @param price    the price of the product
     * @param quantity the quantity of the product
     */
    public void addProduct(String name, double price, int quantity) {
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
     * Calculates the total price of all products in the cart.
     *
     * @return the total price
     */
    public double getTotalPrice() {
        return products.stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
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

    @Override
    public String toString() {
        return String.format("Cart{orderNo='%s', products=%s, totalPrice=%.2f}",
                orderNo, products, getTotalPrice());
    }
}
