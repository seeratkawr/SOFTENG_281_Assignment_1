package nz.ac.auckland.se281;
import java.util.ArrayList;
import java.util.List;

public class Bookings{
    protected List<VenueBooking> venueBookings;
    protected BookingOperations bookingOperations;
    protected BookingServices bookingServices;

    public Bookings() {
      this.venueBookings = new ArrayList<>();
      this.bookingOperations = new BookingOperations();
      this.bookingServices = new BookingServices(this.bookingOperations);
    }

    public void makeBooking(String[] extendedOptions) {
      bookingOperations.makeBooking(extendedOptions, venueBookings);
    }

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
        return bookingOperations.getNextAvailableDate(booking, systemDate);
      }

      return systemDate;
    }

    public String[] getBookingReferenceForDate(String venueCode, String date) {
      VenueBooking booking = findVenueBooking(venueCode);

      if (booking != null) {
        return bookingOperations.getBookingReferenceForDate(booking, date);
      }

      return new String[0];
    }

    public VenueBooking getBooking(String bookingReference) {
      return bookingOperations.getBooking(bookingReference);
    }

    public void addServiceCatering (String bookingReference, Types.CateringType cateringType) {
      bookingServices.addServiceCatering(bookingReference, cateringType);
    }

    public String getCateringService (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      
      if (booking != null) {
        return booking.getCateringService();
      }

      return null;
    }

    public String getCateringPrice (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      
      if (booking != null) {
        return booking.getCateringPrice();
      }

      return null;
    }

    public void addServiceMusic (String bookingReference) {
      bookingServices.addServiceMusic(bookingReference);
    }

    public String getMusicService (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      
      if (booking != null) {
        return booking.getMusicService();
      }

      return null;
    }

    public String getMusicPrice (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      
      if (booking != null) {
        return booking.getMusicPrice();
      }

      return null;
    }

    public void addServiceFloral (String bookingReference, Types.FloralType floralType) {
      bookingServices.addServiceFloral(bookingReference, floralType);
    }

    public String getFloralService (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      
      if (booking != null) {
        return booking.getFloralService();
      }

      return null;
    }

    public String getFloralPrice (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      
      if (booking != null) {
        return booking.getFloralPrice();
      }

      return null;
    }
    
    public String[] InvoiceContent(String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);

      if (booking != null) {
        String[] invoiceContent = new String[9];
        invoiceContent[0] = booking.getCateringPrice();
        invoiceContent[1] = booking.getCateringService();
        invoiceContent[2] = booking.getVenueCode();
        invoiceContent[3] = booking.getMusicPrice();
        invoiceContent[4] = booking.getFloralPrice();
        invoiceContent[5] = booking.getFloralService();
        invoiceContent[6] = booking.attendees.get(booking.bookingReferences.indexOf(bookingReference));
        invoiceContent[7] = booking.getBookedDates().get(booking.bookingReferences.indexOf(bookingReference));
        invoiceContent[8] = booking.customerEmails.get(booking.bookingReferences.indexOf(bookingReference));

        return invoiceContent;
      }

      return new String[0];
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
}