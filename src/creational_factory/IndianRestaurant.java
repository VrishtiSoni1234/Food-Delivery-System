package creational_factory;

import model.MenuItem;

import java.util.List;

public class IndianRestaurant extends Restaurant {
    public IndianRestaurant() {
        super("Spice Route", "North Indian", List.of(
                new MenuItem("Paneer Butter Masala", 220.0),
                new MenuItem("Butter Naan", 40.0),
                new MenuItem("Veg Biryani", 180.0),
                new MenuItem("Dal Tadka", 150.0)));
    }
}
