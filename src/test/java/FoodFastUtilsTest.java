import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.FoodFastUtils;

public class FoodFastUtilsTest {

    @Test
    public void testDeliveryPlanner(){
        int n1 = 30;
        int n2 = 9;
        int n3 = 10;
        int n4 = 8;

        Assertions.assertEquals("FizzBuzz", FoodFastUtils.deliveryPlanner(n1));
        Assertions.assertEquals("Fizz", FoodFastUtils.deliveryPlanner(n2));
        Assertions.assertEquals("Buzz", FoodFastUtils.deliveryPlanner(n3));
        Assertions.assertEquals("", FoodFastUtils.deliveryPlanner(n4));
    }

    @Test
    public void testIsLeapYear(){
        int y1 = 2004;
        int y2 = 2400;
        int y3 = 2100;
        int y4 = 2003;

        Assertions.assertTrue(FoodFastUtils.isLeapYear(y1));
        Assertions.assertTrue(FoodFastUtils.isLeapYear(y2));
        Assertions.assertFalse(FoodFastUtils.isLeapYear(y3));
        Assertions.assertFalse(FoodFastUtils.isLeapYear(y4));
    }

    @Test
    public void testSumUpTo(){
        int n1 = 0;
        int n2 = 5;

        Assertions.assertEquals(0, FoodFastUtils.sumUpTo(n1));
        Assertions.assertEquals(15, FoodFastUtils.sumUpTo(n2));
    }

    @Test
    public void anonymize(){
        String str1 = "";
        String str2 = "KayaK";
        String str3 = "Salut";

        Assertions.assertEquals("", FoodFastUtils.anonymize(str1));
        Assertions.assertEquals("KayaK", FoodFastUtils.anonymize(str2));
        Assertions.assertEquals("tulaS", FoodFastUtils.anonymize(str3));
    }
}
