import java.util.HashMap;
import java.util.Map;

/**
 * ======================================================================
 * MAIN CLASS - bookmystayapp (Use Case 9)
 * ======================================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * Goal: Strengthen system reliability by introducing custom exceptions
 * and fail-fast validation logic to protect the system state.
 *
 * @author Developer
 * @version 9.0
 */

// --- Custom Exception Classes ---
class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) {
        super(message);
    }
}

class InsufficientInventoryException extends Exception {
    public InsufficientInventoryException(String message) {
        super(message);
    }
}

// --- Validated Inventory Service ---
class ValidatedInventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addInventory(String type, int count) {
        inventory.put(type, count);
    }

    /**
     * Process Booking with Validation (Fail-Fast Design)
     * @throws InvalidRoomTypeException if the room type does not exist.
     * @throws InsufficientInventoryException if units are zero.
     */
    public void validateAndBook(String type) throws InvalidRoomTypeException, InsufficientInventoryException {
        // 1. Validate Room Type Existence
        if (!inventory.containsKey(type)) {
            throw new InvalidRoomTypeException("Error: Room type '" + type + "' does not exist in our system.");
        }

        // 2. Validate Availability
        int currentCount = inventory.get(type);
        if (currentCount <= 0) {
            throw new InsufficientInventoryException("Error: No available units for '" + type + "'.");
        }

        // 3. Update State only after all validations pass
        inventory.put(type, currentCount - 1);
        System.out.println("Success: One '" + type + "' room booked successfully.");
    }

    public void displayStatus() {
        System.out.println("Current Inventory State: " + inventory);
    }
}

// --- Main Application Entry Point ---
public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("Welcome to bookmystayapp!");
        System.out.println("Hotel Booking Management System v9.0");
        System.out.println("--------------------------------------------------");

        ValidatedInventoryService service = new ValidatedInventoryService();
        service.addInventory("Single", 1);
        service.addInventory("Suite", 5);

        // Scenario 1: Valid Booking
        processBookingRequest(service, "Suite");

        // Scenario 2: Invalid Room Type (Testing InvalidRoomTypeException)
        processBookingRequest(service, "Penthouse");

        // Scenario 3: Out of Stock (Testing InsufficientInventoryException)
        processBookingRequest(service, "Single"); // First one succeeds
        processBookingRequest(service, "Single"); // Second one should fail

        System.out.println("\n--------------------------------------------------");
        service.displayStatus();
        System.out.println("Validation and Error Handling Use Case completed.");
    }

    /**
     * Helper method to handle exceptions gracefully without crashing the app.
     */
    private static void processBookingRequest(ValidatedInventoryService service, String roomType) {
        try {
            System.out.println("\nAttempting to book: " + roomType);
            service.validateAndBook(roomType);
        } catch (InvalidRoomTypeException | InsufficientInventoryException e) {
            // Graceful Failure Handling: Log the error and continue
            System.err.println("ALARM: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
