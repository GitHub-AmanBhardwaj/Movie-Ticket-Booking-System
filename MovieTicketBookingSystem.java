import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.regex.Pattern;

public class MovieTicketBookingSystem extends Frame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int MARGIN = 30;
    private static final int INSET = 20;
    private static final int TEXT_FIELD_WIDTH = 30;
    private static final Dimension STATUS_LABEL_SIZE = new Dimension(600, 40);
    private static final Dimension BUTTON_SIZE = new Dimension(250, 50);
    private static final Pattern CUSTOMER_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
    private static final Pattern CUSTOMER_NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final int MAX_TICKETS = 10;

    private List<Booking> bookings = new ArrayList<>();
    private Map<String, Map<String, Integer>> seatAvailability = new HashMap<>();
    private int bookingCounter = 0;
    private List<String> movies = Arrays.asList("Avengers", "Inception", "The Matrix");
    private List<String> showtimes = Arrays.asList("12:00 PM", "3:00 PM", "6:00 PM", "9:00 PM");

    private Panel mainPanel, bookingPanel, summaryPanel;
    private CardLayout cardLayout = new CardLayout();
    private Button bookTicketsBtn, deleteBookingBtn, viewBookingsBtn, backToBookingBtn;
    private Label titleLabel, customerIdLabel, customerNameLabel, movieLabel, showtimeLabel, ticketsLabel, deleteIdLabel, statusLabel;
    private TextField customerIdField, customerNameField, ticketsField, deleteIdField;
    private Choice movieChoice, showtimeChoice;
    private TextArea summaryArea;

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);

    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public MovieTicketBookingSystem() {
        super("Movie Ticket Booking System");
        initializeSeatAvailability();
        setupUI();
        addEventListeners();
    }

    private void initializeSeatAvailability() {
        for (String movie : movies) {
            Map<String, Integer> showtimeSeats = new HashMap<>();
            for (String showtime : showtimes) {
                showtimeSeats.put(showtime, 50);
            }
            seatAvailability.put(movie, showtimeSeats);
        }
    }

    private void setupUI() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        Panel wrapperPanel = new Panel(new BorderLayout(MARGIN, MARGIN));
        wrapperPanel.setBackground(BACKGROUND_COLOR);

        mainPanel = new Panel(cardLayout);
        mainPanel.setBackground(BACKGROUND_COLOR);

        bookingPanel = createBookingPanel();
        mainPanel.add(bookingPanel, "BOOK");

        summaryPanel = createSummaryPanel();
        mainPanel.add(summaryPanel, "SUMMARY");

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        add(wrapperPanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
    }

    private Panel createBookingPanel() {
        Panel panel = new Panel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSET, INSET, INSET, INSET);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        titleLabel = new Label("Movie Ticket Booking System", Label.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        customerIdLabel = new Label("Customer ID:");
        customerIdLabel.setFont(LABEL_FONT);
        customerIdLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(customerIdLabel, gbc);

        customerIdField = new TextField(TEXT_FIELD_WIDTH);
        customerIdField.setFont(TEXT_FONT);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(customerIdField, gbc);

        customerNameLabel = new Label("Customer Name:");
        customerNameLabel.setFont(LABEL_FONT);
        customerNameLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(customerNameLabel, gbc);

        customerNameField = new TextField(TEXT_FIELD_WIDTH);
        customerNameField.setFont(TEXT_FONT);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(customerNameField, gbc);

        movieLabel = new Label("Select Movie:");
        movieLabel.setFont(LABEL_FONT);
        movieLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(movieLabel, gbc);

        movieChoice = new Choice();
        movieChoice.setFont(TEXT_FONT);
        for (String movie : movies) {
            movieChoice.add(movie);
        }
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(movieChoice, gbc);

        showtimeLabel = new Label("Select Showtime:");
        showtimeLabel.setFont(LABEL_FONT);
        showtimeLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panel.add(showtimeLabel, gbc);

        showtimeChoice = new Choice();
        showtimeChoice.setFont(TEXT_FONT);
        for (String showtime : showtimes) {
            showtimeChoice.add(showtime);
        }
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(showtimeChoice, gbc);

        ticketsLabel = new Label("Number of Tickets:");
        ticketsLabel.setFont(LABEL_FONT);
        ticketsLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        panel.add(ticketsLabel, gbc);

        ticketsField = new TextField(TEXT_FIELD_WIDTH);
        ticketsField.setFont(TEXT_FONT);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(ticketsField, gbc);

        deleteIdLabel = new Label("Delete by Customer ID:");
        deleteIdLabel.setFont(LABEL_FONT);
        deleteIdLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        panel.add(deleteIdLabel, gbc);

        deleteIdField = new TextField(TEXT_FIELD_WIDTH);
        deleteIdField.setFont(TEXT_FONT);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(deleteIdField, gbc);

        bookTicketsBtn = new Button("Book Tickets");
        styleButton(bookTicketsBtn);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(bookTicketsBtn, gbc);

        deleteBookingBtn = new Button("Delete Booking");
        styleButton(deleteBookingBtn, ACCENT_COLOR);
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(deleteBookingBtn, gbc);

        viewBookingsBtn = new Button("View Bookings");
        styleButton(viewBookingsBtn, ACCENT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(viewBookingsBtn, gbc);

        statusLabel = new Label("", Label.CENTER);
        statusLabel.setFont(LABEL_FONT);
        statusLabel.setForeground(ACCENT_COLOR);
        statusLabel.setPreferredSize(STATUS_LABEL_SIZE);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(statusLabel, gbc);

        return panel;
    }

    private Panel createSummaryPanel() {
        Panel panel = new Panel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSET, INSET, INSET, INSET);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        Label summaryTitle = new Label("Booking Summary", Label.CENTER);
        summaryTitle.setFont(TITLE_FONT);
        summaryTitle.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        panel.add(summaryTitle, gbc);

        summaryArea = new TextArea(20, 60);
        summaryArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        summaryArea.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        panel.add(summaryArea, gbc);

        backToBookingBtn = new Button("Back to Booking");
        styleButton(backToBookingBtn);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0;
        panel.add(backToBookingBtn, gbc);

        return panel;
    }

    private void styleButton(Button button, Color bgColor) {
        button.setFont(BUTTON_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(BUTTON_SIZE);
    }

    private void styleButton(Button button) {
        styleButton(button, SECONDARY_COLOR);
    }

    private void addEventListeners() {
        bookTicketsBtn.addActionListener(e -> bookTickets());
        deleteBookingBtn.addActionListener(e -> deleteBooking());
        viewBookingsBtn.addActionListener(e -> viewBookings());
        backToBookingBtn.addActionListener(e -> cardLayout.show(mainPanel, "BOOK"));
    }

    private void bookTickets() {
        String customerId = customerIdField.getText().trim();
        String customerName = customerNameField.getText().trim();
        int movieIndex = movieChoice.getSelectedIndex();
        int showtimeIndex = showtimeChoice.getSelectedIndex();
        String ticketsText = ticketsField.getText().trim();

        if (customerId.isEmpty() || !CUSTOMER_ID_PATTERN.matcher(customerId).matches()) {
            statusLabel.setText("Please enter a valid Customer ID (alphanumeric)");
            return;
        }
        if (customerName.isEmpty() || !CUSTOMER_NAME_PATTERN.matcher(customerName).matches()) {
            statusLabel.setText("Please enter a valid Customer Name (letters and spaces)");
            return;
        }
        if (movieIndex == -1) {
            statusLabel.setText("Please select a movie");
            return;
        }
        if (showtimeIndex == -1) {
            statusLabel.setText("Please select a showtime");
            return;
        }
        int tickets;
        try {
            tickets = Integer.parseInt(ticketsText);
            if (tickets < 1 || tickets > MAX_TICKETS) {
                statusLabel.setText("Please enter 1 to " + MAX_TICKETS + " tickets");
                return;
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a valid number of tickets");
            return;
        }

        String movie = movies.get(movieIndex);
        String showtime = showtimes.get(showtimeIndex);
        int availableSeats = seatAvailability.get(movie).get(showtime);
        if (tickets > availableSeats) {
            statusLabel.setText("Only " + availableSeats + " seats available for " + movie + " at " + showtime);
            return;
        }

        String bookingId = String.format("T%03d", ++bookingCounter);
        Booking newBooking = new Booking(customerId, customerName, movie, showtime, tickets, bookingId);
        bookings.add(newBooking);
        seatAvailability.get(movie).put(showtime, availableSeats - tickets);

        customerIdField.setText("");
        customerNameField.setText("");
        ticketsField.setText("");
        movieChoice.select(0);
        showtimeChoice.select(0);

        statusLabel.setText("Booking confirmed! Booking ID: " + bookingId);
    }

    private void deleteBooking() {
        String deleteId = deleteIdField.getText().trim();

        if (deleteId.isEmpty()) {
            statusLabel.setText("Please enter a Customer ID to delete");
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
            statusLabel.setText("Booking for Customer ID " + deleteId + " deleted successfully");
        } else {
            statusLabel.setText("No booking found for Customer ID " + deleteId);
        }

        deleteIdField.setText("");
    }

    private void viewBookings() {
        cardLayout.show(mainPanel, "SUMMARY");
        if (bookings.isEmpty()) {
            summaryArea.setText("No bookings have been made yet.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== BOOKING SUMMARY ===\n\n");
        sb.append("Total Bookings: ").append(bookings.size()).append("\n\n");
        sb.append(String.format("%-15s%-20s%-20s%-15s%-10s%s\n",
                "Customer ID", "Customer Name", "Movie", "Showtime", "Tickets", "Booking ID"));
        sb.append("--------------------------------------------------------------------------------\n");

        for (Booking booking : bookings) {
            sb.append(String.format("%-15s%-20s%-20s%-15s%-10d%s\n",
                    booking.getCustomerId(), booking.getCustomerName(),
                    booking.getMovie(), booking.getShowtime(),
                    booking.getTickets(), booking.getBookingId()));
        }

        summaryArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        MovieTicketBookingSystem bookingSystem = new MovieTicketBookingSystem();
        bookingSystem.setVisible(true);
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
