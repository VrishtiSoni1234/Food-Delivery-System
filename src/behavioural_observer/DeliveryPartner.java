package behavioural_observer;

public class DeliveryPartner implements IObserver {

    private final String name;

    public DeliveryPartner(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return "Delivery Partner " + name;
    }

    @Override
    public void update(String subjectName, String event) {
        String output = switch (event) {
            case "ORDER_PREPARING"        -> "Head to the restaurant, the order will be ready soon.";
            case "ORDER_OUT_FOR_DELIVERY" -> "Order picked up. Navigating to the customer.";
            case "ORDER_DELIVERED"        -> "Delivery complete. You are free for the next order.";
            case "ORDER_CANCELLED"        -> "Order cancelled. Trip aborted.";
            default -> null;
        };
        if (output != null) {
            System.out.printf("  [Notification -> %s] %s: %s%n", getName(), subjectName, output);
        }
    }
}
