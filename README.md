# 🍔 Food Delivery System — Java Design Patterns

A console-based **Food Delivery System** built with **Object-Oriented Programming** and **SOLID principles**. It shows how four classic **Gang of Four design patterns** work together in one realistic domain: ordering food, paying, getting notified, and tracking an order from kitchen to doorstep.

---

## ✨ Features

- Designed a scalable food ordering system using **OOP** and **SOLID** principles
- **Factory Pattern** dynamically creates different order types and restaurant services
- **Strategy Pattern** supports multiple payment methods: UPI, Credit Card, and Cash on Delivery
- **Observer Pattern** notifies customers (and kitchen / delivery partner) about order status updates in real time
- **State Pattern** manages the order lifecycle with seamless, validated transitions between states

---

## 📐 Design Patterns Used

### 🏭 Factory Method (`creational_factory`)
Object creation is separated from business logic.

| Factory                     | Creates            |
|-----------------------------|--------------------|
| `DeliveryOrderFactory`      | `DeliveryOrder` (Rs. 40 fee, ~35 min) |
| `PickupOrderFactory`        | `PickupOrder` (no fee, ~20 min)       |
| `ExpressOrderFactory`       | `ExpressOrder` (Rs. 80 fee, ~15 min)  |
| `IndianRestaurantFactory`   | `IndianRestaurant` (Spice Route)      |
| `ItalianRestaurantFactory`  | `ItalianRestaurant` (La Piazza)       |
| `FastFoodRestaurantFactory` | `FastFoodRestaurant` (Burger Barn)    |

- `OrderFactory` / `RestaurantFactory` are the abstract creators; `Order` / `Restaurant` are the abstract products.

### 💳 Strategy (`behavioural_strategy`)
Payment logic is encapsulated behind the `PaymentStrategy` interface and chosen at runtime.

| Strategy                | Behaviour |
|-------------------------|-----------|
| `UpiPayment`            | Validates the UPI ID format and pays instantly |
| `CreditCardPayment`     | Validates a 16-digit card and masks it in output |
| `CashOnDeliveryPayment` | Confirms the order, cash is collected on delivery |

### 👁️ Observer (`behavioural_observer`)
`Order` is the **subject** (`ISubject`). Anyone interested implements `IObserver` and is notified on every event.

| Observer          | Reacts to |
|-------------------|-----------|
| `Customer`        | Payment, preparing, dispatch, delivered, cancelled |
| `RestaurantStaff` | New confirmed order, cooking, handover, cancellation |
| `DeliveryPartner` | Preparing, pickup, delivered, cancellation |

### 🔄 State (`behavioural_state`)
The order lifecycle is modelled with one class per state, so illegal transitions are rejected cleanly.

```
PLACED ──pay──► PREPARING ──► OUT_FOR_DELIVERY ──► DELIVERED
   │                │
   └──── cancel ────┴──────► CANCELLED
```

| State              | next()                         | cancel()                  |
|--------------------|--------------------------------|---------------------------|
| `PlacedState`      | → Preparing (only if paid)     | → Cancelled               |
| `PreparingState`   | → Out for delivery             | → Cancelled (refund if prepaid) |
| `OutForDeliveryState` | → Delivered                 | Blocked                   |
| `DeliveredState`   | Final                          | Blocked                   |
| `CancelledState`   | Final                          | Already cancelled         |

---

## 🗂️ Project Structure

