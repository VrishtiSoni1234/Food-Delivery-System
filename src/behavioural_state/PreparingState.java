package behavioural_state;

import creational_factory.Order;

public class PreparingState implements OrderState {

    @Override
    public void next(Order order) {
        order.setState(new OutForDeliveryState());
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CancelledState());
    }

    @Override
    public String getName() {
        return "PREPARING";
    }

    @Override
    public String getEvent(Order order) {
        return "ORDER_PREPARING";
    }
}
