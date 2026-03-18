import java.util.*;

/**
 * ======================================================================
 * MAIN CLASS - bookmystayapp (Use Case 10)
 * ======================================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Goal: Enable safe cancellation of bookings by reversing system state.
 * Uses a Stack to track released Room IDs, demonstrating LIFO rollback.
 *
 * @author Developer
 * @version 10.0
 */

// --- Cancellation Service & Inventory Manager ---
class CancellationService {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, String> activeBookings = new HashMap<>(); // BookingID -> RoomType

    // Stack tracks released Room IDs for potential reuse or auditing (LIFO)
    private Stack<String> releasedRoomIds = new Stack<>();

    public void setupInitialState(String type, int count) {
        inventory.put(type, count);
    }

    public void addActiveBooking(String bookingId, String roomType) {
        activeBookings.put(bookingId, roomType);
    }

    /**
     * Performs a controlled rollback of a booking.
     * 1. Validates existence. 2. Increments inventory. 3. Records released ID.
     */
    public void cancelBooking(String bookingId, String roomId) {
        System.out.println("\nInitiating cancellation for: " + bookingId);

        // Validation: Ensure the reservation exists
        if (!activeBookings.containsKey(bookingId)) {
            System.err.println("Error: Cancellation failed. Booking ID " + bookingId + " not found.");
            return;
        }

        // State Rollback Logic
        String type = activeBookings.remove(bookingId);

        // 1. Restore Inventory Count
        inventory.put(type, inventory.get(type) + 1);

        // 2. Push Room ID to Rollback Stack (LIFO)
        releasedRoomIds.push(roomId);

        System.out.println("Success: " + bookingId + " cancelled. " + type + " inventory restored.");
        System.out.println("Room ID " + roomId + " has been returned to the pool.");
    }

    public void displaySystemState() {
        System.out.println("\n--- Current System State ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("Active Bookings: " + activeBookings.keySet());
        System.out.println("Recently Released IDs (Stack Top first): " + releasedRoomIds);
        System.out.println("----------------------------");
    }
}

// --- Main Application Entry Point ---
public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("Welcome to bookmystayapp!");
        System.out.println("Hotel Booking Management System v10.0");
        System.out.println("--------------------------------------------------");

        CancellationService service = new CancellationService();

        // 1. Setup initial state with some active bookings
        service.setupInitialState("Single", 5);
        service.setupInitialState("Suite", 2);

        service.addActiveBooking("BK-901", "Suite");
        service.addActiveBooking("BK-902", "Single");
        service.addActiveBooking("BK-903", "Suite");

        System.out.println("Initial State: 3 active bookings, Suite inventory is low.");
        service.displaySystemState();

        // 2. Perform valid cancellations
        service.cancelBooking("BK-903", "Suite-882");
        service.cancelBooking("BK-901", "Suite-104");

        // 3. Attempt an invalid cancellation (Defensive Check)
        service.cancelBooking("BK-999", "Unknown-000");

        // 4. Final State check
        service.displaySystemState();

        System.out.println("Cancellation and Rollback Use Case completed.");
    }
}
