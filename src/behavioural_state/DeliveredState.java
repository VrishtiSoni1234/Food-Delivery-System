package behavioural_state;

import creational_factory.Order;

public class DeliveredState implements OrderState {

    @Override
    public void next(Order order) {
        System.out.println("  [State] Order is already delivered. Nothing more to do.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("  [State] A delivered order cannot be cancelled.");
    }

    @Override
    public String getName() {
        return "DELIVERED";
    }

    @Override
    public String getEvent(Order order) {
        return "ORDER_DELIVERED";
    }
}
