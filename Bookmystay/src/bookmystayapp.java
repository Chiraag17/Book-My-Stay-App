import java.util.LinkedList;
import java.util.Queue;

/**
 * ======================================================================
 * MAIN CLASS - bookmystayapp (Use Case 5)
 * ======================================================================
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Goal: Handle multiple booking requests fairly by introducing a
 * request intake mechanism that preserves arrival order using a Queue.
 *
 * @author Developer
 * @version 5.0
 */

// --- Reservation Model ---
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Reservation [Guest: " + guestName + ", Room Type: " + roomType + "]";
    }
}

// --- Booking Request Queue ---
class BookingRequestQueue {
    // FIFO Principle: Queue handles requests in the order they arrive
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Adds a guest's booking request to the queue.
     * No inventory mutation occurs at this stage.
     */
    public void submitRequest(Reservation request) {
        requestQueue.offer(request);
        System.out.println("Enqueued: " + request.getGuestName() + " for " + request.getRoomType());
    }

    /**
     * Displays the current state of the queue to verify ordering.
     */
    public void displayQueue() {
        System.out.println("\n--- Current Booking Request Queue (FIFO Order) ---");
        if (requestQueue.isEmpty()) {
            System.out.println("The queue is currently empty.");
        } else {
            for (Reservation res : requestQueue) {
                System.out.println(res);
            }
        }
        System.out.println("--------------------------------------------------");
    }
}

// --- Main Application Entry Point ---
public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("Welcome to bookmystayapp!");
        System.out.println("Hotel Booking Management System v5.0");
        System.out.println("--------------------------------------------------");

        // 1. Initialize the Booking Request Queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // 2. Guests submit requests (Request Intake)
        System.out.println("Action: Guests are submitting booking requests...");

        bookingQueue.submitRequest(new Reservation("Alice", "Suite"));
        bookingQueue.submitRequest(new Reservation("Bob", "Single"));
        bookingQueue.submitRequest(new Reservation("Charlie", "Double"));
        bookingQueue.submitRequest(new Reservation("Diana", "Suite"));

        // 3. Display the queue to confirm arrival order is preserved
        bookingQueue.displayQueue();

        System.out.println("Request intake completed. Ready for allocation processing.");
        System.out.println("--------------------------------------------------");
    }
}
