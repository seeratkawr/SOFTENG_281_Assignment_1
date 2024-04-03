package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class Bookings{
    private List<VenueBooking> venueBookings;

    public Bookings() {
        this.venueBookings = new ArrayList<>();
    }

    public void makeBooking(String[] options) {
      // Additional logic for tracking booked dates
      String venueCode = options[0];
      String bookingDate = options[1];
      String customerEmail = options[2];
      String attendees = options[3];
      String bookingReference = options[4];
  
      VenueBooking booking = findVenueBooking(venueCode);
      if (booking != null) {
          booking.addBooking(bookingDate, customerEmail, attendees, bookingReference);
      } else {
          VenueBooking newBooking = new VenueBooking(venueCode);
          newBooking.addBooking(bookingDate, customerEmail, attendees, bookingReference);
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

    public String[] getBookingReferenceForDate(String venueCode, String date) {
      List<String> references = new ArrayList<>();
      VenueBooking booking = findVenueBooking(venueCode);

      if (booking != null) {
        for (String bookedDate : booking.getBookedDates()) {
          if (bookedDate.equals(date)) {
            references.add(booking.getBookingReference(bookedDate));
          }
        }
      }
      return references.toArray(new String[0]);
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
        private List<String> customerEmails;
        private List<String> attendees;
        private List<String> bookingReferences;

        public VenueBooking(String venueCode) {
            this.venueCode = venueCode;
            this.bookedDates = new ArrayList<>();
            this.customerEmails = new ArrayList<>();
            this.attendees = new ArrayList<>();
            this.bookingReferences = new ArrayList<>();
        }

        public String getVenueCode() {
            return venueCode;
        }

        public void addBooking(String date, String customerEmail, String attendees, String bookingReference) {
            bookedDates.add(date);
            customerEmails.add(customerEmail);
            this.attendees.add(attendees);
            bookingReferences.add(bookingReference);
        }

        public List<String> getBookedDates() {
            return bookedDates;
        }

        public String getBookingReference (String date) {
          int index = bookedDates.indexOf(date);
          if (index != -1 && index < bookingReferences.size()) {
            return bookingReferences.get(index);
          }
          return null;
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
