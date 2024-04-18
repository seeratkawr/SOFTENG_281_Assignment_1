package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.FloralType;

public class Floral extends Services {
    private FloralType floralType;
    
    public Floral(Bookings.BookingOperations bookingOperations) {
        super(bookingOperations);
    }

    @Override
    public void addService(String bookingReference) {
        Bookings.VenueBooking booking = bookingOperations.getBooking(bookingReference);

        if (booking != null) {
          String floralService = floralType.getName();
          int floralPrice = floralType.getCost();
  
          booking.setFloralType(floralService);
          booking.setFloralPrice(String.valueOf(floralPrice));
        }
    }
}
