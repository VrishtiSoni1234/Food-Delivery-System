package creational_factory;

public class ExpressOrderFactory extends OrderFactory {
    @Override
    public Order createOrder(String customerName, Restaurant restaurant) {
        return new ExpressOrder(customerName, restaurant);
    }
}
