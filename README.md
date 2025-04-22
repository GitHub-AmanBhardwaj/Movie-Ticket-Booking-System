# Movie Ticket Booking System

A Java AWT-based application for booking movie tickets, managing seat availability, and viewing or deleting bookings.

## Features
- **Ticket Booking**: Book up to 10 tickets per transaction for selected movies and showtimes.
- **Seat Management**: Tracks available seats for each movie and showtime (50 seats initially per show).
- **Booking Deletion**: Cancel bookings by customer ID, restoring seat availability.
- **Booking Summary**: View all bookings with details like customer ID, name, movie, showtime, and ticket count.
- **User Interface**: Intuitive GUI for booking, deleting, and viewing bookings.

## Prerequisites
- Java Development Kit (JDK) 8 or higher

## How to Run
1. Compile the Java file:
   ```bash
   javac MovieTicketBookingSystem.java
   ```
2. Run the application:
   ```bash
   java MovieTicketBookingSystem
   ```

## Usage
1. **Book Tickets**:
   - Enter a valid Customer ID (alphanumeric) and Name (letters and spaces).
   - Select a movie and showtime from the dropdowns.
   - Specify the number of tickets (1–10).
   - Click "Book Tickets" to confirm and receive a booking ID.
2. **Delete Booking**:
   - Enter the Customer ID of the booking to cancel.
   - Click "Delete Booking" to remove it and restore seats.
3. **View Bookings**:
   - Click "View Bookings" to see a summary of all bookings.
   - Use "Back to Booking" to return to the main screen.

## Movies and Showtimes
- **Movies**: Avengers, Inception, The Matrix
- **Showtimes**: 12:00 PM, 3:00 PM, 6:00 PM, 9:00 PM

## Notes
- Each showtime starts with 50 available seats.
- Customer ID and Name are validated using regex patterns.
- Bookings are stored in memory and reset when the application closes.
- The system prevents overbooking by checking seat availability.
