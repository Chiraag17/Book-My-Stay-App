import java.io.*;
import java.util.*;

// ======================================================================
// MAIN CLASS - bookmystayapp (Use Case 12)
// ======================================================================
//
// Use Case 12: Data Persistence & System Recovery
//
// Goal: Ensure critical system state survives application restarts by
//       persisting inventory and booking data to a file and restoring it.
//
// @author Developer
// @version 12.0
// ======================================================================

// --- Serializable System State ---
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<String, Integer> inventory;
    Map<String, String> confirmedBookings;

    SystemState(Map<String, Integer> inventory, Map<String, String> confirmedBookings) {
        this.inventory = new HashMap<>(inventory);
        this.confirmedBookings = new HashMap<>(confirmedBookings);
    }
}

// --- Persistence Service ---
class PersistenceService {
    private static final String FILE_NAME = "system_state.ser";

    // Save system state to file
    public void saveState(SystemState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load system state from file
    public SystemState loadState() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) in.readObject();
            System.out.println("System state restored successfully.");
            return state;
        } catch (FileNotFoundException e) {
            System.err.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error restoring system state: " + e.getMessage());
        }
        return null;
    }
}

// --- Booking Manager with Persistence ---
class BookingManager {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, String> confirmedBookings = new HashMap<>();
    private PersistenceService persistence = new PersistenceService();

    // Restore state if available
    public void restoreState() {
        SystemState state = persistence.loadState();
        if (state != null) {
            this.inventory = state.inventory;
            this.confirmedBookings = state.confirmedBookings;
        }
    }

    // Save current state
    public void saveState() {
        persistence.saveState(new SystemState(inventory, confirmedBookings));
    }

    public void setupInitialState(String type, int count) {
        inventory.put(type, count);
    }

    public void addBooking(String bookingId, String roomType) {
        if (inventory.getOrDefault(roomType, 0) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            confirmedBookings.put(bookingId, roomType);
            System.out.println("Booking Confirmed: " + bookingId + " -> " + roomType);
        } else {
            System.err.println("Booking Failed: " + bookingId + " -> " + roomType + " (No inventory)");
        }
    }

    public void displaySystemState() {
        System.out.println("\n--- Current System State ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("Confirmed Bookings: " + confirmedBookings.keySet());
        System.out.println("----------------------------");
    }
}

// --- Main Application Entry Point ---
public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App!");
        System.out.println("Hotel Booking Management System v12.0");
        System.out.println("--------------------------------------------------");

        BookingManager manager = new BookingManager();

        // Restore state if available
        manager.restoreState();
        manager.displaySystemState();

        // Simulate new bookings
        manager.setupInitialState("Single", manager.inventory.getOrDefault("Single", 2));
        manager.setupInitialState("Suite", manager.inventory.getOrDefault("Suite", 1));

        manager.addBooking("BK-120", "Single");
        manager.addBooking("BK-121", "Suite");

        manager.displaySystemState();

        // Save state before shutdown
        manager.saveState();

        System.out.println("Data Persistence & Recovery Use Case completed.");
    }
}