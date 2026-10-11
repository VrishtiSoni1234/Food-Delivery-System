package behavioural_strategy;

public class CashOnDeliveryPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount) {
        System.out.printf("  [Payment] Cash on Delivery selected. Please pay Rs. %.2f at the doorstep.%n", amount);
        return true;
    }

    @Override
    public String getName() {
        return "Cash on Delivery";
    }

    @Override
    public boolean isPrepaid() {
        return false;
    }
}
