package group11.group11;

public class Product {
    private int productId;
    private String name;
    private double price;
    private double taxedPrice;
    private int stock;
    private int quantity;

    // Constructor for all fields
    public Product(int productId, String name, double price, double taxedPrice, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.taxedPrice = taxedPrice;
        this.stock = stock;
    }

    // Constructor for name, price, and stock
    public Product(String name, double taxedPrice, int quantity) {
        this.name = name;
        this.taxedPrice = taxedPrice;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

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

    public double getTaxedPrice() {
        return taxedPrice;
    }

    public void setTaxedPrice(double taxedPrice) {
        this.taxedPrice = taxedPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getQuantity() {return this.quantity;}

    public void setQuantity(int quantity)
    {
        this.quantity=quantity;
    }

    @Override
    public String toString() {
        return String.format("Product{product_id=%d, name='%s', price=%.2f, taxedPrice=%.2f, stock=%d}",
                productId, name, price, taxedPrice, stock);
    }
}