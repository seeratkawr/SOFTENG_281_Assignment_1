package nz.ac.auckland.se281;
import java.util.ArrayList;
import java.util.List;
public class Bookings{
    private List<VenueBooking> venueBookings;
    private BookingOperations bookingOperations;
    private BookingServices bookingServices;

    public Bookings() {
        this.venueBookings = new ArrayList<>();
        this.bookingOperations = new BookingOperations();
        this.bookingServices = new BookingServices(this.venueBookings, this.bookingOperations);
    }

    public void makeBooking(String[] extendedOptions) {
      bookingOperations.makeBooking(extendedOptions, venueBookings);
    }
    public List<String> getBookedDatesForVenue(String venueCode) {
      VenueBooking booking = findVenueBooking(venueCode);
      if (booking != null) {
        return booking.getBookedDates();
      }
      return new ArrayList<>();
    }
    public String getNextAvailableDate (String venueCode, String systemDate) {
      VenueBooking booking = findVenueBooking(venueCode);
      if (booking != null) {
        return bookingOperations.getNextAvailableDate(booking, systemDate);
      }
      return systemDate;
    }
    public String[] getBookingReferenceForDate(String venueCode, String date) {
      VenueBooking booking = findVenueBooking(venueCode);
      if (booking != null) {
        return bookingOperations.getBookingReferenceForDate(booking, date);
      }
      return new String[0];
    }
    public VenueBooking getBooking(String bookingReference) {
      return bookingOperations.getBooking(bookingReference);
    }

    public void addServiceCatering (String bookingReference, Types.CateringType cateringType) {
      bookingServices.addServiceCatering(bookingReference, cateringType);
    }

    public String getCateringService (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      if (booking != null) {
        return booking.getCateringService();
      }
      return null;
    }

    public String getCateringPrice (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      if (booking != null) {
        return booking.getCateringPrice();
      }
      return null;
    }

    public void addServiceMusic (String bookingReference) {
      bookingServices.addServiceMusic(bookingReference);
    }

    public String getMusicService (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      if (booking != null) {
        return booking.getMusicService();
      }
      return null;
    }

    public String getMusicPrice (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      if (booking != null) {
        return booking.getMusicPrice();
      }
      return null;
    }

    public void addServiceFloral (String bookingReference, Types.FloralType floralType) {
      bookingServices.addServiceFloral(bookingReference, floralType);
    }

    public String getFloralService (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      if (booking != null) {
        return booking.getFloralService();
      }
      return null;
    }

    public String getFloralPrice (String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
      if (booking != null) {
        return booking.getFloralPrice();
      }
      return null;
    }
    
    public String[] InvoiceContent(String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);

      if (booking != null) {
        String[] invoiceContent = new String[9];
        invoiceContent[0] = booking.getCateringPrice();
        invoiceContent[1] = booking.getCateringService();
        invoiceContent[2] = booking.getVenueCode();
        invoiceContent[3] = booking.getMusicPrice();
        invoiceContent[4] = booking.getFloralPrice();
        invoiceContent[5] = booking.getFloralService();
        invoiceContent[6] = booking.attendees.get(booking.bookingReferences.indexOf(bookingReference));
        invoiceContent[7] = booking.getBookedDates().get(booking.bookingReferences.indexOf(bookingReference));
        invoiceContent[8] = booking.customerEmails.get(booking.bookingReferences.indexOf(bookingReference));

        return invoiceContent;
      }

      return new String[0];
    }

    // Helper method to find VenueBooking object by venue code
    private VenueBooking findVenueBooking(String venueCode) {
        for (VenueBooking booking : venueBookings) {
            if (booking.getVenueCode().equals(venueCode)) {
                return booking;
            }
        }
        return null;
    }
    // Inner class representing bookings for a specific venue
    private class VenueBooking {
        private String venueCode;
        private List<String> bookedDates;
        private List<String> customerEmails;
        private List<String> attendees;
        private List<String> bookingReferences;
        private String cateringService;
        private String cateringPrice;
        private String musicService;
        private String musicPrice;
        private String floralService;
        private String floralPrice;

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

        public String getVenueCode() {
            return venueCode;
        }

        public void addBooking(String date, String customerEmail, String attendees, String bookingReference) {
            bookedDates.add(date);
            customerEmails.add(customerEmail);
            this.attendees.add(attendees);
            bookingReferences.add(bookingReference);
        }

        public List<String> getBookedDates() {
            return bookedDates;
        }

        public String getBookingReference (String date) {
          int index = bookedDates.indexOf(date);
          if (index != -1 && index < bookingReferences.size()) {
            return bookingReferences.get(index);
          }
          return null;
        }

        public void setCateringType (String cateringType) {
          this.cateringService = cateringType;
        }

        public String getCateringService () {
          return cateringService;
        }

        public void setCateringPrice (String cateringPrice) {
          this.cateringPrice = cateringPrice;
        }

        public String getCateringPrice () {
          return cateringPrice;
        }

        public void setMusicService(String musicService) {
          this.musicService = musicService;
        }

        public String getMusicService () {
          return musicService;
        }

        public void setMusicPrice (String musicPrice) {
          this.musicPrice = musicPrice;
        }

        public String getMusicPrice () {
          return musicPrice;
        }

        public void setFloralType (String floralType) {
          this.floralService = floralType;
        }

        public String getFloralService () {
          return floralService;
        }

        public void setFloralPrice (String floralPrice) {
          this.floralPrice = floralPrice;
        }

        public String getFloralPrice () {
          return floralPrice;
        }
    }
    private class BookingOperations {
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

        if (!bookedDates.isEmpty()) {
          String lastBookingDate = bookedDates.get(bookedDates.size() - 1);
          String[] dateParts = lastBookingDate.split("/");

          int day = Integer.parseInt(dateParts[0]);
          int month = Integer.parseInt(dateParts[1]);
          int year = Integer.parseInt(dateParts[2]);

          while (true) {
            day++;

            if (day > getDaysInMonth(month, year)) {
              day = 1;
              month++;
              if (month > 12) {
                month = 1;
                year++;
              }
            }

            String nextAvailable = String.format("%02d/%02d/%04d", day, month, year);

            if(!bookedDates.contains(nextAvailable)) {
              return nextAvailable;
            }

          }
        }
        return systemDate;
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

  private class BookingServices {
    @SuppressWarnings("unused")
    List<VenueBooking> venueBookings;
    BookingOperations bookingOperations;

    public BookingServices(List<VenueBooking> venueBookings, BookingOperations bookingOperations) {
      this.venueBookings = venueBookings;
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
        String musicService = "Y";
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
}