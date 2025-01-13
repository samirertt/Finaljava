/**
 * The Product class represents a product entity with details such as product ID, name, price, taxed price, stock, and quantity.
 * It provides constructors to initialize product objects and getter/setter methods to access and modify product attributes.
 */
package group11.group11;

public class Product {
    private int productId;
    private String name;
    private double price;
    private double taxedPrice;
    private int stock;
    private int quantity;

    /**
     * Constructs a Product object with all fields.
     *
     * @param productId  The unique ID of the product.
     * @param name       The name of the product.
     * @param price      The price of the product.
     * @param taxedPrice The taxed price of the product.
     * @param stock      The stock quantity of the product.
     */
    public Product(int productId, String name, double price, double taxedPrice, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.taxedPrice = taxedPrice;
        this.stock = stock;
    }

    /**
     * Constructs a Product object with name, taxed price, and quantity.
     *
     * @param name       The name of the product.
     * @param taxedPrice The taxed price of the product.
     * @param quantity   The quantity of the product.
     */
    public Product(String name, double taxedPrice, int quantity) {
        this.name = name;
        this.taxedPrice = taxedPrice;
        this.quantity = quantity;
    }

    /**
     * Constructs a Product object with order item details.
     *
     * @param orderItemId   The ID of the order item.
     * @param orderNo       The order number.
     * @param itemType      The type of the item.
     * @param itemId        The ID of the item.
     * @param quantity      The quantity of the item.
     * @param pricePerItem  The price per item.
     */
    public Product(int orderItemId, int orderNo, String itemType, int itemId, int quantity, double pricePerItem) {


    }



    /**
     * Returns the unique ID of the product.
     *
     * @return The product ID.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets the unique ID of the product.
     *
     * @param productId The product ID to set.
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Returns the name of the product.
     *
     * @return The product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name The product name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the price of the product.
     *
     * @return The product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price The product price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the taxed price of the product.
     *
     * @return The taxed price.
     */
    public double getTaxedPrice() {
        return taxedPrice;
    }

    /**
     * Sets the taxed price of the product.
     *
     * @param taxedPrice The taxed price to set.
     */
    public void setTaxedPrice(double taxedPrice) {
        this.taxedPrice = taxedPrice;
    }

    /**
     * Returns the stock quantity of the product.
     *
     * @return The stock quantity.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock quantity of the product.
     *
     * @param stock The stock quantity to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returns the quantity of the product.
     *
     * @return The product quantity.
     */
    public int getQuantity() {return this.quantity;}

    /**
     * Sets the quantity of the product.
     *
     * @param quantity The product quantity to set.
     */
    public void setQuantity(int quantity)
    {
        this.quantity=quantity;
    }

    /**
     * Returns a string representation of the Product object.
     *
     * @return A string representation of the product.
     */
    @Override
    public String toString() {
        return String.format("Product{product_id=%d, name='%s', price=%.2f, taxedPrice=%.2f, stock=%d}",
                productId, name, price, taxedPrice, stock);
    }
}
