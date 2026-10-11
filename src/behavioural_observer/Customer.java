package behavioural_observer;

public class Customer implements IObserver {

    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return "Customer " + name;
    }

    @Override
    public void update(String subjectName, String event) {
        String output = switch (event) {
            case "ORDER_PLACED"           -> "Your order has been placed successfully.";
            case "PAYMENT_SUCCESS"        -> "Payment confirmed. Thank you!";
            case "ORDER_PREPARING"        -> "The restaurant is preparing your food.";
            case "ORDER_OUT_FOR_DELIVERY" -> "Your order is out for delivery!";
            case "ORDER_READY_FOR_PICKUP" -> "Your order is ready for pickup!";
            case "ORDER_DELIVERED"        -> "Order completed. Enjoy your meal!";
            case "ORDER_CANCELLED"        -> "Your order has been cancelled.";
            default -> null;
        };
        if (output != null) {
            System.out.printf("  [Notification -> %s] %s: %s%n", getName(), subjectName, output);
        }
    }
}
