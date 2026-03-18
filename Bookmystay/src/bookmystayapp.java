/**
 * ======================================================================
 * MAIN CLASS - BookMyStayApp (Use Case 2)
 * ======================================================================
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Goal: Introduce object modeling through inheritance and abstraction.
 *
 * @author Developer
 * @version 2.0
 */

// Abstract Class: Defines common attributes for all room types
abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    // Encapsulation: Accessors for room characteristics
    public String getType() { return type; }
    public int getBeds() { return beds; }
    public double getPrice() { return price; }

    public void displayInfo() {
        System.out.println("Room Type: " + type + " | Beds: " + beds + " | Price: $" + price);
    }
}

// Inheritance: Concrete room classes extending the abstract Room class
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 350.0);
    }
}

public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App!");
        System.out.println("Hotel Booking Management System v2.0");
        System.out.println("--------------------------------------------------");

        // Polymorphism: Room objects referenced using the abstract Room type
        Room single = new SingleRoom();
        Room doubleRm = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static Availability Representation: Simple variables instead of data structures
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display room details and current availability
        single.displayInfo();
        System.out.println("Available Units: " + singleAvailable);
        System.out.println();

        doubleRm.displayInfo();
        System.out.println("Available Units: " + doubleAvailable);
        System.out.println();

        suite.displayInfo();
        System.out.println("Available Units: " + suiteAvailable);

        System.out.println("--------------------------------------------------");
        System.out.println("System initialized successfully.");
    }
}
