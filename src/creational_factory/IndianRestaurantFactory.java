package creational_factory;

public class IndianRestaurantFactory extends RestaurantFactory {
    @Override
    public Restaurant createRestaurant() {
        return new IndianRestaurant();
    }
}
