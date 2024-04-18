package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.CateringType;

public class Catering extends Services {
  // This class extends the Services 'parent' class
  private CateringType cateringType;

  // Constructor for Catering class
  public Catering(Bookings.BookingOperations bookingOperations) {
    super(bookingOperations);
  }

  // Method that adds a catering service to a booking
  public void addService(String bookingReference) {
    Bookings.VenueBooking booking = bookingOperations.getBooking(bookingReference);

    if (booking != null) {
      String cateringService = cateringType.getName();
      int cateringPrice =
          cateringType.getCostPerPerson()
              * Integer.parseInt(
                  booking.attendees.get(booking.bookingReferences.indexOf(bookingReference)));

      booking.setCateringType(cateringService);
      booking.setCateringPrice(String.valueOf(cateringPrice));
    }
  }

  // Method that sets the catering type
  public void setCateringType(CateringType cateringType) {
    this.cateringType = cateringType;
  }
}
