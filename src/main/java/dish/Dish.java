package dish;

import java.math.BigDecimal;
import java.util.Objects;

public class Dish {
    private final String name;
    private BigDecimal price;
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
