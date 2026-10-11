package creational_factory;

public class ExpressOrder extends Order {

    public ExpressOrder(String customerName, Restaurant restaurant) {
        super(customerName, restaurant);
    }

    @Override
    public String getOrderType() {
        return "Express Delivery";
    }

    @Override
    public double getDeliveryFee() {
        return 80.0;
    }

    @Override
    public String getEta() {
        return "15 minutes";
    }

    @Override
    public String getDispatchEvent() {
        return "ORDER_OUT_FOR_DELIVERY";
    }

    @Override
    public boolean requiresDeliveryPartner() {
        return true;
    }
}
