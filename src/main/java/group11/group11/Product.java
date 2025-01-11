package group11.group11;

public class Product
{
    private int productId;
    private String name;
    private double price;
    private double taxedPrice;
    private int stock;

    // Constructor for all fields
    public Product(int productId, String name, double price, int stock)
    {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.taxedPrice = calculateTaxedPrice(price); // Calculate taxed price
    }
    public Product(String name, double price, int stock)
    {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Method to calculate taxed price (10% tax)
    private double calculateTaxedPrice(double price) {
        double taxRate = 0.10; // 10% tax
        return price * (1 + taxRate); // Price + 10% of price
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
        this.taxedPrice = calculateTaxedPrice(price); // Recalculate taxed price
    }

    public double getTaxedPrice() {
        return taxedPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("Product{product_id=%d, name='%s', price=%.2f, taxedPrice=%.2f, stock=%d}",
                productId, name, price, taxedPrice, stock);
    }
}