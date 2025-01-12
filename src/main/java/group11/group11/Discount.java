package group11.group11;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Discount {
    private final SimpleIntegerProperty minAge;
    private final SimpleIntegerProperty maxAge;
    private final SimpleDoubleProperty discountRate;

    public Discount(int minAge, int maxAge, double discountRate) {
        this.minAge = new SimpleIntegerProperty(minAge);
        this.maxAge = new SimpleIntegerProperty(maxAge);
        this.discountRate = new SimpleDoubleProperty(discountRate);
    }

    public int getMinAge() {
        return minAge.get();
    }

    public void setMinAge(int minAge) {
        this.minAge.set(minAge);
    }

    public int getMaxAge() {
        return maxAge.get();
    }

    public void setMaxAge(int maxAge) {
        this.maxAge.set(maxAge);
    }

    public double getDiscountRate() {
        return discountRate.get();
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate.set(discountRate);
    }
}