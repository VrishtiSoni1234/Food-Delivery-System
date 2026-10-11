package creational_factory;

import model.MenuItem;

import java.util.List;

public class FastFoodRestaurant extends Restaurant {
    public FastFoodRestaurant() {
        super("Burger Barn", "Fast Food", List.of(
                new MenuItem("Veg Burger", 99.0),
                new MenuItem("French Fries", 79.0),
                new MenuItem("Chicken Wrap", 129.0),
                new MenuItem("Cold Coffee", 89.0)));
    }
}
