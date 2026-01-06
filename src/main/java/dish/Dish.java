package dish;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a Dish that is stored in an order.
 * It has a name, a price and a size.
 */
public class Dish {
    /**
     * The name of the dish.
     */
    private final String name;
    /**
     * The price of the dish.
     */
    private BigDecimal price;
    /**
     * The size of the dish.
     */
    private DishSize size;

    public Dish(String name, BigDecimal price, DishSize size) {
        this.name = name;
        this.price = price;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public DishSize getSize() {
        return size;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSize(DishSize size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Dish dish)) return false;
        return Objects.equals(name, dish.name) && Objects.equals(price, dish.price) && size == dish.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, size);
    }

    @Override
    public String toString() {
        return "dish.Dish{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", size=" + size +
                '}';
    }
}
