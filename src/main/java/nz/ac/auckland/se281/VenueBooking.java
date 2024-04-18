package nz.ac.auckland.se281;
import java.util.ArrayList;
import java.util.List;

public class VenueBooking extends Bookings {
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

  public VenueBooking(String venueCode) {
    super();
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