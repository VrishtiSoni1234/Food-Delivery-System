package behavioural_strategy;

public class CreditCardPayment implements PaymentStrategy {

    private final String cardHolder;
    private final String cardNumber;

    public CreditCardPayment(String cardHolder, String cardNumber) {
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber == null ? "" : cardNumber.replaceAll("\\s+", "");
    }

    @Override
    public boolean pay(double amount) {
        if (!cardNumber.matches("\\d{16}")) {
            System.out.println("  [Payment] Invalid card number. Expected 16 digits.");
            return false;
        }
        String masked = "**** **** **** " + cardNumber.substring(12);
        System.out.printf("  [Payment] Rs. %.2f charged to credit card %s (%s).%n", amount, masked, cardHolder);
        return true;
    }

    @Override
    public String getName() {
        return "Credit Card";
    }

    @Override
    public boolean isPrepaid() {
        return true;
    }
}
