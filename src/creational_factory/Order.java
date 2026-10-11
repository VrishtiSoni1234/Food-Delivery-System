package creational_factory;

import behavioural_observer.IObserver;
import behavioural_observer.ISubject;
import behavioural_state.OrderState;
import behavioural_state.PlacedState;
import behavioural_strategy.PaymentStrategy;
import model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public abstract class Order implements ISubject {

    private static int idCounter = 1000;

    protected final int id;
    protected final String customerName;
    protected final Restaurant restaurant;
    protected final List<MenuItem> items = new ArrayList<>();

    private final List<IObserver> observers = new ArrayList<>();
    private OrderState state = new PlacedState();
    private PaymentStrategy paymentStrategy;
    private boolean paid = false;

    public Order(String customerName, Restaurant restaurant) {
        this.id = ++idCounter;
        this.customerName = customerName;
        this.restaurant = restaurant;
    }

    // ---------- Factory products differ in these ----------
    public abstract String getOrderType();
    public abstract double getDeliveryFee();
    public abstract String getEta();
    public abstract String getDispatchEvent();
    public abstract boolean requiresDeliveryPartner();

    // ---------- Order details ----------
    public int getId() {
        return id;
    }

    public String getSubjectName() {
        return "Order #" + id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void addItem(MenuItem item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            items.add(item);
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    public double getTotal() {
        return getSubtotal() + getDeliveryFee();
    }

    public boolean isPaid() {
        return paid;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public String getStatus() {
        return state.getName();
    }

    // ---------- Strategy Pattern: payment is delegated ----------
    public boolean checkout(PaymentStrategy strategy) {
        if (isEmpty()) {
            System.out.println("  [Order] Cannot checkout an empty order.");
            return false;
        }
        if (paid) {
            System.out.println("  [Order] This order is already paid.");
            return false;
        }
        System.out.printf("  [Order] Paying Rs. %.2f using %s...%n", getTotal(), strategy.getName());
        if (strategy.pay(getTotal())) {
            this.paymentStrategy = strategy;
            this.paid = true;
            notifyObservers("PAYMENT_SUCCESS");
            return true;
        }
        System.out.println("  [Order] Payment failed. Please try another method.");
        return false;
    }

    // ---------- State Pattern: lifecycle is delegated ----------
    public void next() {
        state.next(this);
    }

    public void cancel() {
        state.cancel(this);
        if (getStatus().equals("CANCELLED") && paid && paymentStrategy.isPrepaid()) {
            System.out.printf("  [Order] Refund of Rs. %.2f initiated to %s.%n", getTotal(), paymentStrategy.getName());
        }
    }

    public void setState(OrderState newState) {
        System.out.printf("  [State] %s: %s -> %s%n", getSubjectName(), state.getName(), newState.getName());
        this.state = newState;
        if (newState.getName().equals("DELIVERED") && paymentStrategy != null && !paymentStrategy.isPrepaid()) {
            System.out.printf("  [Order] Cash of Rs. %.2f collected on delivery.%n", getTotal());
        }
        notifyObservers(newState.getEvent(this));
    }

    // ---------- Observer Pattern: Order is the subject ----------
    @Override
    public void attach(IObserver observer) {
        observers.add(observer);
        System.out.printf("  [Subject] '%s' is now observing %s.%n", observer.getName(), getSubjectName());
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
        System.out.printf("  [Subject] '%s' stopped observing %s.%n", observer.getName(), getSubjectName());
    }

    @Override
    public void notifyObservers(String event) {
        for (IObserver observer : new ArrayList<>(observers)) {
            observer.update(getSubjectName(), event);
        }
    }

    public void printSummary() {
        System.out.println("  ------------------------------------------");
        System.out.printf("  %s | %s | %s%n", getSubjectName(), getOrderType(), restaurant.getName());
        System.out.printf("  Customer : %s%n", customerName);
        for (MenuItem item : items) {
            System.out.printf("    - %s%n", item);
        }
        System.out.printf("  Subtotal : Rs. %.2f%n", getSubtotal());
        System.out.printf("  Fee      : Rs. %.2f%n", getDeliveryFee());
        System.out.printf("  Total    : Rs. %.2f%n", getTotal());
        System.out.printf("  ETA      : %s%n", getEta());
        System.out.printf("  Payment  : %s%n", paymentStrategy == null ? "Pending" : paymentStrategy.getName());
        System.out.printf("  Status   : %s%n", getStatus());
        System.out.println("  ------------------------------------------");
    }
}
