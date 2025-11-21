import java.util.*;

public class DeliveryPlatform {

    private Map<String, Order> map;

    public DeliveryPlatform(Map<String, Order> map) {
        this.map = new HashMap<>();
    }

    public Map<String, Order> getMap() {
        return this.map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryPlatform d = (DeliveryPlatform) o;
        return Objects.equals(map, d.map);
    }

    public void placeOrder(Order order){
        this.getMap().put(UUID.randomUUID().toString(),order);
    }

    public Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(this.getMap().get(orderId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
