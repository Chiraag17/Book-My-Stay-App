import java.util.*;

/**
 * ======================================================================
 * MAIN CLASS - bookmystayapp (Use Case 8)
 * ======================================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * Goal: Maintain a chronological record of confirmed bookings using a List,
 * reinforcing a persistence-oriented mindset for auditing and reporting.
 *
 * @author Developer
 * @version 8.0
 */

// --- Model for a Confirmed Booking ---
class ConfirmedBooking {
    private String bookingId;
    private String guestName;
    private String roomType;
    private double totalCost;

    public ConfirmedBooking(String bookingId, String guestName, String roomType, double totalCost) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return String.format("[%s] Guest: %-10s | Room: %-10s | Total: $%.2f",
                bookingId, guestName, roomType, totalCost);
    }

    public double getTotalCost() { return totalCost; }
}

// --- Booking History & Reporting Service ---
class BookingReportService {
    // List stores bookings in chronological (insertion) order
    private List<ConfirmedBooking> bookingHistory = new ArrayList<>();

    /**
     * Adds a newly confirmed reservation to the historical audit trail.
     */
    public void recordBooking(ConfirmedBooking booking) {
        bookingHistory.add(booking);
        System.out.println("History Updated: " + booking);
    }

    /**
     * Admin Tool: Generates a summary report of all transactions.
     * This is a read-only operation on the historical data.
     */
    public void generateSummaryReport() {
        System.out.println("\n--- ADMINISTRATIVE BOOKING REPORT ---");
        if (bookingHistory.isEmpty()) {
            System.out.println("No booking history found.");
            return;
        }

        double totalRevenue = 0;
        for (ConfirmedBooking b : bookingHistory) {
            System.out.println(b);
            totalRevenue += b.getTotalCost();
        }

        System.out.println("-------------------------------------");
        System.out.println("Total Bookings: " + bookingHistory.size());
        System.out.printf("Total Revenue:  $%.2f\n", totalRevenue);
        System.out.println("-------------------------------------");
    }
}

// --- Main Application Entry Point ---
public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("Welcome to bookmystayapp!");
        System.out.println("Hotel Booking Management System v8.0");
        System.out.println("--------------------------------------------------");

        // 1. Initialize the History and Reporting Service
        BookingReportService reportService = new BookingReportService();

        // 2. Simulate the completion of various bookings
        System.out.println("Action: Recording confirmed bookings to history...");

        reportService.recordBooking(new ConfirmedBooking("BK-501", "Alice", "Suite", 425.0));
        reportService.recordBooking(new ConfirmedBooking("BK-502", "Bob", "Single", 110.0));
        reportService.recordBooking(new ConfirmedBooking("BK-503", "Charlie", "Double", 215.0));

        // 3. Admin requests a report
        System.out.println("\nAdmin is requesting the daily operations report...");
        reportService.generateSummaryReport();

        System.out.println("Reporting and historical tracking completed.");
        System.out.println("--------------------------------------------------");
    }
}
