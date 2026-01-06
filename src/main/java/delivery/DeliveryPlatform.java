package delivery;

import customer.Customer;
import exceptions.OrderPreparationException;
import order.Order;
import order.OrderStatus;
import restaurant.Restaurant;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Represents a Delivery Platform for a restaurant.
 * This class stores orders and provides methods to get commands
 * from a specific criteria (id, customer, order status).
 */
public class DeliveryPlatform {
    /**
     * The unique Map that stores orders with a random UUID rendered
     * to a String as key.
     */
    private final ConcurrentHashMap<String, Order> orders;

    public DeliveryPlatform() {
        this.orders = new ConcurrentHashMap<>();
    }

    /**
     * Simulates the placement of an order to send to the Restaurant to
     * get it prepared.
     * The order preparation can fail so its status is passed to CANCELLED.
     * If the preparation succeed, the status is passed to IN_PREPARATION.
     * @see Restaurant
     * @param order the order to simulate the placement in the platform.
     * @return an Optional object containing the generated order UUID if the preparation succeed.
     */
    public synchronized Optional<String> placeOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        try {
            Restaurant.prepare(order);
            order.setStatus(OrderStatus.IN_PREPARATION);
        } catch (OrderPreparationException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
            order.setStatus(OrderStatus.CANCELLED);
        } finally {
            String deliveryOrderId = UUID.randomUUID().toString();
            this.orders.putIfAbsent(deliveryOrderId,order);
            return Optional.of(deliveryOrderId);
        }
    }

    /**
     * Returns an Optional containing the Order matching the specified ID
     * if present in the orders Map.
     * @param orderId the ID to look for inside the Map.
     * @return an Optional containing the Order
     */
    public Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(this.orders.get(orderId));
    }

    /**
     * Returns all the orders made by the specified customer.
     * @param customer the customer to look for in the orders.
     * @return a List of Order.
     */
    public List<Order> findOrdersByCustomer(Customer customer) {
        return this.orders.values().stream()
                .filter(order -> order.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    /**
     * Returns all the orders matching the specified status.
     * @param status the status to look for in the orders.
     * @return a List of Order.
     */
    public List<Order> findOrderByStatus(OrderStatus status) {
        return this.orders.values().stream()
                .filter(order -> order.getStatus().equals(status))
                .collect(Collectors.toList());
    }
}