```
Food-Delivery-System/
├── README.md
├── .gitignore
└── src/
    ├── Main.java                        # Interactive console simulator
    ├── model/
    │   └── MenuItem.java
    ├── creational_factory/
    │   ├── Order.java                   # Abstract product + Observer subject
    │   ├── DeliveryOrder.java
    │   ├── PickupOrder.java
    │   ├── ExpressOrder.java
    │   ├── OrderFactory.java            # Abstract creator
    │   ├── DeliveryOrderFactory.java
    │   ├── PickupOrderFactory.java
    │   ├── ExpressOrderFactory.java
    │   ├── Restaurant.java              # Abstract product
    │   ├── IndianRestaurant.java
    │   ├── ItalianRestaurant.java
    │   ├── FastFoodRestaurant.java
    │   ├── RestaurantFactory.java       # Abstract creator
    │   ├── IndianRestaurantFactory.java
    │   ├── ItalianRestaurantFactory.java
    │   └── FastFoodRestaurantFactory.java
    ├── behavioural_strategy/
    │   ├── PaymentStrategy.java
    │   ├── UpiPayment.java
    │   ├── CreditCardPayment.java
    │   └── CashOnDeliveryPayment.java
    ├── behavioural_observer/
    │   ├── ISubject.java
    │   ├── IObserver.java
    │   ├── Customer.java
    │   ├── RestaurantStaff.java
    │   └── DeliveryPartner.java
    └── behavioural_state/
        ├── OrderState.java
        ├── PlacedState.java
        ├── PreparingState.java
        ├── OutForDeliveryState.java
        ├── DeliveredState.java
        └── CancelledState.java
```

---

## 🧱 SOLID Principles in This Project

- **S — Single Responsibility:** each class has one job (e.g. `UpiPayment` only handles UPI, `PreparingState` only handles the Preparing stage).
- **O — Open/Closed:** add a new payment method, order type, restaurant, observer, or state without modifying existing classes.
- **L — Liskov Substitution:** any `Order`, `Restaurant`, `PaymentStrategy`, `IObserver`, or `OrderState` subtype can replace its parent type safely.
- **I — Interface Segregation:** small focused interfaces (`PaymentStrategy`, `IObserver`, `ISubject`, `OrderState`).
- **D — Dependency Inversion:** `Order` depends on the `PaymentStrategy`, `OrderState`, and `IObserver` abstractions, never on concrete classes.

---

## 🚀 Getting Started

### Prerequisites
- **Java 17 or higher** (the code uses switch expressions)
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or the JDK command line

### Clone the repository
```bash
git clone https://github.com/VrishtiSoni1234/Food-Delivery-System.git
cd Food-Delivery-System
```

### Compile
```bash
# Linux / macOS
mkdir -p out
javac -d out $(find src -name "*.java")

# Windows (PowerShell)
mkdir out
javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
```

### Run
```bash
java -cp out Main
```

---

## 🎮 Demo Menu

```
1. Place a full order (all patterns together)
2. Factory Method  (create restaurants and order types)
3. Strategy        (switch payment methods)
4. Observer        (live order notifications)
5. State           (order lifecycle transitions)
6. Exit
```

Sample output (Observer notifications during an order):

```
[State] Order #1001: PLACED -> PREPARING
[Notification -> Customer Vrishti] Order #1001: The restaurant is preparing your food.
[Notification -> Spice Route Kitchen] Order #1001: Cooking started.
[Notification -> Delivery Partner Ravi] Order #1001: Head to the restaurant, the order will be ready soon.
```

Tip for testing payments: UPI IDs look like `name@bank`, and card numbers must be 16 digits.

---

## 🔧 Extending the Project

| I want to add...        | What to do |
|-------------------------|------------|
| A new payment method (e.g. Wallet) | Create `WalletPayment implements PaymentStrategy` |
| A new order type (e.g. Scheduled)  | Create `ScheduledOrder extends Order` and `ScheduledOrderFactory extends OrderFactory` |
| A new restaurant        | Create a `Restaurant` subclass and its `RestaurantFactory` |
| A new notification target | Create a class implementing `IObserver` and `attach()` it to an order |
| A new order state (e.g. Refunded) | Create a class implementing `OrderState` and transition to it from the relevant state |

---

## 📚 Learning Outcomes

- How **Factory Method** decouples object creation from usage
- How **Strategy** swaps algorithms (payments) at runtime without `if/else` chains
- How **Observer** enables event-driven, one-to-many communication
- How **State** replaces large status `switch` blocks with clean, self-contained classes
- How multiple patterns **collaborate** inside one OOP / SOLID-based design

---

> Educational project built to practise design patterns in Java.
