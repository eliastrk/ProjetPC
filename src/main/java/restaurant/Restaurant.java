package restaurant;

import exceptions.OrderPreparationException;
import order.Order;
import order.OrderStatus;

import java.util.Random;

/**
 * Represents a unique restaurant referenced in the delivery platform.
 * This class has a unique name and address as String attributes.
 */
public class Restaurant {

    /**
     * The unique name of the restaurant.
     */
    private static final String name = "Tasty Crousty";

    /**
     * The unique address of the restaurant.
     */
    private static final String address = "45, Avenue du Poulet Braisé 75001 Paris";

    public static String getName() {
        return name;
    }

    public static String getAddress() {
        return address;
    }

    /**
     * Simulates the preparation of and order with a 1/5 chance to fail.
     * In that case, throws an Exception.
     * @param order the order to simulate the preparation.
     * @throws OrderPreparationException if case the order failed to be prepared.
     */
    public static void prepare(Order order) throws OrderPreparationException {
        Integer r = new Random().nextInt(5);
        if (r == 1) {
            throw new OrderPreparationException("The order number " + order.getId() + " could not be prepared");
        }
        order.setStatus(OrderStatus.PENDING);
    }
}
