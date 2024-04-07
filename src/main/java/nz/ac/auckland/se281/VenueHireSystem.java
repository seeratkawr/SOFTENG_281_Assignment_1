package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;


public class VenueHireSystem {
  // Created 4 new arraylists to store the venue name, code, capacity, and hire fee
  ArrayList<String> venueNames = new ArrayList<>();
  ArrayList<String> venueCodes = new ArrayList<>();
  ArrayList<String> venueCapacity = new ArrayList<>();
  ArrayList<String> venueHireFee = new ArrayList<>();
  Bookings bookings;

  String SystemDate = null;
  
  public VenueHireSystem() {
    bookings = new Bookings();
  }

  public void printVenues() {
    String number = ""; // initialised String to nothing

    //If there are no venues
    if (venueNames.isEmpty()) {
      MessageCli.NO_VENUES.printMessage(); 

      //If there is one venue
    } else if (venueNames.size() == 1) {
      MessageCli.NUMBER_VENUES.printMessage("is", "one", ""); 

      //If there are between 2 and 9 venues
    } else if((venueNames.size() >= 2) && (venueNames.size() < 10)) {
      //Created string array to store the 'words' of the numbers.
      String[] numberWords = {"two", "three", "four", "five", "six", "seven", "eight", "nine"};
      number = numberWords[venueNames.size() - 2];
      MessageCli.NUMBER_VENUES.printMessage("are", number, "s"); 

      //If there are 10 or more venues
    } else if (venueNames.size() >= 10) {
      number = String.valueOf(venueNames.size());
      MessageCli.NUMBER_VENUES.printMessage("are", number, "s"); 
    }

    //For loop to iterate over the venueName, VenueCodes, venueCapacity, and venueHireFee to print.
    for (int i = 0; i < venueNames.size(); i++) { 
      String venue = venueNames.get(i);
      String code = venueCodes.get(i);
      String capacity = venueCapacity.get(i);
      String hireFee = venueHireFee.get(i);
    
      if (SystemDate == null) {
        MessageCli.VENUE_ENTRY.printMessage(venue, code, capacity, hireFee);
      } else {
        String nextAvailable = bookings.getNextAvailableDate(code, SystemDate);
    
        // Prints the venues
        MessageCli.VENUE_ENTRY.printMessage(venue, code, capacity, hireFee, nextAvailable); 
      }
    }
  }

  public void createVenue(String venueName, String venueCode, String capacityInput, String hireFeeInput) {
    //If the venueName input is empty.
    if (venueName.trim().isEmpty()) {
      MessageCli.VENUE_NOT_CREATED_EMPTY_NAME.printMessage(); 
      return;
    }
    
    //If the venueCode exists in the system already
    for (String code : venueCodes) {
      for (String venue : venueNames) {
        if (venueCode.equals(code)) {
          MessageCli.VENUE_NOT_CREATED_CODE_EXISTS.printMessage(venueCode, venue); 
          return;
        }
      }
    }

    //If capacity is a decimals
    try {
      Integer.parseInt(capacityInput);
    } catch (NumberFormatException e) {
       MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", "");
       return;
    }

    //If the capacity input is a word
    if (capacityInput.matches("[a-zA-Z]+")) {
      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", "");
      return;
      //If the capacity input is negative
    } else if (Integer.parseInt(capacityInput) < 0) {
      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", " positive");
      return;
    } 
  
    // If hire fee is a decimal
    try {
      Integer.parseInt(hireFeeInput);
    } catch (NumberFormatException e) {
       MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", "");
       return;
    }

    //If the hire fee input is a word
    if (hireFeeInput.matches("[a-zA-Z]+")) {
      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", "");
      return;
      //If the hire fee input is negative
    } else if (Integer.parseInt(hireFeeInput) < 0) {
      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", " positive");
      return;
    }

    //Store the inputs in the arraylists created.
    venueNames.add(venueName);
    venueCodes.add(venueCode);
    venueCapacity.add(capacityInput);
    venueHireFee.add(hireFeeInput);

    //Print Message for venue created successfully.
    MessageCli.VENUE_SUCCESSFULLY_CREATED.printMessage(venueName, venueCode);
  }

  public void setSystemDate(String dateInput) {
    SystemDate = dateInput;
    MessageCli.DATE_SET.printMessage(dateInput);
  }

  public void printSystemDate() {
    if (SystemDate == null) {
      MessageCli.CURRENT_DATE.printMessage("not set");
    } else {
      MessageCli.CURRENT_DATE.printMessage(SystemDate);
    }
  }

