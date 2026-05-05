import java.time.LocalDate;

public class Booking {
    private static int counter = 1000;
    private int bookingId;
    private String guestName;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalAmount;
    private boolean isPaid;

    public Booking(String guestName, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.bookingId = ++counter;
        this.guestName = guestName;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        long nights = checkOut.toEpochDay() - checkIn.toEpochDay();
        this.totalAmount = nights * room.getPrice();
        this.isPaid = false;
    }

    public int getBookingId() { return bookingId; }
    public String getGuestName() { return guestName; }
    public Room getRoom() { return room; }
    public boolean isPaid() { return isPaid; }
    public void markPaid() { this.isPaid = true; }

    public void display() {
        System.out.println("  -----------------------------------------------------");
        System.out.println("  Booking ID  : " + bookingId);
        System.out.println("  Guest       : " + guestName);
        System.out.println("  Room        : " + room.getRoomNumber() + " (" + room.getCategory() + ")");
        System.out.println("  Check-in    : " + checkIn);
        System.out.println("  Check-out   : " + checkOut);
        long nights = checkOut.toEpochDay() - checkIn.toEpochDay();
        System.out.println("  Nights      : " + nights);
        System.out.printf("  Total       : Rs.%.2f%n", totalAmount);
        System.out.println("  Payment     : " + (isPaid ? "PAID" : "PENDING"));
        System.out.println("  -----------------------------------------------------");
    }

    public double getTotalAmount() { return totalAmount; }
}