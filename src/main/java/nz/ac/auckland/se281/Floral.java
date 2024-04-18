package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.FloralType;

public class Floral extends Services {
  // This class extends the Services 'parent' class
  private FloralType floralType;

  // Constructor for Floral class
  public Floral(Bookings.BookingOperations bookingOperations) {
    super(bookingOperations);
  }

  // Method that adds a floral service to a booking
  public void addService(String bookingReference) {
    Bookings.VenueBooking booking = bookingOperations.getBooking(bookingReference);

    if (booking != null) {
      String floralService = floralType.getName();
      int floralPrice = floralType.getCost();

      booking.setFloralType(floralService);
      booking.setFloralPrice(String.valueOf(floralPrice));
    }
  }

  // Method that sets the floral type
  public void setFloralType(FloralType floralType) {
    this.floralType = floralType;
  }
}
