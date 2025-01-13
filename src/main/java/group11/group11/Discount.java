/**
 * Represents a discount based on age range and discount rate.
 */
package group11.group11;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Discount {
    private final SimpleIntegerProperty minAge;
    private final SimpleIntegerProperty maxAge;
    private final SimpleDoubleProperty discountRate;

    /**
     * Constructs a Discount object with the specified age range and discount rate.
     *
     * @param minAge       The minimum age for the discount.
     * @param maxAge       The maximum age for the discount.
     * @param discountRate The discount rate to be applied.
     */
    public Discount(int minAge, int maxAge, double discountRate) {
        this.minAge = new SimpleIntegerProperty(minAge);
        this.maxAge = new SimpleIntegerProperty(maxAge);
        this.discountRate = new SimpleDoubleProperty(discountRate);
    }

    /**
     * Returns the minimum age for the discount.
     *
     * @return The minimum age.
     */
    public int getMinAge() {
        return minAge.get();
    }

    /**
     * Sets the minimum age for the discount.
     *
     * @param minAge The minimum age to set.
     */
    public void setMinAge(int minAge) {
        this.minAge.set(minAge);
    }

    /**
     * Returns the maximum age for the discount.
     *
     * @return The maximum age.
     */
    public int getMaxAge() {
        return maxAge.get();
    }

    /**
     * Sets the maximum age for the discount.
     *
     * @param maxAge The maximum age to set.
     */
    public void setMaxAge(int maxAge) {
        this.maxAge.set(maxAge);
    }

    /**
     * Returns the discount rate.
     *
     * @return The discount rate.
     */
    public double getDiscountRate() {
        return discountRate.get();
    }

    /**
     * Sets the discount rate.
     *
     * @param discountRate The discount rate to set.
     */
    public void setDiscountRate(double discountRate) {
        this.discountRate.set(discountRate);
    }
}
