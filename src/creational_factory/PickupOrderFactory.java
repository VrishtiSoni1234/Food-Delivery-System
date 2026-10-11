package creational_factory;

public class PickupOrderFactory extends OrderFactory {
    @Override
    public Order createOrder(String customerName, Restaurant restaurant) {
        return new PickupOrder(customerName, restaurant);
    }
}
