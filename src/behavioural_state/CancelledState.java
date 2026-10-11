package behavioural_state;

import creational_factory.Order;

public class CancelledState implements OrderState {

    @Override
    public void next(Order order) {
        System.out.println("  [State] Order is cancelled. It cannot move forward.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("  [State] Order is already cancelled.");
    }

    @Override
    public String getName() {
        return "CANCELLED";
    }

    @Override
    public String getEvent(Order order) {
        return "ORDER_CANCELLED";
    }
}
