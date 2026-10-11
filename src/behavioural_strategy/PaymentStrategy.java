package behavioural_strategy;

public interface PaymentStrategy {

    boolean pay(double amount);

    String getName();

    // true if money is collected at checkout, false if collected later (e.g. Cash on Delivery)
    boolean isPrepaid();
}
