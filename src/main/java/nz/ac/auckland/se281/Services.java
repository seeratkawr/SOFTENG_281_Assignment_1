package nz.ac.auckland.se281;

public abstract class Services {
    Bookings.BookingOperations bookingOperations;

    public Services(Bookings.BookingOperations bookingOperations) {
        this.bookingOperations = bookingOperations;
    }

    public abstract void addService(String bookingReference);
}
