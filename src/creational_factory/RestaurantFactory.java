package creational_factory;

public abstract class RestaurantFactory {

    public abstract Restaurant createRestaurant();

    public Restaurant openRestaurant() {
        System.out.printf("%n[FactoryMethod] Using %s...%n", this.getClass().getSimpleName());
        Restaurant restaurant = createRestaurant();
        System.out.printf("[FactoryMethod] Restaurant '%s' is now open.%n", restaurant.getName());
        return restaurant;
    }
}
