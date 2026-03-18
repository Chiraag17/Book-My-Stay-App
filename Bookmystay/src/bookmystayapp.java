import java.util.HashMap;
import java.util.Map;

/**
 * ======================================================================
 * MAIN CLASS - bookmystayapp (Use Case 3)
 * ======================================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Goal: Use a HashMap to manage room availability, creating a single
 * source of truth for the system state.
 *
 * @author Developer
 * @version 3.0
 */

// Domain Model
abstract class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() { return type; }
}

class SingleRoom extends Room { public SingleRoom() { super("Single", 100.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super("Double", 180.0); } }
class SuiteRoom extends Room { public SuiteRoom() { super("Suite", 350.0); } }

/**
 * RoomInventory - Encapsulates inventory logic.
 * Uses HashMap for O(1) lookup and centralized state management.
 */
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    // Register room types
    public void registerRoom(String type, int initialCount) {
        inventory.put(type, initialCount);
    }

    // Update availability (e.g., -1 for a booking)
    public void updateAvailability(String type, int change) {
        if (inventory.containsKey(type)) {
            int current = inventory.get(type);
            inventory.put(type, current + change);
        }
    }

    // Display current inventory state
    public void displayInventory() {
        System.out.println("--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }
    }
}

public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("Welcome to bookmystayapp!");
        System.out.println("Hotel Booking Management System v3.0");
        System.out.println("--------------------------------------------------");

        // 1. Initialize the Centralized Inventory
        RoomInventory hotelInventory = new RoomInventory();

        // 2. Register room types and counts (Single Source of Truth)
        hotelInventory.registerRoom("Single", 10);
        hotelInventory.registerRoom("Double", 5);
        hotelInventory.registerRoom("Suite", 2);

        // 3. Display Initial State
        hotelInventory.displayInventory();

        // 4. Demonstrate a controlled update (Booking a Double Room)
        System.out.println("\nAction: Booking one 'Double' room...");
        hotelInventory.updateAvailability("Double", -1);

        // 5. Display Final State
        hotelInventory.displayInventory();

        System.out.println("--------------------------------------------------");
        System.out.println("Use Case 3: Inventory Management completed.");
    }
}
