package nz.ac.auckland.se281;

public class Music extends Services {
    public Music(Bookings.BookingOperations bookingOperations) {
        super(bookingOperations);
    }

    @Override
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
