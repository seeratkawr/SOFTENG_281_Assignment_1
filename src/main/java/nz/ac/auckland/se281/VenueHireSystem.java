package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;


public class VenueHireSystem {
  // Created 4 new arraylists to store the venue name, code, capacity, and hire fee
  ArrayList<String> venueNames = new ArrayList<>();
  ArrayList<String> venueCodes = new ArrayList<>();
  ArrayList<String> venueCapacity = new ArrayList<>();
  ArrayList<String> venueHireFee = new ArrayList<>();
  
  public VenueHireSystem() {}

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

      //Prints the venues
      MessageCli.VENUE_ENTRY.printMessage(venue, code, capacity, hireFee); 
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
    // TODO implement this method
  }

  public void printSystemDate() {
    // TODO implement this method
  }

  public void makeBooking(String[] options) {
    // TODO implement this method
  }

  public void printBookings(String venueCode) {
    // TODO implement this method
  }

  public void addCateringService(String bookingReference, CateringType cateringType) {
    // TODO implement this method
  }

  public void addServiceMusic(String bookingReference) {
    // TODO implement this method
  }

  public void addServiceFloral(String bookingReference, FloralType floralType) {
    // TODO implement this method
  }

  public void viewInvoice(String bookingReference) {
    // TODO implement this method
  }
}
