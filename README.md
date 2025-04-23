# Movie Ticket Booking System

A Java terminal-based application for booking movie tickets, managing seat availability, and viewing or deleting bookings.

## Features

- **Ticket Booking**: Book up to 10 tickets per transaction for selected movies and showtimes.
- **Seat Management**: Tracks available seats for each movie and showtime (50 seats initially per show).
- **Booking Deletion**: Cancel bookings by customer ID, restoring seat availability.
- **Booking Summary**: View all bookings with details like customer ID, name, movie, showtime, and ticket count.
- **User Interface**: Simple console-based menu for booking, deleting, and viewing bookings.

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

1. **Main Menu**:

   - Run the application to see the menu with options:
     - Book Tickets
     - Delete Booking
     - View Bookings
     - Exit
   - Enter the number (1–4) to select an option.

2. **Book Tickets**:

   - Enter a valid Customer ID (alphanumeric) and Name (letters and spaces).
   - Select a movie by entering its number (e.g., 1 for Avengers).
   - Select a showtime by entering its number (e.g., 1 for 12:00 PM).
   - Specify the number of tickets (1–10).
   - Confirm to receive a booking ID (e.g., T001).

3. **Delete Booking**:

   - Enter the Customer ID of the booking to cancel.
   - Confirm to remove the booking and restore seats.

4. **View Bookings**:

   - Select "View Bookings" to see a table of all bookings, including customer ID, name, movie, showtime, ticket count, and booking ID.
   - Return to the main menu by selecting another option.

## Movies and Showtimes

- **Movies**: Avengers, Inception, The Matrix
- **Showtimes**: 12:00 PM, 3:00 PM, 6:00 PM, 9:00 PM

## Notes

- Each showtime starts with 50 available seats.
- Customer ID and Name are validated using regex patterns.
- Bookings are stored in memory and reset when the application closes.
- The system prevents overbooking by checking seat availability.
- Input validation ensures proper data entry (e.g., numeric input for tickets, valid movie/showtime selections).
