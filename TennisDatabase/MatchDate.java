/*
 *    Class Name: MatchDate
 *    Interface:  MatchDateInterface
 *    Package:    TennisDatabase
 *    Purpose:    Provides a container for holding match dates. Provides functions/methods to compare and print match dates.
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   05/04/2018
 */

package TennisDatabase;

class MatchDate implements MatchDateInterface {
   private int year, month, day;
   
   /* Constructors */
   public MatchDate (int year, int month, int day) {
      setYear(year);
      setMonth(month);
      setDay(day);
   }
   
   /* Setters */
   public void setYear (int year)
      throws TennisDatabaseRuntimeException {
      // Throw exception if year is negative
      if (year < 0) {
         throw new TennisDatabaseRuntimeException("Error setting match year! " + year + " is not a valid year. Year must be integer greater than 0.");
      }
      
      // Set year
      this.year = year;
   }
   public void setMonth (int month)
      throws TennisDatabaseRuntimeException {
      // Throw exception if month is not between 1 and 12
      if ((month < 1) || (month > 12)) {
         throw new TennisDatabaseRuntimeException("Error setting match month! " + month + " is not a valid month. Month must be integer 1 - 12.");
      }
      
      // Set month
      this.month = month;
   }
   public void setDay (int day) 
      throws TennisDatabaseRuntimeException {
      // Throw exception if day is not between 1 and 31. Don't enforce proper day for the set month
      if ((day < 1) || (day > 31)) {
         throw new TennisDatabaseRuntimeException("Error setting match day! " + day + " is not a valid day. Day must be integer 1 - 31.");
      }
      
      // Set day
      this.day = day;
   }
   
   /* Getters */
   public int getYear() { return this.year; }
   public int getMonth() { return this.month; }
   public int getDay() { return this.day; }
   public String getDateStr () { return (String.format("%1$02d/%2$02d/%3$04d", this.getMonth(), this.getDay(), this.getYear())); }
   
   /* Functions / Methods */
   
   // Desc.:   Determines sorting order of this MatchDate compared to an input MatchDate.
   // Input:   MatchDate object (reference)
   // Output:  Integer indicating sorting order:
   //             -1 = this MatchDate is NEWER input MatchDate
   //             0 = this MatchDate is EQUAL to input MatchDate
   //             1 = this MatchDate is OLDER input MatchDate
   public int compareTo(MatchDate md) {
      int NEWER = -1;
      int EQUAL = 0;
      int OLDER = 1;
      
      int thisDate = ((this.getYear() * 10000) + (this.getMonth() * 100) + (this.getDay()));
      int mdDate = ((md.getYear() * 10000) + (md.getMonth() * 100) + (md.getDay()));
      
      if (thisDate < mdDate) { return OLDER; }
      if (thisDate > mdDate) { return NEWER; }
      
      // Dates are equal
      return EQUAL;
   }
}
// © 2018 Jeremy Maxey-Vesperman