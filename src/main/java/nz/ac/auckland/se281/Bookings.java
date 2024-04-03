package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class Bookings{
    private List<VenueBooking> venueBookings;

    public Bookings() {
        super(); // Call the constructor of the superclass
        this.venueBookings = new ArrayList<>();
    }

    public void makeBooking(String[] options) {
      // Additional logic for tracking booked dates
      String venueCode = options[0];
      String bookingDate = options[1];
  
      VenueBooking booking = findVenueBooking(venueCode);
      if (booking != null) {
          booking.addBookedDate(bookingDate);
      } else {
          VenueBooking newBooking = new VenueBooking(venueCode);
          newBooking.addBookedDate(bookingDate);
          venueBookings.add(newBooking);
      }
  }
  

    // Method to get booked dates for a venue
    public List<String> getBookedDatesForVenue(String venueCode) {
        VenueBooking booking = findVenueBooking(venueCode);
        if (booking != null) {
            return booking.getBookedDates();
        }
        return new ArrayList<>();
    }

    // Helper method to find VenueBooking object by venue code
    private VenueBooking findVenueBooking(String venueCode) {
        for (VenueBooking booking : venueBookings) {
            if (booking.getVenueCode().equals(venueCode)) {
                return booking;
            }
        }
        return null;
    }

    // Inner class representing bookings for a specific venue
    private class VenueBooking {
        private String venueCode;
        private List<String> bookedDates;

        public VenueBooking(String venueCode) {
            this.venueCode = venueCode;
            this.bookedDates = new ArrayList<>();
        }

        public String getVenueCode() {
            return venueCode;
        }

        public void addBookedDate(String date) {
            bookedDates.add(date);
        }

        public List<String> getBookedDates() {
            return bookedDates;
        }
    }
}