  public void makeBooking(String[] options) {
    String bookingReference = BookingReferenceGenerator.generateBookingReference();
    if (SystemDate == null) {
      MessageCli.BOOKING_NOT_MADE_DATE_NOT_SET.printMessage();
    } else if (venueNames.isEmpty()) {
      MessageCli.BOOKING_NOT_MADE_NO_VENUES.printMessage();
    } else {
      String[] extendedOptions = new String[options.length + 2];
        extendedOptions[0] = options[0];
        extendedOptions[1] = options[1];
        extendedOptions[2] = options[2];
        extendedOptions[3] = options[3];
        extendedOptions[4] = bookingReference;
        extendedOptions[5] = venueHireFee.get(venueCodes.indexOf(options[0]));

      for (int i = 0; i < venueCodes.size(); i++) {
        int minCapacity = (int) (Integer.parseInt(venueCapacity.get(i)) * 0.25);

        if (options[0].equals(venueCodes.get(i))) {
          if (options[1].compareTo(SystemDate) < 0) {
            MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(options[1], SystemDate);
            return;

          } else if (Integer.parseInt(options[3]) < minCapacity) {
            MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(options[3], String.valueOf(minCapacity), venueCapacity.get(i));
            MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(bookingReference, venueNames.get(i), options[1], String.valueOf(minCapacity));
            extendedOptions[3] = String.valueOf(minCapacity);
            bookings.makeBooking(extendedOptions);

          } else {
            List<String> bookedDates = bookings.getBookedDatesForVenue(venueCodes.get(i));
            if (bookedDates.contains(options[1])) {
              MessageCli.BOOKING_NOT_MADE_VENUE_ALREADY_BOOKED.printMessage(venueNames.get(i), options[1]);
              return;

            } else {
              MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(bookingReference, venueNames.get(i), options[1], options[3]);
              bookings.makeBooking(extendedOptions);
              return;

            }
          }
        }
      }
      if (!venueCodes.contains(options[0])) {
        MessageCli.BOOKING_NOT_MADE_VENUE_NOT_FOUND.printMessage(options[0]);
        return;
      }
    }  
  }

  public void printBookings(String venueCode) {
    if (!venueCodes.contains(venueCode)) {
      MessageCli.PRINT_BOOKINGS_VENUE_NOT_FOUND.printMessage(venueCode);
      return;
    }

    int venueIndex = -1;

    for (int i = 0; i < venueCodes.size(); i++) {
      if (venueCode.equals(venueCodes.get(i))) {
        venueIndex = i;
        break;
      }
    }

    List<String> bookedDates = bookings.getBookedDatesForVenue(venueCode);
    if (bookedDates.isEmpty()) {
      MessageCli.PRINT_BOOKINGS_HEADER.printMessage(venueNames.get(venueIndex));
      MessageCli.PRINT_BOOKINGS_NONE.printMessage(venueNames.get(venueIndex));
      return;
    } else {
      MessageCli.PRINT_BOOKINGS_HEADER.printMessage(venueNames.get(venueIndex));
      for (String date : bookedDates) {
        String[] references = bookings.getBookingReferenceForDate(venueCode, date);
        if (references.length > 0 && references != null) {
          for (String reference : references) {
            if (reference != null) {
              MessageCli.PRINT_BOOKINGS_ENTRY.printMessage(reference, date);
            }
          }
        }
      }
    }
  }

  public void addCateringService(String bookingReference, CateringType cateringType) {
    if (bookings.getBooking(bookingReference) == null) {
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage("Catering", bookingReference);
      return;
    } else {
      MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage("Catering (" + cateringType.getName() + ")", bookingReference);
      bookings.addServiceCatering(bookingReference, cateringType);
    }
  }

  public void addServiceMusic(String bookingReference) {
    // TODO implement this method
  }

  public void addServiceFloral(String bookingReference, FloralType floralType) {
    // TODO implement this method
  }

  public void viewInvoice(String bookingReference) {
    if (bookings.getBooking(bookingReference) == null) {
      MessageCli.VIEW_INVOICE_BOOKING_NOT_FOUND.printMessage(bookingReference);
      return;
    } else {
      String cateringPrice = bookings.getCateringPrice(bookingReference);
      String cateringTypeName = bookings.getCateringService(bookingReference);
      MessageCli.INVOICE_CONTENT_CATERING_ENTRY.printMessage(cateringTypeName, cateringPrice);
    }
}

}
