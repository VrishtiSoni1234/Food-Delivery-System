package creational_factory;

import model.MenuItem;

import java.util.List;

public class ItalianRestaurant extends Restaurant {
    public ItalianRestaurant() {
        super("La Piazza", "Italian", List.of(
                new MenuItem("Margherita Pizza", 249.0),
                new MenuItem("Penne Arrabbiata", 229.0),
                new MenuItem("Garlic Bread", 99.0),
                new MenuItem("Tiramisu", 140.0)));
    }
}
