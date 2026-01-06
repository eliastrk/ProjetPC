import customer.Customer;
import delivery.DeliveryPlatform;
import dish.Dish;
import dish.DishSize;
import order.Order;
import order.OrderStatus;
import restaurant.Restaurant;

import java.math.BigDecimal;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        Restaurant r1 = new Restaurant("Les Saveurs du Nord", "Lille");
        Restaurant r2 = new Restaurant("Pepe Chicken", "Noisy-le-Grand");
        Restaurant r3 = new Restaurant("Orgueil", "Paris");

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

        DeliveryPlatform platform = new DeliveryPlatform();

        String r1Id = platform.signInRestaurant(r1);
        String r2Id = platform.signInRestaurant(r2);
        String r3Id = platform.signInRestaurant(r3);

        platform.placeOrder(r1Id, o1);
        platform.placeOrder(r3Id, o2);
        platform.placeOrder(r2Id, o3);
        platform.placeOrder(r1Id, o4);
        platform.placeOrder(r2Id, o5);


        System.out.println("Orders by ID");
        System.out.println("=========");
        System.out.println(platform.findOrderById(r1Id).get());
        System.out.println(platform.findOrderById(r3Id).get());
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
