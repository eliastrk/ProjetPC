import customer.Customer;
import delivery.DeliveryPlatform;
import dish.Dish;
import dish.DishSize;
import order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DeliveryPlatformTest {

    @Test
    public void testPlaceOrderAndFindById() {
        DeliveryPlatform platform = new DeliveryPlatform();

        Customer c = new Customer("Alice", "Paris");
        Dish d = new Dish("Burger", new BigDecimal("8.50"), DishSize.MEDIUM);

        Map<Dish, Integer> dishes = new HashMap<>();
        dishes.put(d, 1);

        Order o = new Order(dishes, c);

        String deliveryId = platform.placeOrder(o).orElse("");
        Assertions.assertFalse(deliveryId.isEmpty());

        Assertions.assertTrue(platform.findOrderById(deliveryId).isPresent());
        Assertions.assertEquals(c, platform.findOrderById(deliveryId).get().getCustomer());
    }

    @Test
    public void testFindOrdersByCustomer() {
        DeliveryPlatform platform = new DeliveryPlatform();

        Customer c1 = new Customer("Alice", "Paris");
        Customer c2 = new Customer("Bob", "Lyon");

        Dish d = new Dish("Frites", new BigDecimal("4.20"), DishSize.SMALL);

        Map<Dish, Integer> dishes = new HashMap<>();
        dishes.put(d, 1);

        // On place plusieurs commandes pour être sûr d'en avoir pour chaque customer
        platform.placeOrder(new Order(dishes, c1));
        platform.placeOrder(new Order(dishes, c1));
        platform.placeOrder(new Order(dishes, c2));

        Assertions.assertEquals(2, platform.findOrdersByCustomer(c1).size());
        Assertions.assertEquals(1, platform.findOrdersByCustomer(c2).size());
    }
}
