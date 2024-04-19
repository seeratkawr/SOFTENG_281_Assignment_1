package nz.ac.auckland.se281;

public class MusicService extends Services {
  // This class extends the Services 'parent' class
  // Constructor for Music class
  public MusicService(Bookings.BookingOperations bookingOperations) {
    super(bookingOperations);
  }

  // Method that adds a music service to a booking
  public void addService(String bookingReference) {
    Bookings.VenueBooking booking = bookingOperations.getBooking(bookingReference);

    if (booking != null) {
      String musicService = "Music";
      int musicPrice = 500;

      booking.setMusicService(musicService);
      booking.setMusicPrice(String.valueOf(musicPrice));
    }
  }
}
