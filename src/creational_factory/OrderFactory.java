package creational_factory;

public abstract class OrderFactory {

    public abstract Order createOrder(String customerName, Restaurant restaurant);

    public Order placeOrder(String customerName, Restaurant restaurant) {
        System.out.printf("%n[FactoryMethod] Using %s...%n", this.getClass().getSimpleName());
        Order order = createOrder(customerName, restaurant);
        System.out.printf("[FactoryMethod] Created %s (%s).%n", order.getSubjectName(), order.getOrderType());
        return order;
    }
}
