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

    public String getNextAvailableDate (String venueCode, String systemDate) {
      VenueBooking booking = findVenueBooking(venueCode);

      if (booking != null) {
        List<String> bookedDates = booking.getBookedDates();
        if (!bookedDates.isEmpty()) {
          String lastBookingDate = bookedDates.get(bookedDates.size() - 1);

          String[] dateParts = lastBookingDate.split("/");
          int day = Integer.parseInt(dateParts[0]);
          int month = Integer.parseInt(dateParts[1]);
          int year = Integer.parseInt(dateParts[2]);

          while (true) {
            day++;

            if (day > getDaysInMonth(month, year)) {
              day = 1;
              month++;

              if (month > 12) {
                month = 1;
                year++;
              }
            }
            String nextAvailable = String.format("%02d/%02d/%04d", day, month, year);

            if (!bookedDates.contains(nextAvailable)) {
              return nextAvailable;
            }

          }
        }
      }
      return systemDate;
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

    private int getDaysInMonth(int month, int year) {
        switch (month) {
            case 2:
                return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
              return 30;
            default:
              return 31;
        }
    }
}
