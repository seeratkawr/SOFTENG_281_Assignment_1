package nz.ac.auckland.se281;
import java.util.ArrayList;
import java.util.List;

public class BookingOperations extends Bookings {
  public BookingOperations() {
    super();
  }
  
  public void makeBooking(String[] extendedOptions, List<VenueBooking> venueBookings) {
    String venueCode = extendedOptions[0];
    String bookingDate = extendedOptions[1];
    String customerEmail = extendedOptions[2];
    String attendees = extendedOptions[3];
    String bookingReference = extendedOptions[4];

    VenueBooking booking = findVenueBooking(venueCode, venueBookings);

    if (booking != null) {
      booking.addBooking(bookingDate, customerEmail, attendees, bookingReference);
    } else {
      VenueBooking newBooking = new VenueBooking(venueCode);
      newBooking.addBooking(bookingDate, customerEmail, attendees, bookingReference);
      venueBookings.add(newBooking);
    }
  }

  public String getNextAvailableDate (VenueBooking booking, String systemDate) {
    List<String> bookedDates = booking.getBookedDates();

    if (bookedDates.isEmpty()) {
      return systemDate;
    }

    String nextAvailableDate = getNextDate(systemDate);

    while (bookedDates.contains(nextAvailableDate)) {
      nextAvailableDate = getNextDate(nextAvailableDate);
    }

    return nextAvailableDate;
  }

  public String getNextDate(String date) {
    String[] dateParts = date.split("/");
    int day = Integer.parseInt(dateParts[0]);
    int month = Integer.parseInt(dateParts[1]);
    int year = Integer.parseInt(dateParts[2]);

    day++;
    if (day > getDaysInMonth(month, year)) {
      day = 1;
      month++;

      if (month > 12) {
        month = 1;
        year++;
      }
    }

    return String.format("%02d/%02d/%04d", day, month, year);
  }

  public String[] getBookingReferenceForDate (VenueBooking booking, String date) {
    List<String> references = new ArrayList<>();
    
    for (String bookedDate : booking.getBookedDates()) {
      if (bookedDate.equals(date)) {
        references.add(booking.getBookingReference(bookedDate));
      }
    }

    return references.toArray(new String[0]);
  }

  private VenueBooking findVenueBooking(String venueCode, List<VenueBooking> venueBookings) {
    for (VenueBooking booking : venueBookings) {
      if (booking.getVenueCode().equals(venueCode)) {
        return booking;
      }
    }

    return null;
  }
  
  private int getDaysInMonth(int month, int year) {
    switch (month) {
      case 2:
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
      case 4:
      case 6:
      case 9:
      case 11:
        return 30;
      default:
        return 31;
    }
  }

  public VenueBooking getBooking(String bookingReference) {
    for (VenueBooking booking : venueBookings) {
      if (booking.bookingReferences.contains(bookingReference)) {
        return booking;
      }
    }
  return null;
}
}