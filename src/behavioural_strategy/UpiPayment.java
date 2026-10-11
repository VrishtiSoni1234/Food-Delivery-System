package behavioural_strategy;

public class UpiPayment implements PaymentStrategy {

    private final String upiId;

    public UpiPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean pay(double amount) {
        if (upiId == null || !upiId.matches("^[\\w.\\-]{2,}@[a-zA-Z]{2,}$")) {
            System.out.println("  [Payment] Invalid UPI ID: " + upiId);
            return false;
        }
        System.out.printf("  [Payment] Rs. %.2f paid via UPI (%s).%n", amount, upiId);
        return true;
    }

    @Override
    public String getName() {
        return "UPI";
    }

    @Override
    public boolean isPrepaid() {
        return true;
    }
}
