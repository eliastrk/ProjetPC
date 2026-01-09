import java.math.BigDecimal;
import java.util.Objects;

public class Dish {
    private final String name;
    private final BigDecimal price;
    private final DishSize size;

    public Dish(String name, BigDecimal price, DishSize size) {
        this.name = name;
        this.price = price;
        this.size = size;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public DishSize getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        return "Dish{" + "name=" + name + ", price=" + price + ", Size=" + size + '}';
    }

    public int hashCode() {
        return Objects.hash(this.getName(), this.getPrice(), this.getSize());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish d = (Dish) o;
        return Objects.equals(price, d.price) && Objects.equals(name, d.name) && Objects.equals(size, d.size);
    }
}
