package nz.ac.auckland.se281;

public abstract class Services {
  // This is an abstract class that is extended by Catering, Floral, and Music classes
  protected Bookings.BookingOperations bookingOperations;

  // Constructor for Services class
  public Services(Bookings.BookingOperations bookingOperations) {
    this.bookingOperations = bookingOperations;
  }

  // Abstract method that is implemented by Catering, Floral, and Music classes
  public abstract void addService(String bookingReference);
}
