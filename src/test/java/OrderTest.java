import customer.Customer;
import dish.Dish;
import dish.DishSize;
import order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrderTest {

    @Test
    public void testCalculateTotalPriceSingleDish() {
        Customer c = new Customer("Daoud", "Baskinta");
        Dish d = new Dish("Couscous", new BigDecimal("8.50"), DishSize.MEDIUM);

        Map<Dish, Integer> dishes = new HashMap<>();
        dishes.put(d, 2);

        Order o = new Order(dishes, c);

        Assertions.assertEquals(new BigDecimal("17.00"), o.calculateTotalPrice());
    }

    @Test
    public void testCalculateTotalPriceMultipleDishes() {
        Customer c = new Customer("Matheo", "Amsterdam");

        Dish d1 = new Dish("Naan", new BigDecimal("8.50"), DishSize.MEDIUM);
        Dish d2 = new Dish("Frites", new BigDecimal("4.20"), DishSize.SMALL);

        Map<Dish, Integer> dishes = new HashMap<>();
        dishes.put(d1, 1);
        dishes.put(d2, 3);

        Order o = new Order(dishes, c);

        Assertions.assertEquals(new BigDecimal("21.10"), o.calculateTotalPrice());
    }
}
