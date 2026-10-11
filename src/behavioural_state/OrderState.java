package behavioural_state;

import creational_factory.Order;

public interface OrderState {

    void next(Order order);

    void cancel(Order order);

    String getName();

    // Event code broadcast to observers when the order enters this state
    String getEvent(Order order);
}
