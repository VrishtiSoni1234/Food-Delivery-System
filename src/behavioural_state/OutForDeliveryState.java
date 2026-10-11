package behavioural_state;

import creational_factory.Order;

public class OutForDeliveryState implements OrderState {

    @Override
    public void next(Order order) {
        order.setState(new DeliveredState());
    }

    @Override
    public void cancel(Order order) {
        System.out.println("  [State] Too late to cancel. The order has already left the restaurant.");
    }

    @Override
    public String getName() {
        return "OUT_FOR_DELIVERY";
    }

    @Override
    public String getEvent(Order order) {
        return order.getDispatchEvent();
    }
}
