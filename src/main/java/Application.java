import customer.Customer;
import delivery.DeliveryPlatform;
import dish.Dish;
import dish.DishSize;
import order.Order;
import order.OrderStatus;
import restaurant.Restaurant;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {

        Customer c1 = new Customer("Matthieu Herbaut","Lens");
        Customer c2 = new Customer("Jean-Jacques Goldman", "Paris");
        Customer c3 = new Customer("Marion Cotillard", "Le Touquet");
        Customer c4 = new Customer("Michèle Obama", "Washington D.C.");

        Dish d1 = new Dish("Cheese Naan Kebab", new BigDecimal(8.5), DishSize.MEDIUM);
        Dish d2 = new Dish("Sandwich Américain", new BigDecimal(6.8), DishSize.LARGE);
        Dish d3 = new Dish("Frites au cheddar", new BigDecimal(4.2), DishSize.SMALL);

        Order o1 = new Order(Map.of(d1,1),c2);
        o1.setStatus(OrderStatus.CANCELLED);
        Order o2 = new Order(Map.of(d3,3),c4);
        o2.setStatus(OrderStatus.IN_PREPARATION);
        Order o3 = new Order(Map.of(d2,2),c1);
        o3.setStatus(OrderStatus.COMPLETED);
        Order o4 = new Order(Map.of(d2,4),c3);
        o4.setStatus(OrderStatus.CANCELLED);
        Order o5 = new Order(Map.of(d3,1),c4);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        DeliveryPlatform platform = new DeliveryPlatform();
        System.out.println("=========");
        System.out.println("Placing orders to the platform");
        Future<String> do1IdF = executor.submit(() -> platform.placeOrder(o1).orElse(""));
        Future<String> do2IdF = executor.submit(()->platform.placeOrder(o2).orElse(""));
        Future<String> do3IdF = executor.submit(()->platform.placeOrder(o3).orElse(""));
        Future<String> do4IdF = executor.submit(()->platform.placeOrder(o4).orElse(""));
        Future<String> do5IdF = executor.submit(()->platform.placeOrder(o5).orElse(""));
        System.out.println();

        executor.shutdown();

        String do1Id;
        String do2Id;
        String do3Id;
        String do4Id;
        String do5Id;

        try {
            do1Id = do1IdF.get();
            do2Id = do2IdF.get();
            do3Id = do3IdF.get();
            do4Id = do4IdF.get();
            do5Id = do5IdF.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        for (String deliveryId : new String[]{do1Id, do2Id, do3Id, do4Id, do5Id}) {
            if (deliveryId != null && !deliveryId.isBlank()) {
                //System.out.println("Saving order with deliveryId = " + deliveryId);
                try {
                    platform.saveOrderToDatabase(deliveryId);
                } catch (SQLException e) {
                    System.err.println("Failed to save order to DB (deliveryId=" + deliveryId + "): " + e.getMessage());
                }
            }
        }

        System.out.println("Orders by ID");
        System.out.println("=========");
        Order resultOrder1 = platform.findOrderById(do1Id).orElse(null);
        if (Objects.nonNull(resultOrder1)) {
            System.out.println(resultOrder1);
        }
        Order resultOrder2 = platform.findOrderById(do3Id).orElse(null);
        if (Objects.nonNull(resultOrder2)) {
            System.out.println(resultOrder2);
        }
        System.out.println();

        System.out.println("Orders by status");
        System.out.println("=========");
        platform.findOrderByStatus(OrderStatus.PENDING).stream().forEach(System.out::println);
        platform.findOrderByStatus(OrderStatus.IN_PREPARATION).stream().forEach(System.out::println);
        platform.findOrderByStatus(OrderStatus.COMPLETED).stream().forEach(System.out::println);
        platform.findOrderByStatus(OrderStatus.CANCELLED).stream().forEach(System.out::println);
        System.out.println();

        System.out.println("Order by Customer");
        System.out.println("=========");
        platform.findOrdersByCustomer(c4).stream().forEach(System.out::println);
        System.out.println();

    }
}
