import dish.Dish;
import dish.DishSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class DishTest {

    @Test
    public void testDishCreation() {
        Dish d1 = new Dish("Pâtes au pesto", new BigDecimal(4), DishSize.SMALL);

        Assertions.assertEquals("Pâtes au pesto", d1.getName());
        Assertions.assertEquals(new BigDecimal(4), d1.getPrice());
        Assertions.assertEquals(DishSize.SMALL, d1.getSize());
    }

    @Test
    public void testDishModification() {
        Dish d1 = new Dish("Cheese Naan Kebab", new BigDecimal(8), DishSize.LARGE);

        d1.setSize(DishSize.SMALL);
        Assertions.assertEquals(DishSize.SMALL, d1.getSize());

        d1.setPrice(new BigDecimal(10));
        Assertions.assertEquals(new BigDecimal(10), d1.getPrice());
    }
}
