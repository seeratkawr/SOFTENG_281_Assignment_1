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
        this.bookingServices = new BookingServices();
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

    public int totalPrice (String bookingReference) {
      return bookingServices.totalPrice(bookingReference);
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
      private String attendees;
      private List<String> bookingReferences;
      private String hireFee;
      private Types.CateringType cateringType;
  
      public VenueBooking(String venueCode) {
          this.venueCode = venueCode;
          this.bookedDates = new ArrayList<>();
          this.customerEmails = new ArrayList<>();
          this.attendees = new String();
          this.bookingReferences = new ArrayList<>();
          this.hireFee = new String();
          this.cateringType = null;
      }
  
      public String getVenueCode() {
          return venueCode;
      }
  
      public String getAttendees() {
          return attendees;
      }
  
      public Types.CateringType getCateringType() {
          return cateringType;
      }

        public void addBooking(String date, String customerEmail, String attendees, String bookingReference, String hireFee, Types.CateringType cateringType) {
            bookedDates.add(date);
            customerEmails.add(customerEmail);
            this.attendees = attendees;
            bookingReferences.add(bookingReference);
            this.cateringType = cateringType;
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
    }

    private class BookingOperations {
      public void makeBooking(String[] extendedOptions, List<VenueBooking> venueBookings) {
        String venueCode = extendedOptions[0];
        String bookingDate = extendedOptions[1];
        String customerEmail = extendedOptions[2];
        String attendees = extendedOptions[3];
        String bookingReference = extendedOptions[4];
        String hireFee = extendedOptions[5];
        Types.CateringType cateringType = null;

        VenueBooking booking = findVenueBooking(venueCode, venueBookings);
        if (booking != null) {
          booking.addBooking(bookingDate, customerEmail, attendees, bookingReference, hireFee, cateringType);
        } else {
          VenueBooking newBooking = new VenueBooking(venueCode);
          newBooking.addBooking(bookingDate, customerEmail, attendees, bookingReference, hireFee, cateringType);
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
      public void addServiceCatering (String bookingReference, Types.CateringType cateringType) {
        VenueBooking booking = bookingOperations.getBooking(bookingReference);

        if (booking != null) {
          booking.cateringType = cateringType;
        }
      }

      public int totalPrice(String bookingReference) {
        VenueBooking booking = bookingOperations.getBooking(bookingReference);
    
        if (booking != null) {
            int cateringCost = booking.getCateringType().getCostPerPerson() * Integer.parseInt(booking.getAttendees());
            int venueHireFee = Integer.parseInt(booking.hireFee);
            int totalPrice = cateringCost + venueHireFee;
            return totalPrice;
        }
        
        return 0;
    }
    
    }

    public String[] getInvoiceContent(String bookingReference) {
      VenueBooking booking = bookingOperations.getBooking(bookingReference);
    
      if (booking != null) {
        String[] invoiceContent = new String[8];
        invoiceContent[0] = bookingReference;
        
        // Ensure bookedDates list is not empty before accessing the first element
        if (!booking.bookedDates.isEmpty()) {
          invoiceContent[2] = booking.bookedDates.get(0);
        }
        invoiceContent[3] = booking.attendees;
        invoiceContent[4] = booking.venueCode;
        invoiceContent[5] = booking.hireFee;
    
        if (booking.cateringType != null) {
          invoiceContent[6] = booking.cateringType.getName();
          int cateringCost = bookingServices.totalPrice(bookingReference) - Integer.parseInt(booking.hireFee);
          invoiceContent[7] = String.valueOf(cateringCost);
        }
    
        return invoiceContent;
      }
      return new String[0];
    }
    
}
