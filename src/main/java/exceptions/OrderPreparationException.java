package exceptions;

/**
 * The Exception to throw when an order preparation failed.
 */
public class OrderPreparationException extends Exception{
    public OrderPreparationException(String message) {
        super(message);
    }
}
