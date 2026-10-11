package behavioural_observer;

public class RestaurantStaff implements IObserver {

    private final String restaurantName;

    public RestaurantStaff(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public String getName() {
        return restaurantName + " Kitchen";
    }

    @Override
    public void update(String subjectName, String event) {
        String output = switch (event) {
            case "PAYMENT_SUCCESS"        -> "New confirmed order received. Get ready to cook!";
            case "ORDER_PREPARING"        -> "Cooking started.";
            case "ORDER_OUT_FOR_DELIVERY",
                 "ORDER_READY_FOR_PICKUP" -> "Order handed over. Kitchen is clear.";
            case "ORDER_CANCELLED"        -> "Order cancelled. Stop preparation.";
            default -> null;
        };
        if (output != null) {
            System.out.printf("  [Notification -> %s] %s: %s%n", getName(), subjectName, output);
        }
    }
}
