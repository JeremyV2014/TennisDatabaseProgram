/*
 *    Class Name: TennisPlayer
 *    Interface:  TennisPlayerInterface
 *    Package:    TennisDatabase
 *    Purpose:    Provides a container for holding player data. Provides functions/methods to compare and print players.
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   05/04/2018
 */

package TennisDatabase;

public class TennisPlayer implements TennisPlayerInterface {
   /* Instance Variables */
   private String uid,        // Unique ID of tennis player
                  firstName,  // First name of tennis player
                  lastName,   // Last name of tennis player
                  country;    // Country of player
   private int year;          // Year the tennis player was born
   private WinLossRec winLossRec;
   
   /* Constructors */   
   // Constructor: UID - Minimum to create tennis player
   TennisPlayer(String uid) { 
      setUID(uid);
      setFirstName("UNKNOWN");
      setLastName("UNKNOWN");
      setYear(-1);
      setCountry("UNKNOWN");
      winLossRec = new WinLossRec();
   }
   
   // Constructor: All fields
   TennisPlayer(String uid, String firstName, String lastName, int year, String country) {
      setUID(uid);
      setFirstName(firstName);
      setLastName(lastName);
      setYear(year);
      setCountry(country);
      winLossRec = new WinLossRec();
   }
   
   /* Setters */
   // Unique IDs should be immutable. Private so that only TennisPlayer can set the uid and only called from the constructor of the class
   private void setUID (String uid) { this.uid = uid.toUpperCase(); }
   public void setFirstName (String firstName) { this.firstName = firstName.toUpperCase(); }
   public void setLastName (String lastName) { this.lastName = lastName.toUpperCase(); }
   public void setYear (int year) { this.year = year; }
   public void setCountry (String country) { this.country = country.toUpperCase(); }
   public void setWinLoss (WinLossRec wlr) { this.winLossRec = wlr; }
   
   /* Getters */
   public String getId () { return this.uid; }
   public String getFirstName () { return this.firstName; }
   public String getLastName () { return this.lastName; }
   public int getBirthYear () { return this.year; }
   public String getCountry () { return this.country; }
   public WinLossRec getWinLoss() { return this.winLossRec; }
   public String getWinLossString() { return this.getWinLoss().toString(); }
   
   /* Functions / Methods */
   // Desc.:   Increment this player's win counter
   public void incWinRecord() { this.getWinLoss().incWin(); }
   
   // Desc.:   Increment this player's loss counter
   public void incLossRecord() { this.getWinLoss().incLoss(); }
   
   // Desc.:   Prints this tennis player on the console.
   // Output:  Formatted tennis player data printed to text-based console
   public void print() {
      System.out.print(this.getId() + ": " + this.getFirstName() + " " + this.getLastName() + ", ");
      System.out.printf("%04d",this.getBirthYear());
      System.out.println(", " + this.getCountry() + ", " + this.getWinLossString() );
   }
   
   // Desc.:   Determines sorting order of this TennisPlayer compared to an input TennisPlayer.
   //          Sorting order: Identical reference -> Unique ID -> First Name -> Last Name -> Date of Birth -> Country
   // Input:   TennisPlayer object (reference)
   // Output:  Integer indicating sorting order:
   //             Negative = this TennisPlayer comes BEFORE input TennisPlayer
   //             Zero = this TennisPlayer is EQUAL to input TennisPlayer
   //             Positve = this TennisPlayer comes AFTER input TennisPlayer
   public int compareTo(TennisPlayer tp) {
      final int BEFORE = -1;
      final int EQUAL = 0;
      final int AFTER = 1;
      
      
      // Optimization to first check if we are comparing the same object
      if (this == tp) { return EQUAL; }
      
      // Compare UID first
      int comparison = this.getId().compareTo(tp.getId());
      if (comparison != EQUAL) { return comparison; }
      
      // Sort by first name
      comparison = this.getFirstName().compareTo(tp.getFirstName());
      if (comparison != EQUAL) { return comparison; }
      
      // Sort by last name
      comparison = this.getLastName().compareTo(tp.getLastName());
      if (comparison != EQUAL) { return comparison; }
      
      // Sort by date-of-birth
      if (this.getBirthYear() < tp.getBirthYear()) { return BEFORE; }
      if (this.getBirthYear() > tp.getBirthYear()) { return AFTER; }
      
      // Sort by country
      comparison = this.getCountry().compareTo(tp.getCountry());
      if (comparison != EQUAL) { return comparison; }
      
      // Contents of players are equal
      return EQUAL;
   }
}
// © 2018 Jeremy Maxey-Vesperman