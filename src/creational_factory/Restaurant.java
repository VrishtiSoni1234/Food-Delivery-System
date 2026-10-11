package creational_factory;

import model.MenuItem;

import java.util.List;

public abstract class Restaurant {

    protected final String name;
    protected final String cuisine;
    protected final List<MenuItem> menu;

    public Restaurant(String name, String cuisine, List<MenuItem> menu) {
        this.name = name;
        this.cuisine = cuisine;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void displayMenu() {
        System.out.printf("  > %s (%s)%n", name, cuisine);
        for (int i = 0; i < menu.size(); i++) {
            System.out.printf("    %d. %s%n", i + 1, menu.get(i));
        }
    }
}
