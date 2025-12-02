package restaurant;

import exceptions.OrderPreparationException;
import order.Order;
import order.OrderStatus;

import java.util.Random;

public class Restaurant {
    private final String name;
    private final String address;

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void prepareOrder(Order order) throws OrderPreparationException {
        Integer r = new Random().nextInt(5);
        if (r == 1) {
            throw new OrderPreparationException("La commande n'as pas pu être préparée");
        }
        order.setStatus(OrderStatus.IN_PREPARATION);
    }
}
