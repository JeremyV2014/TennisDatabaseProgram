/*
 *    Class Name: TennisMatchesContainer
 *    Interface:  TennisMatchesContainerInterface
 *    Package:    TennisDatabase
 *    Purpose:    Container for storing a sorted list of all TennisMatch objects
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   06/08/2018
 */

package TennisDatabase;

// import javafx classes
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

// import other needed classes
import java.util.LinkedList;
import java.util.Iterator;
import java.io.*;

public class TennisMatchesContainer implements TennisMatchesContainerInterface {   
   private LinkedList<TennisMatch> matchesList;   
   
   // Contructor - Default creates an empty JCF LinkedList of TennisMatch objects
   public TennisMatchesContainer() {
      matchesList = new LinkedList<TennisMatch>();
   }
   
   // Desc.:   Insert a TennisMatch object (reference) into this container.
   // Input:   A TennisMatch object (reference).
   // Output:  Throws an exception if match cannot be inserted in this container.
   public void insertMatch( TennisMatch m )
      throws TennisDatabaseRuntimeException {
      if (this.matchesList.contains(m)) {
         throw new TennisDatabaseRuntimeException("Error! Match already exists!");
      }
      
      if (this.matchesList.isEmpty()) { this.matchesList.add(m); }
      else {
         Iterator<TennisMatch> iter = matchesList.iterator(); // Get the iterator for the matches list
         int i = 0;
         while (iter.hasNext() && (m.compareTo(iter.next()) > 0)) {i++;}
         
         matchesList.add(i, m);
      }
   }
   
   // Desc.:   Wraps matches list in an ObservableList
   // Output.: Returns an ObservableList object of TennisMatches
   public ObservableList<TennisMatch> getMatchesList() {
      return FXCollections.observableList(matchesList);
   }
   
   // Desc.:   Prints all tennis matches to the console.
   // Output:  Throws an exception if there are no matches in this container.
   public void printAllMatches() {
      Iterator<TennisMatch> iter = matchesList.iterator();
      
      if (!iter.hasNext()) { throw new TennisDatabaseRuntimeException("Error printing all matches! No matches to print!"); }
      
      while (iter.hasNext()) {
         iter.next().print();
      }
   }
   
   // Desc.:   Writes all tennis matches to a text file.
   // Input:   Preconfigured PrintWriter object to output data to file.
   // Output:  All matches in database formatted in a text file.
   public void matchesToFile(PrintWriter writer) {
      // Create an iterator for the matches list
      Iterator<TennisMatch> iter = matchesList.iterator();
      
      // Iterate through list and output all formatted matches to text file
      while( iter.hasNext() ) {
         TennisMatch m = iter.next();
         String p1Id = m.getPlayer1Id();
         String p2Id = m.getPlayer2Id();
         MatchDate date = m.getDate();
         String dateStr = String.format("%1$04d%2$02d%3$02d", date.getYear() ,date.getMonth(), date.getDay() );
         String tournament = m.getTournament();
         String score = m.getScore();
         
         writer.println("MATCH/" + p1Id + "/" + p2Id + "/" + dateStr + "/" + tournament + "/" + score);
      }
   }
}
// © 2018 Jeremy Maxey-Vesperman