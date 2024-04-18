package nz.ac.auckland.se281;

public class BookingServices extends Bookings {
  BookingOperations bookingOperations;

  public BookingServices(BookingOperations bookingOperations) {
    super();
    this.bookingOperations = bookingOperations;
  }

  public void addServiceCatering (String bookingReference, Types.CateringType cateringType) {
    VenueBooking booking = bookingOperations.getBooking(bookingReference);

    if (booking != null) {
      String cateringService = cateringType.getName();
      int cateringPrice = cateringType.getCostPerPerson() * Integer.parseInt(booking.attendees.get(booking.bookingReferences.indexOf(bookingReference)));

      booking.setCateringType(cateringService);
      booking.setCateringPrice(String.valueOf(cateringPrice));
    }
  }

  public void addServiceMusic (String bookingReference) {
    VenueBooking booking = bookingOperations.getBooking(bookingReference);

    if (booking != null) {
      String musicService = "Music";
      int musicPrice = 500;

      booking.setMusicService(musicService);
      booking.setMusicPrice(String.valueOf(musicPrice));
    }
  }

  public void addServiceFloral (String bookingReference, Types.FloralType floralType) {
    VenueBooking booking = bookingOperations.getBooking(bookingReference);

    if(booking != null) {
      String floralService = floralType.getName();
      int floralPrice = floralType.getCost();

      booking.setFloralType(floralService);
      booking.setFloralPrice(String.valueOf(floralPrice));
    }
  }
}