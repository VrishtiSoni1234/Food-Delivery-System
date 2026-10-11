package creational_factory;

public class PickupOrder extends Order {

    public PickupOrder(String customerName, Restaurant restaurant) {
        super(customerName, restaurant);
    }

    @Override
    public String getOrderType() {
        return "Self Pickup";
    }

    @Override
    public double getDeliveryFee() {
        return 0.0;
    }

    @Override
    public String getEta() {
        return "20 minutes";
    }

    @Override
    public String getDispatchEvent() {
        return "ORDER_READY_FOR_PICKUP";
    }

    @Override
    public boolean requiresDeliveryPartner() {
        return false;
    }
}
