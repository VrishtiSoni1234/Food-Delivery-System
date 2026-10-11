import behavioural_observer.Customer;
import behavioural_observer.DeliveryPartner;
import behavioural_observer.RestaurantStaff;
import behavioural_strategy.CashOnDeliveryPayment;
import behavioural_strategy.CreditCardPayment;
import behavioural_strategy.PaymentStrategy;
import behavioural_strategy.UpiPayment;
import creational_factory.*;
import model.MenuItem;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--- Welcome to the Food Delivery Design Pattern Simulator ---");

        while (true) {
            printHeader("MAIN MENU");
            System.out.println("Select what you want to do:");
            System.out.println("  1. Place a full order (all patterns together)");
            System.out.println("  2. Factory Method  (create restaurants and order types)");
            System.out.println("  3. Strategy        (switch payment methods)");
            System.out.println("  4. Observer        (live order notifications)");
            System.out.println("  5. State           (order lifecycle transitions)");
            System.out.println("  6. Exit");
            System.out.print("> Enter choice: ");

            String choice = readLine();
            if (choice == null) {
                return; // input closed
            }

            switch (choice.trim()) {
                case "1" -> placeFullOrder();
                case "2" -> demoFactory();
                case "3" -> demoStrategy();
                case "4" -> demoObserver();
                case "5" -> demoState();
                case "6" -> {
                    System.out.println("Exiting simulator. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // ------------------------------------------------------------------
    // Full end-to-end flow
    // ------------------------------------------------------------------
    private static void placeFullOrder() {
        printHeader("PLACE A FULL ORDER");

        Restaurant restaurant = chooseRestaurant();
        Order order = chooseOrderType(restaurant);

        order.getRestaurant().displayMenu();
        addItemsInteractively(order);
        if (order.isEmpty()) {
            System.out.println("No items selected. Order discarded.");
            return;
        }

        // Observer: attach everyone who cares about this order
        System.out.println("\nSetting up notifications...");
        order.attach(new Customer(order.getCustomerName()));
        order.attach(new RestaurantStaff(restaurant.getName()));
        if (order.requiresDeliveryPartner()) {
            order.attach(new DeliveryPartner("Ravi"));
        }

        order.printSummary();

        // Strategy: pay
        while (!order.isPaid()) {
            PaymentStrategy strategy = choosePaymentStrategy(order.getCustomerName());
            if (strategy == null) {
                System.out.println("Checkout cancelled. Order not paid.");
                return;
            }
            order.checkout(strategy);
        }

        // State: drive the lifecycle
        lifecycleLoop(order);
    }

    private static void lifecycleLoop(Order order) {
        while (true) {
            System.out.printf("%nCurrent status: %s%n", order.getStatus());
            System.out.println("  1. Move to next stage");
            System.out.println("  2. Cancel order");
            System.out.println("  3. Show order summary");
            System.out.println("  0. Back to main menu");
            System.out.print("> Enter choice: ");
            String choice = readLine();
            if (choice == null) {
                return;
            }
            switch (choice.trim()) {
                case "1" -> order.next();
                case "2" -> order.cancel();
                case "3" -> order.printSummary();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ------------------------------------------------------------------
    // Demo 1: Factory Method
    // ------------------------------------------------------------------
    private static void demoFactory() {
        printHeader("DEMO 1: FACTORY METHOD (Creational)");

        Restaurant restaurant = chooseRestaurant();
        restaurant.displayMenu();

        Order order = chooseOrderType(restaurant);
        System.out.println("\nThe factory returned a concrete Order through the abstract Order type:");
        order.printSummary();
        pauseForEnter();
    }

    // ------------------------------------------------------------------
    // Demo 2: Strategy
    // ------------------------------------------------------------------
    private static void demoStrategy() {
        printHeader("DEMO 2: STRATEGY (Behavioural)");

        Order order = sampleOrder("Demo Customer", new DeliveryOrderFactory());
        order.printSummary();

        System.out.println("\nSame order, different payment strategies chosen at runtime.");
        while (!order.isPaid()) {
            PaymentStrategy strategy = choosePaymentStrategy(order.getCustomerName());
            if (strategy == null) {
                break;
            }
            order.checkout(strategy);
        }
        pauseForEnter();
    }

    // ------------------------------------------------------------------
    // Demo 3: Observer
    // ------------------------------------------------------------------
    private static void demoObserver() {
        printHeader("DEMO 3: OBSERVER (Behavioural)");

        Order order = sampleOrder("Vrishti", new DeliveryOrderFactory());
        Customer customer = new Customer("Vrishti");
        DeliveryPartner partner = new DeliveryPartner("Ravi");

        System.out.println("\n[Observer] Attaching three observers to the order:");
        order.attach(customer);
        order.attach(new RestaurantStaff(order.getRestaurant().getName()));
        order.attach(partner);

        System.out.println("\n[Observer] Paying (observers get PAYMENT_SUCCESS)...");
        order.checkout(new CashOnDeliveryPayment());

        System.out.println("\n[Observer] Moving to PREPARING...");
        order.next();

        System.out.println("\n[Observer] Delivery partner detaches (no more updates for them):");
        order.detach(partner);

        System.out.println("\n[Observer] Moving to OUT FOR DELIVERY...");
        order.next();

        pauseForEnter();
    }

    // ------------------------------------------------------------------
    // Demo 4: State
    // ------------------------------------------------------------------
    private static void demoState() {
        printHeader("DEMO 4: STATE (Behavioural)");

        Order order = sampleOrder("Vrishti", new PickupOrderFactory());
        order.attach(new Customer("Vrishti"));

        System.out.println("\n[State] Trying to advance before paying (should be blocked):");
        order.next();

        System.out.println("\n[State] Paying, then walking through the whole lifecycle:");
        order.checkout(new UpiPayment("vrishti@upi"));
        order.next(); // PREPARING
        order.next(); // READY / OUT FOR DELIVERY

        System.out.println("\n[State] Trying to cancel after dispatch (should be blocked):");
        order.cancel();

        order.next(); // DELIVERED
        System.out.println("\n[State] Trying to move past the final state:");
        order.next();

        System.out.println("\n[State] A second order, cancelled while still PREPARING:");
        Order second = sampleOrder("Vrishti", new DeliveryOrderFactory());
        second.checkout(new CreditCardPayment("Vrishti", "1234 5678 9012 3456"));
        second.next();
        second.cancel();
        second.next(); // blocked

        pauseForEnter();
    }

    // ------------------------------------------------------------------
    // Helpers: selection menus
    // ------------------------------------------------------------------
    private static Restaurant chooseRestaurant() {
        System.out.println("Select a restaurant:");
        System.out.println("  1. Spice Route (North Indian)");
        System.out.println("  2. La Piazza (Italian)");
        System.out.println("  3. Burger Barn (Fast Food)");
        System.out.print("> Enter choice: ");

        RestaurantFactory factory = switch (safe(readLine())) {
            case "2" -> new ItalianRestaurantFactory();
            case "3" -> new FastFoodRestaurantFactory();
            default -> new IndianRestaurantFactory();
        };
        return factory.openRestaurant();
    }

    private static Order chooseOrderType(Restaurant restaurant) {
        System.out.print("\nEnter your name: ");
        String name = safe(readLine());
        if (name.isBlank()) {
            name = "Guest";
        }

        System.out.println("\nSelect order type:");
        System.out.println("  1. Home Delivery   (Rs. 40,  ~35 min)");
        System.out.println("  2. Self Pickup     (Rs. 0,   ~20 min)");
        System.out.println("  3. Express         (Rs. 80,  ~15 min)");
        System.out.print("> Enter choice: ");

        OrderFactory factory = switch (safe(readLine())) {
            case "2" -> new PickupOrderFactory();
            case "3" -> new ExpressOrderFactory();
            default -> new DeliveryOrderFactory();
        };
        return factory.placeOrder(name, restaurant);
    }

    private static void addItemsInteractively(Order order) {
        List<MenuItem> menu = order.getRestaurant().getMenu();
        while (true) {
            System.out.print("\nEnter item number to add (0 to finish): ");
            int index = readInt();
            if (index == 0) {
                return;
            }
            if (index < 1 || index > menu.size()) {
                System.out.println("Invalid item number.");
                continue;
            }
            System.out.print("Quantity: ");
            int qty = readInt();
            if (qty < 1) {
                System.out.println("Quantity must be at least 1.");
                continue;
            }
            order.addItem(menu.get(index - 1), qty);
            System.out.printf("Added %d x %s%n", qty, menu.get(index - 1).getName());
        }
    }

    private static PaymentStrategy choosePaymentStrategy(String customerName) {
        System.out.println("\nSelect payment method:");
        System.out.println("  1. UPI");
        System.out.println("  2. Credit Card");
        System.out.println("  3. Cash on Delivery");
        System.out.println("  0. Cancel");
        System.out.print("> Enter choice: ");

        switch (safe(readLine())) {
            case "1":
                System.out.print("Enter UPI ID (e.g. name@bank): ");
                return new UpiPayment(safe(readLine()));
            case "2":
                System.out.print("Enter 16-digit card number: ");
                return new CreditCardPayment(customerName, safe(readLine()));
            case "3":
                return new CashOnDeliveryPayment();
            case "0":
                return null;
            default:
                System.out.println("Invalid choice, defaulting to Cash on Delivery.");
                return new CashOnDeliveryPayment();
        }
    }

    private static Order sampleOrder(String customer, OrderFactory orderFactory) {
        Restaurant restaurant = new IndianRestaurantFactory().openRestaurant();
        Order order = orderFactory.placeOrder(customer, restaurant);
        order.addItem(restaurant.getMenu().get(0), 1);
        order.addItem(restaurant.getMenu().get(1), 2);
        return order;
    }

    // ------------------------------------------------------------------
    // Helpers: input / output
    // ------------------------------------------------------------------
    private static String readLine() {
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    private static String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private static int readInt() {
        try {
            return Integer.parseInt(safe(readLine()));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void printHeader(String title) {
        String border = "============================================================";
        System.out.println();
        System.out.println(border);
        int padding = Math.max(0, (border.length() - title.length()) / 2);
        System.out.println(" ".repeat(padding) + title);
        System.out.println(border);
    }

    private static void pauseForEnter() {
        System.out.print("\nPress Enter to continue...");
        readLine();
    }
}
