package creational_factory;

public class ItalianRestaurantFactory extends RestaurantFactory {
    @Override
    public Restaurant createRestaurant() {
        return new ItalianRestaurant();
    }
}
