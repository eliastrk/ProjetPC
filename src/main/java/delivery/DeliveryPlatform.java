package delivery;

import customer.Customer;
import exceptions.OrderPreparationException;
import order.Order;
import order.OrderStatus;
import restaurant.Restaurant;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveryPlatform {
    private final Map<String,Order> orders;
    private final Map<String, Restaurant> restaurants;

    public DeliveryPlatform() {
        this.orders = new HashMap<>();
        this.restaurants = new HashMap<>();
    }

    public String signInRestaurant(Restaurant restaurant) {
        String uuid = UUID.randomUUID().toString();
        this.restaurants.put(uuid,restaurant);
        return uuid;
    }

    public void placeOrder(String restaurantId, Order order) {
        try {
            Restaurant restaurant = this.restaurants.get(restaurantId);
            restaurant.prepareOrder(order);
            this.orders.put(restaurantId,order);
        } catch (OrderPreparationException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            order.setStatus(OrderStatus.CANCELLED);
        }

    }

    public Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(this.orders.get(orderId));
    }

    public List<Order> findOrdersByCustomer(Customer customer) {
        return this.orders.values().stream()
                .filter(order -> order.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    public List<Order> findOrderByStatus(OrderStatus status) {
        return this.orders.values().stream()
                .filter(order -> order.getStatus().equals(status))
                .collect(Collectors.toList());
    }
}
