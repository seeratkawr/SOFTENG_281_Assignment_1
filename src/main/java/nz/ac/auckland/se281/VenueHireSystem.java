package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;


public class VenueHireSystem {
  ArrayList<VenueHireSystem> venues = new ArrayList<>();
  
  public VenueHireSystem() {}

  public void printVenues() {
    String number = "";
    if (venues.isEmpty()) {
      MessageCli.NO_VENUES.printMessage();
    } else if (venues.size() == 1) {
      MessageCli.NUMBER_VENUES.printMessage("is", "one", "");
    } else if((venues.size() >= 2) && (venues.size() < 10)) {
      String[] numberWords = {"two", "three", "four", "five", "six", "seven", "eight", "nine"};
      number = numberWords[venues.size() - 2];
      MessageCli.NUMBER_VENUES.printMessage("are", number, "s");
    } else if (venues.size() >= 10) {
      number = String.valueOf(venues.size());
      MessageCli.NUMBER_VENUES.printMessage("are", number, "s");
    }
  }

  public void createVenue(String venueName, String venueCode, String capacityInput, String hireFeeInput) {
    // TODO implement this method
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
