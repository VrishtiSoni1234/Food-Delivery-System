package creational_factory;

public class DeliveryOrderFactory extends OrderFactory {
    @Override
    public Order createOrder(String customerName, Restaurant restaurant) {
        return new DeliveryOrder(customerName, restaurant);
    }
}
