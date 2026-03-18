import java.util.*;

/**
 * ======================================================================
 * MAIN CLASS - bookmystayapp (Use Case 7)
 * ======================================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Goal: Demonstrate business extensibility by mapping multiple optional
 * services to a reservation ID using Map<String, List<Service>>.
 *
 * @author Developer
 * @version 7.0
 */

// --- Model for Add-On Services ---
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() { return name; }
    public double getCost() { return cost; }

    @Override
    public String toString() {
        return name + " ($" + cost + ")";
    }
}

// --- Add-On Service Manager ---
class AddOnServiceManager {
    // Maps Reservation ID to a List of selected Services (One-to-Many)
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    /**
     * Associates a service with a specific reservation ID.
     */
    public void addServiceToBooking(String bookingId, Service service) {
        // If the ID is new, initialize a new ArrayList
        reservationServices.computeIfAbsent(bookingId, k -> new ArrayList<>()).add(service);
        System.out.println("Added " + service.getName() + " to Booking ID: " + bookingId);
    }

    /**
     * Calculates the total cost of all add-ons for a specific reservation.
     */
    public double calculateAddOnTotal(String bookingId) {
        List<Service> services = reservationServices.get(bookingId);
        if (services == null) return 0.0;

        return services.stream().mapToDouble(Service::getCost).sum();
    }

    /**
     * Displays all selected services for a reservation.
     */
    public void displayBookingServices(String bookingId) {
        List<Service> services = reservationServices.getOrDefault(bookingId, Collections.emptyList());
        System.out.println("Services for " + bookingId + ": " + services);
        System.out.println("Total Add-On Cost: $" + calculateAddOnTotal(bookingId));
    }
}

// --- Main Application Entry Point ---
public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("Welcome to bookmystayapp!");
        System.out.println("Hotel Booking Management System v7.0");
        System.out.println("--------------------------------------------------");

        // 1. Define available services
        Service breakfast = new Service("Buffet Breakfast", 25.0);
        Service wifi = new Service("High-Speed WiFi", 10.0);
        Service spa = new Service("Spa Treatment", 50.0);

        // 2. Initialize the Add-On Service Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // 3. Simulate existing bookings (from Use Case 6)
        String bookingId1 = "BK-1001";
        String bookingId2 = "BK-1002";

        System.out.println("Action: Guests are selecting optional add-ons...");

        // Guest 1 selects Breakfast and WiFi
        serviceManager.addServiceToBooking(bookingId1, breakfast);
        serviceManager.addServiceToBooking(bookingId1, wifi);

        // Guest 2 selects Spa
        serviceManager.addServiceToBooking(bookingId2, spa);

        // 4. Display Results
        System.out.println("\n--- Final Billing Summary (Add-Ons Only) ---");
        serviceManager.displayBookingServices(bookingId1);
        System.out.println();
        serviceManager.displayBookingServices(bookingId2);

        System.out.println("--------------------------------------------------");
        System.out.println("Add-on service selection logic completed.");
    }
}
