import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class HotelSystem {

    static Scanner sc = new Scanner(System.in);
    static Hotel hotel = new Hotel("Grand Plaza Hotel");

    static void printLine() {
        System.out.println("  -----------------------------------------------------");
    }

    static LocalDate getDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("  [!] Invalid date. Use format: YYYY-MM-DD");
            }
        }
    }

    static void bookRoom() {
        System.out.println("\n  ROOM CATEGORIES:");
        System.out.println("  1. Standard  - Rs.1500/night");
        System.out.println("  2. Deluxe    - Rs.3000/night");
        System.out.println("  3. Suite     - Rs.6000/night");
        System.out.print("  Choose category (1-3): ");
        String cat = sc.nextLine().trim();
        String category;
        switch (cat) {
            case "1": category = "Standard"; break;
            case "2": category = "Deluxe"; break;
            case "3": category = "Suite"; break;
            default:
                System.out.println("  [!] Invalid choice.");
                return;
        }
        hotel.displayAvailableRooms(category);
        System.out.print("  Enter your name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("  [!] Name cannot be empty.");
            return;
        }
        System.out.print("  Enter room number: ");
        int roomNo;
        try {
            roomNo = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid room number.");
            return;
        }
        LocalDate checkIn  = getDate("  Check-in  (YYYY-MM-DD): ");
        LocalDate checkOut = getDate("  Check-out (YYYY-MM-DD): ");
        hotel.makeBooking(name, roomNo, checkIn, checkOut);
    }

    static void cancelRoom() {
        System.out.print("  Enter Booking ID to cancel: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("  Are you sure? (yes/no): ");
            String confirm = sc.nextLine().trim().toLowerCase();
            if (confirm.equals("yes") || confirm.equals("y")) {
                hotel.cancelBooking(id);
            } else {
                System.out.println("  [!] Cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid ID.");
        }
    }

    static void makePayment() {
        System.out.print("  Enter Booking ID to pay: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            hotel.processPayment(id);
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid ID.");
        }
    }

    static void searchBooking() {
        System.out.print("  Enter Booking ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            hotel.searchBooking(id);
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid ID.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=====================================================");
        System.out.println("         HOTEL RESERVATION SYSTEM                   ");
        System.out.println("         Grand Plaza Hotel                          ");
        System.out.println("=====================================================");

        boolean running = true;
        while (running) {
            System.out.println("\n  MAIN MENU");
            printLine();
            System.out.println("  1. View All Rooms");
            System.out.println("  2. Book a Room");
            System.out.println("  3. Cancel Booking");
            System.out.println("  4. View All Bookings");
            System.out.println("  5. Search Booking");
            System.out.println("  6. Make Payment");
            System.out.println("  7. Exit");
            printLine();
            System.out.print("  Choose (1-7): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": hotel.displayAllRooms(); break;
                case "2": bookRoom(); break;
                case "3": cancelRoom(); break;
                case "4": hotel.displayAllBookings(); break;
                case "5": searchBooking(); break;
                case "6": makePayment(); break;
                case "7":
                    System.out.println("  Thank you! Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("  [!] Choose 1-7.");
            }
        }
        sc.close();
    }
}