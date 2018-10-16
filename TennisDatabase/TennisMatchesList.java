/*
 *    Class Name: TennisPlayer
 *    Interface:  TennisPlayerInterface
 *    Package:    TennisDatabase
 *    Purpose:    Singly-Linked List for storing a specific player's matches.
 *                Provides methods to insert a match and print all matches of player
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   06/08/2018
 */

package TennisDatabase;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.LinkedList;

class TennisMatchesList implements TennisMatchesListInterface {
   private TennisMatchNode head;
   private int numOfMatches;
   
   // Desc.:   Insert a TennisMatch object (reference) into this list.
   // Input:   A TennisMatch object (reference).
   // Output:  Throws an exception if match cannot be inserted in this list.
   public void insertMatch( TennisMatch m )
      throws TennisDatabaseRuntimeException {
      if (numOfMatches == 0) { // No matches exist... insert node at head
         head = new TennisMatchNode(m);
      } else { // Otherwise... find insertion point
         TennisMatchNode prev = null, curr = head;
         
         do {
            if (m.compareTo(curr.getMatch()) < 0) { break; } // Exit loop if match belongs in front of current node
            prev = curr;
            curr = curr.getNext(); // Advance through list
         } while (curr != null); // Stop when end of list is reached
         
         if ((curr == head) && (prev == null)) {
            // Check if insert before head (Full loop iteration didn't happen. Therefore current is still head and prev is still null)
            head = new TennisMatchNode(m, curr); // Inserted player node becomes head node. Update pointer
         } else { // Perform insertion other than before head
            TennisMatchNode mNode = new TennisMatchNode(m, curr); // Insert before current node
            prev.setNext(mNode); // Set next pointer of previous node to point to new node
         }
      }
      numOfMatches++; // Increment list size counter
   }
   
   // Desc.:   Creates an ObservableList from matches list for tableview
   // Output:  Returns an ObservableList of TennisMatch objects
   public ObservableList<TennisMatch> getMatches()
      throws TennisDatabaseRuntimeException {
      // Throw error if there are no matches to print
      if (numOfMatches == 0) { throw new TennisDatabaseRuntimeException("Error getting all tennis matches! Player has no matches to print!"); }
      // Start at head
      TennisMatchNode curr = head;
      // Create empty ObservableList of TennisMatch objects
      ObservableList<TennisMatch> oList = FXCollections.observableList(new LinkedList<TennisMatch>());
      while(curr != null) {
         // Iterate through and add all matches to the ObservableList
         TennisMatch m = curr.getMatch();
         oList.add(m);
         curr = curr.getNext(); // Advance to next node
      }
      return oList;
   }
   
   // Desc.:   Prints all tennis matches in this list to the console.
   // Output:  Throws an exception if there are no matches in this list.
   public void printMatches()
      throws TennisDatabaseRuntimeException {
      // Throw error if there are no matches to print
      if (numOfMatches == 0) { throw new TennisDatabaseRuntimeException("Error printing all tennis matches! Player has no matches to print!"); }
      
      // Print all matches out
      TennisMatchNode curr = head;
      while (curr != null) {
         curr.getMatch().print(); // Print match
         curr = curr.getNext(); // Advance to next node
      }
   }
}
// © 2018 Jeremy Maxey-Vesperman