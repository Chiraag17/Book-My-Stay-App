import java.util.*;
import java.util.concurrent.*;

// ======================================================================
// MAIN CLASS - bookmystayapp (Use Case 11)
// ======================================================================
//
// Use Case 11: Concurrent Booking Simulation (Thread Safety)
//
// Goal: Demonstrate how concurrent access to shared resources can lead
//       to inconsistent system state and show how synchronization ensures
//       correctness under multi-user conditions.
//
// @author Developer
// @version 11.0
// ======================================================================

// --- Booking Request Object ---
class BookingRequest {
    String bookingId;
    String roomType;

    BookingRequest(String bookingId, String roomType) {
        this.bookingId = bookingId;
        this.roomType = roomType;
    }
}

// --- Concurrent Booking Processor ---
class ConcurrentBookingProcessor {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, String> confirmedBookings = new HashMap<>();
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();

    // Initialize inventory
    public void setupInitialState(String type, int count) {
        inventory.put(type, count);
    }

    // Add booking request to queue
    public void submitBookingRequest(BookingRequest request) {
        synchronized (bookingQueue) {
            bookingQueue.add(request);
        }
    }

    // Process booking requests safely
    public void processRequests() {
        while (true) {
            BookingRequest request;
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) break;
                request = bookingQueue.poll();
            }
            allocateRoom(request);
        }
    }

    // Critical section: allocate room
    private synchronized void allocateRoom(BookingRequest request) {
        String type = request.roomType;
        if (inventory.getOrDefault(type, 0) > 0) {
            inventory.put(type, inventory.get(type) - 1);
            confirmedBookings.put(request.bookingId, type);
            System.out.println(Thread.currentThread().getName() +
                    " -> Booking Confirmed: " + request.bookingId + " -> " + type);
        } else {
            System.err.println(Thread.currentThread().getName() +
                    " -> Booking Failed: " + request.bookingId + " -> " + type + " (No inventory)");
        }
    }

    public void displaySystemState() {
        System.out.println("\n--- Final System State ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("Confirmed Bookings: " + confirmedBookings.keySet());
        System.out.println("--------------------------");
    }
}

// --- Main Application Entry Point ---
public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App!");
        System.out.println("Hotel Booking Management System v11.0");
        System.out.println("--------------------------------------------------");

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();

        // Setup initial inventory
        processor.setupInitialState("Single", 3);
        processor.setupInitialState("Suite", 2);

        // Simulate multiple guests submitting requests concurrently
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Submit booking requests
        for (int i = 1; i <= 6; i++) {
            String bookingId = "BK-" + (100 + i);
            String roomType = (i % 2 == 0) ? "Suite" : "Single";
            processor.submitBookingRequest(new BookingRequest(bookingId, roomType));
        }

        // Multiple threads process requests concurrently
        for (int i = 0; i < 3; i++) {
            executor.execute(() -> processor.processRequests());
        }

        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display final system state
        processor.displaySystemState();

        System.out.println("Concurrent Booking Simulation completed.");
    }
}