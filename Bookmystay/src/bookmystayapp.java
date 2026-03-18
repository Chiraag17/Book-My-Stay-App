import java.util.HashMap;
import java.util.Map;

/**
 * ======================================================================
 * MAIN CLASS - bookmystayapp (Use Case 4)
 * ======================================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Goal: Implement read-only search functionality to prevent unintended
 * state mutation during data access.
 *
 * @author Developer
 * @version 4.0
 */

// --- Domain Model ---
abstract class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }
}

class SingleRoom extends Room { public SingleRoom() { super("Single", 100.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super("Double", 180.0); } }
class SuiteRoom extends Room { public SuiteRoom() { super("Suite", 350.0); } }

// --- Inventory Management (State Holder) ---
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void registerRoom(String type, int initialCount) {
        inventory.put(type, initialCount);
    }

    /**
     * Read-Only Access: Retrieves availability without modifying the map.
     */
    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// --- Search Service (Read-Only Access) ---
class SearchService {
    /**
     * Performs a search and displays only rooms with availability > 0.
     * Demonstrates Defensive Programming and Separation of Concerns.
     */
    public void searchAvailableRooms(Map<String, Room> roomMap, RoomInventory inventory) {
        System.out.println("\n--- Available Rooms Search Results ---");
        boolean roomsFound = false;

        for (String type : roomMap.keySet()) {
            int count = inventory.getAvailability(type);

            // Validation Logic: Exclude unavailable room types
            if (count > 0) {
                Room room = roomMap.get(type);
                System.out.println("Type: " + room.getType() +
                        " | Price: $" + room.getPrice() +
                        " | Units Available: " + count);
                roomsFound = true;
            }
        }

        if (!roomsFound) {
            System.out.println("Currently, no rooms are available.");
        }
        System.out.println("--------------------------------------");
    }
}

// --- Main Application Entry Point ---
public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("Welcome to bookmystayapp!");
        System.out.println("Hotel Booking Management System v4.0");
        System.out.println("--------------------------------------------------");

        // 1. Setup Domain Objects
        Map<String, Room> roomMap = new HashMap<>();
        roomMap.put("Single", new SingleRoom());
        roomMap.put("Double", new DoubleRoom());
        roomMap.put("Suite", new SuiteRoom());

        // 2. Initialize Inventory State
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom("Single", 8);
        inventory.registerRoom("Double", 0); // Explicitly sold out for testing
        inventory.registerRoom("Suite", 3);

        // 3. Initiate Search Service (Read-Only Operation)
        SearchService searchService = new SearchService();
        System.out.println("Guest is searching for available rooms...");
        searchService.searchAvailableRooms(roomMap, inventory);

        System.out.println("Search operation finished. No inventory was modified.");
        System.out.println("--------------------------------------------------");
    }
}
