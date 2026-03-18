import java.util.*;

/**
 * ======================================================================
 * MAIN CLASS - bookmystayapp (Use Case 6)
 * ======================================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Goal: Process queued requests, generate unique Room IDs using a Set,
 * and update inventory to prevent double-booking.
 *
 * @author Developer
 * @version 6.0
 */

// --- Domain Model ---
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// --- Inventory Service ---
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();
    // Set ensures Room IDs are unique and never duplicated
    private Set<String> allocatedRoomIds = new HashSet<>();

    public void addInventory(String type, int count) {
        inventory.put(type, count);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public void allocateRoom(String type, String roomId) {
        // Decrement inventory
        inventory.put(type, inventory.get(type) - 1);
        // Record unique Room ID
        allocatedRoomIds.add(roomId);
    }

    public void displayStatus() {
        System.out.println("\n--- Final System State ---");
        System.out.println("Remaining Inventory: " + inventory);
        System.out.println("Allocated Room IDs: " + allocatedRoomIds);
    }
}

// --- Booking & Allocation Service ---
class BookingService {
    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void processRequests(Queue<Reservation> queue) {
        System.out.println("\n--- Processing Booking Queue ---");

        while (!queue.isEmpty()) {
            Reservation request = queue.poll(); // FIFO Dequeue
            String type = request.getRoomType();

            if (inventoryService.isAvailable(type)) {
                // Generate a Unique Room ID (e.g., Suite-101)
                String roomId = type + "-" + (100 + new Random().nextInt(900));

                inventoryService.allocateRoom(type, roomId);

                System.out.println("CONFIRMED: " + request.getGuestName() +
                        " assigned to " + roomId);
            } else {
                System.out.println("DENIED: No " + type + " rooms available for " +
                        request.getGuestName());
            }
        }
    }
}

// --- Main Application Entry Point ---
public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("Welcome to bookmystayapp!");
        System.out.println("Hotel Booking Management System v6.0");
        System.out.println("--------------------------------------------------");

        // 1. Setup Inventory
        InventoryService inventory = new InventoryService();
        inventory.addInventory("Single", 2);
        inventory.addInventory("Suite", 1);

        // 2. Setup Request Queue (Use Case 5 logic)
        Queue<Reservation> requestQueue = new LinkedList<>();
        requestQueue.offer(new Reservation("Alice", "Suite"));
        requestQueue.offer(new Reservation("Bob", "Single"));
        requestQueue.offer(new Reservation("Charlie", "Suite")); // Should be denied
        requestQueue.offer(new Reservation("Diana", "Single"));

        // 3. Process Allocations
        BookingService bookingService = new BookingService(inventory);
        bookingService.processRequests(requestQueue);

        // 4. Show final state
        inventory.displayStatus();

        System.out.println("--------------------------------------------------");
        System.out.println("Allocation and synchronization completed.");
    }
}
