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
  Services services;

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
    //Sets the system date to the input
    SystemDate = dateInput;
    //Prints the date set successfully
    MessageCli.DATE_SET.printMessage(dateInput);
  }

  public void printSystemDate() {
    if (SystemDate == null) {
      //If the system date is not set, print message
      MessageCli.CURRENT_DATE.printMessage("not set");
    } else {
      //If the system date is set, print the date
      MessageCli.CURRENT_DATE.printMessage(SystemDate);
    }
  }

  public void makeBooking(String[] options) {
    String bookingReference = BookingReferenceGenerator.generateBookingReference();
    if (SystemDate == null) {
      //System date must be set to make a booking, if not print message
      MessageCli.BOOKING_NOT_MADE_DATE_NOT_SET.printMessage();
    } else if (venueNames.isEmpty()) {
      //There must be venues in the system to make a booking, if not print message
      MessageCli.BOOKING_NOT_MADE_NO_VENUES.printMessage();
    } else {
      //Create an extended array to store the options inputted when making booking
      String[] extendedOptions = new String[options.length + 2];
      extendedOptions[0] = options[0];
      extendedOptions[1] = options[1];
      extendedOptions[2] = options[2];
      extendedOptions[3] = options[3];
      extendedOptions[4] = bookingReference;

      for (int i = 0; i < venueCodes.size(); i++) {
        //Calculate the minimum capacity of the venue. 
        int minCapacity = (int) (Integer.parseInt(venueCapacity.get(i)) * 0.25);

        //If the venueCode inputted is in the system
        if (options[0].equals(venueCodes.get(i))) {
          //If the date inputted is before the system date, print error message
          if (options[1].compareTo(SystemDate) < 0) {
            MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(options[1], SystemDate);
            return;
            //If the capacity inputted is less than the minimum capacity, adjust the capacity to the minimum capacity and print a message
          } else if (Integer.parseInt(options[3]) < minCapacity) {
            MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(options[3], String.valueOf(minCapacity), venueCapacity.get(i));
            MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(bookingReference, venueNames.get(i), options[1], String.valueOf(minCapacity));
            extendedOptions[3] = String.valueOf(minCapacity);
            bookings.makeBooking(extendedOptions);
          } else {
            List<String> bookedDates = bookings.getBookedDatesForVenue(venueCodes.get(i));
            //If the date inputted is already booking, print error message
            if (bookedDates.contains(options[1])) {
              MessageCli.BOOKING_NOT_MADE_VENUE_ALREADY_BOOKED.printMessage(venueNames.get(i), options[1]);
              return;
              //If the date inputted is not already booking, make the booking, print success message
            } else {
              MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(bookingReference, venueNames.get(i), options[1], options[3]);
              bookings.makeBooking(extendedOptions);
              return;

            }
          }
        }
      }
      //If the venueCode inputted is not in the system, print error message
      if (!venueCodes.contains(options[0])) {
        MessageCli.BOOKING_NOT_MADE_VENUE_NOT_FOUND.printMessage(options[0]);
        return;
      }
    }  
  }

  public void printBookings(String venueCode) {
    //If the venueCode inputted is not in the system, print error message
    if (!venueCodes.contains(venueCode)) {
      MessageCli.PRINT_BOOKINGS_VENUE_NOT_FOUND.printMessage(venueCode);
      return;
    }

    int venueIndex = -1;

    //Find the index of the venueCode inputted
    for (int i = 0; i < venueCodes.size(); i++) {
      if (venueCode.equals(venueCodes.get(i))) {
        venueIndex = i;
        break;
      }
    }

    List<String> bookedDates = bookings.getBookedDatesForVenue(venueCode);

    //If there are no booked dates for the venue, print message that there are no bookings
    if (bookedDates.isEmpty()) {
      MessageCli.PRINT_BOOKINGS_HEADER.printMessage(venueNames.get(venueIndex));
      MessageCli.PRINT_BOOKINGS_NONE.printMessage(venueNames.get(venueIndex));
      return;
      //If there are booked dates for the venue, print the bookings
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
    //If there is no booking connected to the booking reference, print error message
    if (bookings.getBooking(bookingReference) == null) {
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage("Catering", bookingReference);
      return;
      //Print a success message, and add the catering service to the booking.
    } else {
      MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage("Catering (" + cateringType.getName() + ")", bookingReference);
      bookings.addServiceCatering(bookingReference, cateringType);
    }
  }

  public void addServiceMusic(String bookingReference) {
    //If there is no booking connected to the booking reference, print error message
    if (bookings.getBooking(bookingReference) == null) {
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage("Music", bookingReference);
      return;
      //Print a success message, and add the music service to the booking
    } else {
      MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage("Music", bookingReference);
      bookings.addServiceMusic(bookingReference);
    }
  }

  public void addServiceFloral(String bookingReference, FloralType floralType) {
    //If there is no booking connected to the booking reference, print error message
    if (bookings.getBooking(bookingReference) == null) {
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage("Floral", bookingReference);
      return;
      //Print a success message, and add the floral service to the booking
    } else {
      MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage("Floral (" + floralType.getName() +")", bookingReference);
      bookings.addServiceFloral(bookingReference, floralType);
    }
  }

  public void viewInvoice(String bookingReference) {
    String[] invoiceContent = bookings.InvoiceContent(bookingReference);
    String venueCode = "";
    String venueFee = "0";
    String attendees = "0";
    String partyDate = "";
    String venueName = "";
    String customerEmail = "";
    String musicPrice = "0";
    String cateringPrice = "0";
    String cateringTypeName = "";
    String floralPrice = "0";
    String floralTypeName = "";
    int totalPrice = 0;
    
    //if there is no booking connected to the booking reference, print error message
    if (bookings.getBooking(bookingReference) == null) {
      MessageCli.VIEW_INVOICE_BOOKING_NOT_FOUND.printMessage(bookingReference);
      return;
      //Print the invoice content
    } else {
      venueCode = invoiceContent[2];
      attendees = invoiceContent[6];
      partyDate = invoiceContent[7];
      venueName = venueNames.get(venueCodes.indexOf(venueCode));
      venueFee = venueHireFee.get(venueCodes.indexOf(venueCode));
      customerEmail = invoiceContent[8];
      totalPrice += Integer.parseInt(venueFee);

      MessageCli.INVOICE_CONTENT_TOP_HALF.printMessage(
        bookingReference, customerEmail, SystemDate, partyDate, attendees, venueName
      );

      MessageCli.INVOICE_CONTENT_VENUE_FEE.printMessage(venueFee);

      //If there is a catering service in the booking, print the catering service invoice
      if (bookings.getCateringService(bookingReference) != null) {
        cateringPrice = invoiceContent[0];
        cateringTypeName = invoiceContent[1];
        MessageCli.INVOICE_CONTENT_CATERING_ENTRY.printMessage(cateringTypeName, cateringPrice);
        totalPrice += Integer.parseInt(cateringPrice);
      }

      //If there is a music service in the booking, print the music service invoice
      if (bookings.getMusicService(bookingReference) != null) {
        musicPrice = invoiceContent[3];
        MessageCli.INVOICE_CONTENT_MUSIC_ENTRY.printMessage(musicPrice);
        totalPrice += Integer.parseInt(musicPrice);
      }

      //If there is a floral service in the booking, print the floral service invoice
      if (bookings.getFloralService(bookingReference) != null) {
        floralPrice = invoiceContent[4];
        floralTypeName = invoiceContent[5];
        MessageCli.INVOICE_CONTENT_FLORAL_ENTRY.printMessage(floralTypeName, floralPrice);
        totalPrice += Integer.parseInt(floralPrice);
      }
      
      MessageCli.INVOICE_CONTENT_BOTTOM_HALF.printMessage(String.valueOf(totalPrice));
    }
  }
}