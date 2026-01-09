package order;

import customer.Customer;
import dish.Dish;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.io.Serializable;

public class Order implements Serializable {
    private final String id;
    private OrderStatus status;
    private final Map<Dish, Integer> dishes;
    private final Customer customer;
    private final LocalDateTime orderDate;
    private static final long serialVersionUID = 1L;

    public Order(Map<Dish, Integer> dishes, Customer customer) {
        this.id = UUID.randomUUID().toString();
        this.dishes = dishes;
        this.customer = customer;
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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

    public BigDecimal calculateTotalPrice() {
        BigDecimal total = new BigDecimal(0);
        
        for (Map.Entry<Dish, Integer> entry : this.dishes.entrySet()) {
            BigDecimal unitPrice = entry.getKey().getPrice();
            BigDecimal quantity = BigDecimal.valueOf(entry.getValue());
            total = total.add(unitPrice.multiply(quantity));
        }

        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order order)) return false;
        return Objects.equals(id, order.id) && status == order.status && Objects.equals(dishes, order.dishes) && Objects.equals(customer, order.customer) && Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, dishes, customer, orderDate);
    }

    @Override
    public String toString() {
        return "order.Order{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", dishes=" + dishes +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                '}';
    }
}
