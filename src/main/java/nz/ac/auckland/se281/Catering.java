package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.CateringType;

public class Catering extends Services {
    private CateringType cateringType;
    public Catering (Bookings.BookingOperations bookingOperations) {
        super(bookingOperations);
    }

    @Override
    public void addService(String bookingReference) {
        Bookings.VenueBooking booking = bookingOperations.getBooking(bookingReference);

        if (booking != null) {
          String cateringService = cateringType.getName();
          int cateringPrice = cateringType.getCostPerPerson() * Integer.parseInt(booking.attendees.get(booking.bookingReferences.indexOf(bookingReference)));
  
          booking.setCateringType(cateringService);
          booking.setCateringPrice(String.valueOf(cateringPrice));
        }
    }
}
