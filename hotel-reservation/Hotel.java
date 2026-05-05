import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {
    private String hotelName;
    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;

    public Hotel(String hotelName) {
        this.hotelName = hotelName;
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
        initRooms();
    }

    private void initRooms() {
        // Standard rooms
        for (int i = 101; i <= 105; i++)
            rooms.add(new Room(i, "Standard", 1500.00));
        // Deluxe rooms
        for (int i = 201; i <= 205; i++)
            rooms.add(new Room(i, "Deluxe", 3000.00));
        // Suite rooms
        for (int i = 301; i <= 303; i++)
            rooms.add(new Room(i, "Suite", 6000.00));
    }

    public void displayAllRooms() {
        System.out.println("\n  HOTEL: " + hotelName);
        System.out.println("  -----------------------------------------------------");
        System.out.printf("  %-10s %-12s %-14s %s%n",
                "Room No.", "Category", "Price/Night", "Status");
        System.out.println("  -----------------------------------------------------");
        for (Room r : rooms) r.display();
        System.out.println("  -----------------------------------------------------");
    }

    public void displayAvailableRooms(String category) {
        System.out.println("\n  AVAILABLE ROOMS - " + category.toUpperCase());
        System.out.println("  -----------------------------------------------------");
        boolean found = false;
        for (Room r : rooms) {
            if (r.getCategory().equalsIgnoreCase(category) && r.isAvailable()) {
                r.display();
                found = true;
            }
        }
        if (!found) System.out.println("  No rooms available in this category.");
        System.out.println("  -----------------------------------------------------");
    }

    public Booking makeBooking(String guestName, int roomNumber,
                               LocalDate checkIn, LocalDate checkOut) {
        Room target = null;
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) { target = r; break; }
        }
        if (target == null) {
            System.out.println("  [!] Room not found.");
            return null;
        }
        if (!target.isAvailable()) {
            System.out.println("  [!] Room already booked.");
            return null;
        }
        if (!checkOut.isAfter(checkIn)) {
            System.out.println("  [!] Check-out must be after check-in.");
            return null;
        }
        Booking b = new Booking(guestName, target, checkIn, checkOut);
        target.setAvailable(false);
        bookings.add(b);
        System.out.println("  [OK] Booking confirmed! ID: " + b.getBookingId());
        return b;
    }

    public void cancelBooking(int bookingId) {
        Booking target = null;
        for (Booking b : bookings) {
            if (b.getBookingId() == bookingId) { target = b; break; }
        }
        if (target == null) {
            System.out.println("  [!] Booking not found.");
            return;
        }
        target.getRoom().setAvailable(true);
        bookings.remove(target);
        System.out.println("  [OK] Booking " + bookingId + " cancelled.");
    }

    public void processPayment(int bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingId() == bookingId) {
                if (b.isPaid()) {
                    System.out.println("  [!] Already paid.");
                } else {
                    b.markPaid();
                    System.out.printf("  [OK] Payment of Rs.%.2f received!%n",
                            b.getTotalAmount());
                }
                return;
            }
        }
        System.out.println("  [!] Booking not found.");
    }

    public void displayAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("  No bookings found.");
            return;
        }
        for (Booking b : bookings) b.display();
    }

    public void searchBooking(int bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingId() == bookingId) {
                b.display();
                return;
            }
        }
        System.out.println("  [!] Booking not found.");
    }
}