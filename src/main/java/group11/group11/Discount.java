package group11.group11;

public class Discount {
    private int minAge;
    private int maxAge;
    private double discountRate;

    public Discount(int minAge, int maxAge, double discountRate) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.discountRate = discountRate;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", discountRate=" + discountRate +
                '}';
    }
}
