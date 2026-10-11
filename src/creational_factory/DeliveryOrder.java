package creational_factory;

public class DeliveryOrder extends Order {

    public DeliveryOrder(String customerName, Restaurant restaurant) {
        super(customerName, restaurant);
    }

    @Override
    public String getOrderType() {
        return "Home Delivery";
    }

    @Override
    public double getDeliveryFee() {
        return 40.0;
    }

    @Override
    public String getEta() {
        return "35 minutes";
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
