import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MovieTicketBookingSystem {
    private static final Pattern CUSTOMER_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
    private static final Pattern CUSTOMER_NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final int MAX_TICKETS = 10;
    private static final List<String> MOVIES = Arrays.asList("Avengers", "Inception", "The Matrix");
    private static final List<String> SHOWTIMES = Arrays.asList("12:00 PM", "3:00 PM", "6:00 PM", "9:00 PM");

    private List<Booking> bookings = new ArrayList<>();
    private Map<String, Map<String, Integer>> seatAvailability = new HashMap<>();
    private int bookingCounter = 0;
    private Scanner scanner = new Scanner(System.in);

    public MovieTicketBookingSystem() {
        initializeSeatAvailability();
    }

    private void initializeSeatAvailability() {
        for (String movie : MOVIES) {
            Map<String, Integer> showtimeSeats = new HashMap<>();
            for (String showtime : SHOWTIMES) {
                showtimeSeats.put(showtime, 50);
            }
            seatAvailability.put(movie, showtimeSeats);
        }
    }

    public void start() {
        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    bookTickets();
                    break;
                case "2":
                    deleteBooking();
                    break;
                case "3":
                    viewBookings();
                    break;
                case "4":
                    System.out.println("Thank you for using the Movie Ticket Booking System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Movie Ticket Booking System ===");
        System.out.println("1. Book Tickets");
        System.out.println("2. Delete Booking");
        System.out.println("3. View Bookings");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }

    private void bookTickets() {
        System.out.print("Enter Customer ID (alphanumeric): ");
        String customerId = scanner.nextLine().trim();
        if (customerId.isEmpty() || !CUSTOMER_ID_PATTERN.matcher(customerId).matches()) {
            System.out.println("Invalid Customer ID. Must be alphanumeric.");
            return;
        }

        System.out.print("Enter Customer Name (letters and spaces): ");
        String customerName = scanner.nextLine().trim();
        if (customerName.isEmpty() || !CUSTOMER_NAME_PATTERN.matcher(customerName).matches()) {
            System.out.println("Invalid Customer Name. Must contain only letters and spaces.");
            return;
        }

        System.out.println("Available Movies:");
        for (int i = 0; i < MOVIES.size(); i++) {
            System.out.println((i + 1) + ". " + MOVIES.get(i));
        }
        System.out.print("Select Movie (1-" + MOVIES.size() + "): ");
        int movieIndex;
        try {
            movieIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (movieIndex < 0 || movieIndex >= MOVIES.size()) {
                System.out.println("Invalid movie selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        System.out.println("Available Showtimes:");
        for (int i = 0; i < SHOWTIMES.size(); i++) {
            System.out.println((i + 1) + ". " + SHOWTIMES.get(i));
        }
        System.out.print("Select Showtime (1-" + SHOWTIMES.size() + "): ");
        int showtimeIndex;
        try {
            showtimeIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (showtimeIndex < 0 || showtimeIndex >= SHOWTIMES.size()) {
                System.out.println("Invalid showtime selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        System.out.print("Enter Number of Tickets (1-" + MAX_TICKETS + "): ");
        int tickets;
        try {
            tickets = Integer.parseInt(scanner.nextLine().trim());
            if (tickets < 1 || tickets > MAX_TICKETS) {
                System.out.println("Please enter 1 to " + MAX_TICKETS + " tickets.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        String movie = MOVIES.get(movieIndex);
        String showtime = SHOWTIMES.get(showtimeIndex);
        int availableSeats = seatAvailability.get(movie).get(showtime);
        if (tickets > availableSeats) {
            System.out.println("Only " + availableSeats + " seats available for " + movie + " at " + showtime);
            return;
        }

        String bookingId = String.format("T%03d", ++bookingCounter);
        Booking newBooking = new Booking(customerId, customerName, movie, showtime, tickets, bookingId);
        bookings.add(newBooking);
        seatAvailability.get(movie).put(showtime, availableSeats - tickets);

        System.out.println("Booking confirmed! Booking ID: " + bookingId);
    }

    private void deleteBooking() {
        System.out.print("Enter Customer ID to delete booking: ");
        String deleteId = scanner.nextLine().trim();

        if (deleteId.isEmpty()) {
            System.out.println("Please enter a Customer ID.");
            return;
        }

        Booking toRemove = null;
        for (Booking booking : bookings) {
            if (booking.getCustomerId().equals(deleteId)) {
                toRemove = booking;
                break;
            }
        }

        if (toRemove != null) {
            bookings.remove(toRemove);
            seatAvailability.get(toRemove.getMovie())
                    .put(toRemove.getShowtime(), seatAvailability.get(toRemove.getMovie()).get(toRemove.getShowtime()) + toRemove.getTickets());
            System.out.println("Booking for Customer ID " + deleteId + " deleted successfully.");
        } else {
            System.out.println("No booking found for Customer ID " + deleteId);
        }
    }

    private void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings have been made yet.");
            return;
        }

        System.out.println("\n=== BOOKING SUMMARY ===");
        System.out.println("Total Bookings: " + bookings.size());
        System.out.printf("%-15s%-20s%-20s%-15s%-10s%s\n",
                "Customer ID", "Customer Name", "Movie", "Showtime", "Tickets", "Booking ID");
        System.out.println("--------------------------------------------------------------------------------");
        for (Booking booking : bookings) {
            System.out.printf("%-15s%-20s%-20s%-15s%-10d%s\n",
                    booking.getCustomerId(), booking.getCustomerName(),
                    booking.getMovie(), booking.getShowtime(),
                    booking.getTickets(), booking.getBookingId());
        }
    }

    public static void main(String[] args) {
        MovieTicketBookingSystem bookingSystem = new MovieTicketBookingSystem();
        bookingSystem.start();
    }

    static class Booking {
        private String customerId;
        private String customerName;
        private String movie;
        private String showtime;
        private int tickets;
        private String bookingId;

        public Booking(String customerId, String customerName, String movie, String showtime, int tickets, String bookingId) {
            this.customerId = customerId;
            this.customerName = customerName;
            this.movie = movie;
            this.showtime = showtime;
            this.tickets = tickets;
            this.bookingId = bookingId;
        }

        public String getCustomerId() { return customerId; }
        public String getCustomerName() { return customerName; }
        public String getMovie() { return movie; }
        public String getShowtime() { return showtime; }
        public int getTickets() { return tickets; }
        public String getBookingId() { return bookingId; }
    }
}
