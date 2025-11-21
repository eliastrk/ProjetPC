import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final String id;
    private OrderStatus status;
    private Map<Dish, Integer> dishes;
    private Customer customer;
    private LocalDateTime orderDate;

    public Order(Customer customer, LocalDateTime orderDate) {
        this.id = UUID.randomUUID().toString();
        this.status = OrderStatus.PENDING;
        this.dishes = new HashMap<>();
        this.customer = customer;
        this.orderDate = orderDate;
    }

    public String getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addDish(Dish dish) {
        this.getDishes().put(dish, dishes.getOrDefault(dish, 0) + 1);
    }

    public void removeDish(Dish dish) {
        dishes.remove(dish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus(), getDishes(), getCustomer(), getOrderDate());
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal somme = new BigDecimal(0);
        for (Map.Entry<Dish,Integer> entry : this.getDishes().entrySet()) {
            somme = somme.add(entry.getKey().getPrice()).multiply(new BigDecimal(entry.getValue()));
        }
        return somme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order d = (Order) o;
        return Objects.equals(id, d.id) && Objects.equals(status, d.status) && Objects.equals(dishes, d.dishes) && Objects.equals(customer, d.customer) && Objects.equals(orderDate, d.orderDate);
    }

    public String toString() {
        return "Order{" + "id=" + this.getId() + ", status=" + this.getStatus() + ", dishes=" + this.getDishes() + ", customer=" + this.getCustomer() + ", orderDate=" + this.getOrderDate() + '}';
    }



}