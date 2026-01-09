package delivery;

import customer.Customer;
import exceptions.OrderPreparationException;
import order.Order;
import order.OrderStatus;
import restaurant.Restaurant;
import logger.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.io.*;
import java.nio.file.*;

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

    private static final String DB_URL  = System.getenv()
        .getOrDefault("FOODFAST_DB_URL", "jdbc:postgresql://localhost:5432/foodfast");

    private static final String DB_USER = System.getenv()
        .getOrDefault("FOODFAST_DB_USER", "postgres");

    private static final String DB_PASS = System.getenv()
        .getOrDefault("FOODFAST_DB_PASS", "postgres");

    private transient Logger logger = Logger.getInstance();




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
            logger.error("La préparation de la commande a échoué : " + e.getMessage());
            //e.printStackTrace();
            order.setStatus(OrderStatus.CANCELLED);
        } finally {
            String deliveryOrderId = UUID.randomUUID().toString();
            this.orders.putIfAbsent(deliveryOrderId,order);
            logger.info("La commande a été créé. deliveryId=" + deliveryOrderId + ", status=" + order.getStatus());
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


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }


    public void saveOrderToDatabase(String deliveryId) throws SQLException {
        Order order = this.orders.get(deliveryId);
        if (order == null) {
            throw new IllegalArgumentException("Unknown deliveryId: " + deliveryId);
        }

        String sql = """
            INSERT INTO orders (
                delivery_id, order_id,
                customer_id, customer_name, customer_addr,
                status, order_date, total_price
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT (delivery_id) DO NOTHING
            """;

        try (Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, deliveryId);
            ps.setString(2, order.getId());
            ps.setString(3, order.getCustomer().getId());
            ps.setString(4, order.getCustomer().getName());
            ps.setString(5, order.getCustomer().getAddress());
            ps.setString(6, order.getStatus().name());
            ps.setTimestamp(7, Timestamp.valueOf(order.getOrderDate()));
            ps.setBigDecimal(8, order.calculateTotalPrice());

            ps.executeUpdate();
        }
    }

    public void savePlatformState(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(this.orders);
        }
    }

    public void loadPlatformState(String filePath) throws IOException, ClassNotFoundException {
        Path path = Paths.get(filePath);

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            Object obj = ois.readObject();
            @SuppressWarnings("unchecked")
            ConcurrentHashMap<String, Order> loaded = (ConcurrentHashMap<String, Order>) obj;

            this.orders.clear();
            this.orders.putAll(loaded);
        }

        this.logger = Logger.getInstance();
    }

}
