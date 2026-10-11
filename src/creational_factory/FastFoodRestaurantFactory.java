package creational_factory;

public class FastFoodRestaurantFactory extends RestaurantFactory {
    @Override
    public Restaurant createRestaurant() {
        return new FastFoodRestaurant();
    }
}
