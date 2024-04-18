package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class Bookings {
  // This class is responsible for managing bookings for venues
  protected List<VenueBooking> venueBookings;
  protected BookingOperations bookingOperations;
  protected Services cateringService;
  protected Services musicService;
  protected Services floralService;

  // Constructor for Bookings class
  public Bookings() {
    this.venueBookings = new ArrayList<>();
    this.bookingOperations = new BookingOperations();
    this.cateringService = new Catering(bookingOperations);
    this.musicService = new Music(bookingOperations);
    this.floralService = new Floral(bookingOperations);
  }

  // Calls the makeBooking method in BookingOperations
  public void makeBooking(String[] extendedOptions) {
    bookingOperations.makeBooking(extendedOptions, venueBookings);
  }

  // Calls the getBookedDatesForVenue method in VenueBooking
  public List<String> getBookedDatesForVenue(String venueCode) {
    // This method returns a list of booked dates for a venue
    VenueBooking booking = findVenueBooking(venueCode);

    // If the booking is not null, return the booked dates, otherwise return an empty list
    if (booking != null) {
      return booking.getBookedDates();
    }

    return new ArrayList<>();
  }

  // Calls the getNextAvailableDate method in BookingOperations
  public String getNextAvailableDate(String venueCode, String systemDate) {
    // This method returns the next available date for a venue
    VenueBooking booking = findVenueBooking(venueCode);

    // If the booking is not null, return the next available date, otherwise return the system date
    if (booking != null) {
      return bookingOperations.getNextAvailableDate(booking, systemDate);
    }

    return systemDate;
  }

  // Calls the getBookingReferenceForDate method in BookingOperations
  public String[] getBookingReferenceForDate(String venueCode, String date) {
    // This method returns the booking reference for a given date
    VenueBooking booking = findVenueBooking(venueCode);

    // If the booking is not null, return the booking reference for the date, otherwise return an
    // empty array
    if (booking != null) {
      return bookingOperations.getBookingReferenceForDate(booking, date);
    }

    return new String[0];
  }

  // Calls the getBooking method in BookingOperations
  public VenueBooking getBooking(String bookingReference) {
    return bookingOperations.getBooking(bookingReference);
  }

  // Calls the Catering addService method, from the abstract Services class
  public void addServiceCatering(String bookingReference, Types.CateringType cateringType) {
    ((Catering) cateringService).setCateringType(cateringType);
    cateringService.addService(bookingReference);
  }

  // Calls the Music addService method, from the abstract Service class
  public void addServiceMusic(String bookingReference) {
    musicService.addService(bookingReference);
  }

  // Calls the Floral addService method, from the abstract Service class
  public void addServiceFloral(String bookingReference, Types.FloralType floralType) {
    ((Floral) floralService).setFloralType(floralType);
    floralService.addService(bookingReference);
  }

  // Calls the getCateringService method in VenueBooking
  public String getCateringService(String bookingReference) {
    // This method returns the catering service for a booking
    VenueBooking booking = bookingOperations.getBooking(bookingReference);

    // If the booking is not null, return the catering service, otherwise return null
    if (booking != null) {
      return booking.getCateringService();
    }

    return null;
  }

  // Calls the getCateringPrice method in VenueBooking
  public String getCateringPrice(String bookingReference) {
    // This method returns the catering price for a booking
    VenueBooking booking = bookingOperations.getBooking(bookingReference);

    // If the booking is not null, return the catering price, otherwise return null
    if (booking != null) {
      return booking.getCateringPrice();
    }

    return null;
  }

  // Calls the getMusicService method in VenueBooking
  public String getMusicService(String bookingReference) {
    // This method returns the music service for a booking
    VenueBooking booking = bookingOperations.getBooking(bookingReference);

    // If the booking is not null, return the music service, otherwise return null
    if (booking != null) {
      return booking.getMusicService();
    }

    return null;
  }

  // Calls the getMusicPrice method in VenueBooking
  public String getMusicPrice(String bookingReference) {
    VenueBooking booking = bookingOperations.getBooking(bookingReference);

    // If the booking is not null return the music price, otherwise return null
    if (booking != null) {
      return booking.getMusicPrice();
    }

    return null;
  }

  // Calls the getFloralService method in VenueBooking
  public String getFloralService(String bookingReference) {
    VenueBooking booking = bookingOperations.getBooking(bookingReference);

    // If the booking is not null, return the floral service, otherwise return null
    if (booking != null) {
      return booking.getFloralService();
    }

    return null;
  }

  // Calls the getFloralPrice method in VenueBooking
  public String getFloralPrice(String bookingReference) {
    VenueBooking booking = bookingOperations.getBooking(bookingReference);

    // If the booking is not null return the floral price, otherwise return null
    if (booking != null) {
      return booking.getFloralPrice();
    }

    return null;
  }

  // Gathers the invoice content for a booking
  public String[] InvoiceContent(String bookingReference) {
    VenueBooking booking = bookingOperations.getBooking(bookingReference);

    // If the booking is not null return the invoice content, otherwise return an empty array
    if (booking != null) {
      // Create an array to store the invoice content
      String[] invoiceContent = new String[9];
      invoiceContent[0] = booking.getCateringPrice();
      invoiceContent[1] = booking.getCateringService();
      invoiceContent[2] = booking.getVenueCode();
      invoiceContent[3] = booking.getMusicPrice();
      invoiceContent[4] = booking.getFloralPrice();
      invoiceContent[5] = booking.getFloralService();
      invoiceContent[6] =
          booking.attendees.get(booking.bookingReferences.indexOf(bookingReference));
      invoiceContent[7] =
          booking.getBookedDates().get(booking.bookingReferences.indexOf(bookingReference));
      invoiceContent[8] =
          booking.customerEmails.get(booking.bookingReferences.indexOf(bookingReference));

      return invoiceContent;
    }

    return new String[0];
  }

  // Helper method to find VenueBooking object by venue code
  protected VenueBooking findVenueBooking(String venueCode) {
    for (VenueBooking booking : venueBookings) {
      if (booking.getVenueCode().equals(venueCode)) {
        return booking;
      }
    }

    return null;
  }

  protected class VenueBooking {
    // This class represents bookings for a specific venue
    protected String venueCode;
    protected List<String> bookedDates;
    protected List<String> customerEmails;
    protected List<String> attendees;
    protected List<String> bookingReferences;
    protected String cateringService;
    protected String cateringPrice;
    protected String musicService;
    protected String musicPrice;
    protected String floralService;
    protected String floralPrice;

    // Constructor for VenueBooking class
    public VenueBooking(String venueCode) {
      this.venueCode = venueCode;
      this.bookedDates = new ArrayList<>();
      this.customerEmails = new ArrayList<>();
      this.attendees = new ArrayList<>();
      this.bookingReferences = new ArrayList<>();
      this.cateringService = null;
      this.cateringPrice = null;
      this.musicService = null;
      this.musicPrice = null;
      this.floralService = null;
      this.floralPrice = null;
    }

    // Getter for venueCode
    public String getVenueCode() {
      return venueCode;
    }

    // Adds a booking to the venue
    public void addBooking(
        String date, String customerEmail, String attendees, String bookingReference) {
      bookedDates.add(date);
      customerEmails.add(customerEmail);
      this.attendees.add(attendees);
      bookingReferences.add(bookingReference);
    }

    // Getter for bookedDates
    public List<String> getBookedDates() {
      return bookedDates;
    }

    // Getter for bookingReference for given date
    public String getBookingReference(String date) {
      int index = bookedDates.indexOf(date);
      if (index != -1 && index < bookingReferences.size()) {
        return bookingReferences.get(index);
      }

      return null;
    }

    // Setter for cateringService
    public void setCateringType(String cateringType) {
      this.cateringService = cateringType;
    }

    // Getter for cateringService
    public String getCateringService() {
      return cateringService;
    }

    // Setter for cateringPrice
    public void setCateringPrice(String cateringPrice) {
      this.cateringPrice = cateringPrice;
    }

    // Getter for cateringPrice
    public String getCateringPrice() {
      return cateringPrice;
    }

    // Setter for musicService
    public void setMusicService(String musicService) {
      this.musicService = musicService;
    }

    // Getter for musicService
    public String getMusicService() {
      return musicService;
    }

    // Setter for musicPrice
    public void setMusicPrice(String musicPrice) {
      this.musicPrice = musicPrice;
    }

    // Getter for musicPrice
    public String getMusicPrice() {
      return musicPrice;
    }

    // Setter for floralService
    public void setFloralType(String floralType) {
      this.floralService = floralType;
    }

    // Getter for floralService
    public String getFloralService() {
      return floralService;
    }

    // Setter for floralPrice
    public void setFloralPrice(String floralPrice) {
      this.floralPrice = floralPrice;
    }

    // Getter for floralPrice
    public String getFloralPrice() {
      return floralPrice;
    }
  }

  // Inner class representing booking operations
  protected class BookingOperations {
    // Makes a booking and adds it to the venueBookings list
    public void makeBooking(String[] extendedOptions, List<VenueBooking> venueBookings) {
      String venueCode = extendedOptions[0];
      String bookingDate = extendedOptions[1];
      String customerEmail = extendedOptions[2];
      String attendees = extendedOptions[3];
      String bookingReference = extendedOptions[4];

      VenueBooking booking = findVenueBooking(venueCode, venueBookings);

      // If the booking is not null, add the booking, otherwise create a new booking and add it
      if (booking != null) {
        booking.addBooking(bookingDate, customerEmail, attendees, bookingReference);
      } else {
        VenueBooking newBooking = new VenueBooking(venueCode);
        newBooking.addBooking(bookingDate, customerEmail, attendees, bookingReference);
        venueBookings.add(newBooking);
      }
    }

    // Gets the next available date for a venue
    public String getNextAvailableDate(VenueBooking booking, String systemDate) {
      List<String> bookedDates = booking.getBookedDates();

      // If there are no booked dates, return the system date
      if (bookedDates.isEmpty()) {
        return systemDate;
      }

      // Get the nextAvailableDate
      String nextAvailableDate = getNextDate(systemDate);

      // If the nextAvailableDate is already booked, keep incrementing the date until it is not
      // booked
      while (bookedDates.contains(nextAvailableDate)) {
        nextAvailableDate = getNextDate(nextAvailableDate);
      }

      // Return the next available date
      return nextAvailableDate;
    }

    // Gets the next date
    public String getNextDate(String date) {
      // Split the date into day, month, and year
      String[] dateParts = date.split("/");
      int day = Integer.parseInt(dateParts[0]);
      int month = Integer.parseInt(dateParts[1]);
      int year = Integer.parseInt(dateParts[2]);

      // Increment the day
      // If the day is greater than the number of days in the month, increment the month and set the
      // day to 1
      // If the month is greater than 12, increment the year and set the month to 1
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

    // Gets the booking reference for a given date
    public String[] getBookingReferenceForDate(VenueBooking booking, String date) {
      List<String> references = new ArrayList<>();

      // For each booked date, if the date matches the given date, add the booking reference to the
      // list
      for (String bookedDate : booking.getBookedDates()) {
        if (bookedDate.equals(date)) {
          references.add(booking.getBookingReference(bookedDate));
        }
      }

      // Return the list of booking references as an array
      return references.toArray(new String[0]);
    }

    // Helper method to find the VenueBooking object by venue code
    protected VenueBooking findVenueBooking(String venueCode, List<VenueBooking> venueBookings) {
      for (VenueBooking booking : venueBookings) {
        if (booking.getVenueCode().equals(venueCode)) {
          return booking;
        }
      }

      return null;
    }

    // Gets the number of days in a month
    protected int getDaysInMonth(int month, int year) {
      // Return the number of days in the month, depending on the month and year
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

    // Gets the booking by booking reference
    public VenueBooking getBooking(String bookingReference) {
      for (VenueBooking booking : venueBookings) {
        if (booking.bookingReferences.contains(bookingReference)) {
          return booking;
        }
      }
      return null;
    }
  }
}
