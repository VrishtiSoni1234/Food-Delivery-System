package behavioural_state;

import creational_factory.Order;

public class PlacedState implements OrderState {

    @Override
    public void next(Order order) {
        if (!order.isPaid()) {
            System.out.println("  [State] Cannot start preparing: payment not completed yet.");
            return;
        }
        order.setState(new PreparingState());
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CancelledState());
    }

    @Override
    public String getName() {
        return "PLACED";
    }

    @Override
    public String getEvent(Order order) {
        return "ORDER_PLACED";
    }
}
