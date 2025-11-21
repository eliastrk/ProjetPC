import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DeliveryPlatform {
    private final Map<String,Order> orders;

    public DeliveryPlatform() {
        this.orders = new HashMap<>();
    }

    void placeOrder(Order order) {

    }

    Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(this.orders.get(orderId));
    }
}
