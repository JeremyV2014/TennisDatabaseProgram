/*
 *    Class Name: TennisPlayerNode
 *    Interface:  TennisPlayerNodeInterface
 *    Package:    TennisDatabase
 *    Purpose:    Node for storing TennisPlayers, pointers to children, and a matches list.
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   06/08/2018
 */

package TennisDatabase;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class TennisPlayerNode implements TennisPlayerNodeInterface {
   private TennisPlayer player;
   private TennisMatchesList matchesList;
   private TennisPlayerNode left, right;
   
   // Constructor - creates a node with TennisPlayer item, no children, and empty matches list
   public TennisPlayerNode( TennisPlayer player ) {
      this.setPlayer(player);
      this.matchesList = new TennisMatchesList();
      this.setLeftChild(null);
      this.setRightChild(null);
   }
   
   // Constructor - creates a node with TennisPlayer item, sets children, and creates empty matches list 
   public TennisPlayerNode( TennisPlayer player, TennisPlayerNode left, TennisPlayerNode right ) {
      this.setPlayer(player);
      this.matchesList = new TennisMatchesList();
      this.setLeftChild(left);
      this.setRightChild(right);
   }
   
   /* Setters */
   public void setPlayer(TennisPlayer player) { this.player = player; }
   public void setLeftChild(TennisPlayerNode left) { this.left = left; }
   public void setRightChild(TennisPlayerNode right) { this.right = right; }

   /* Getters */
   public TennisPlayer getPlayer() { return this.player; }
   public TennisPlayerNode getLeftChild() { return this.left; }
   public TennisPlayerNode getRightChild() { return this.right; }
   
   // Desc.:   Insert a TennisMatch object (reference) into this node.
   // Input:   A TennisMatch object (reference).
   // Output:  Throws an exception if match cannot be inserted in this list.
   public void insertMatch( TennisMatch m )
      throws TennisDatabaseRuntimeException {
      this.matchesList.insertMatch(m); // Attempt to insert match
      this.updateWinLoss(m); // Update win/loss record of player based on newly-inserted match
   }
   
   // Desc.:   Updates this node's player's win/loss record based on the outcome of the passed match
   // Input:   A TennisMatch object (reference).
   // Output:  Throws an exception if winner cannot be determined.
   private void updateWinLoss( TennisMatch m ) 
      throws TennisDatabaseRuntimeException {
      int winner = m.getWinner();
      
      // Increment either win or loss record based on whether winner id matches this player's id
      switch(winner) {
         case 1: 
            if (this.getPlayer().getId().equals(m.getPlayer1Id())) { // Compare this player's id to the player 1 id of the match
               this.getPlayer().incWinRecord(); // If they match, this player won the match
            } else {
               this.getPlayer().incLossRecord(); // Otherwise they lost
            }
            break;
         case 2:
            if (this.getPlayer().getId().equals(m.getPlayer2Id())) { // Compare this player's id to the player 2 id of the match
               this.getPlayer().incWinRecord(); // If they match, this player won the match
            } else {
               this.getPlayer().incLossRecord(); // Otherwise they lost
            }
            break;
         // Should never reach this case as matches can't be created with an invalid score
         default: throw new TennisDatabaseRuntimeException("Error updating win/loss record! No winner could be determined in current match.");
      }
   }
   
   // Desc.:   Wraps matchesList in ObservableList
   // Output:  Returns ObservableList of TennisMatches for tableview
   public ObservableList<TennisMatch> getMatches() {
      return this.matchesList.getMatches();
   }
   
   // Desc.:   Prints all tennis matches in the list of this player to the console.
   // Output:  Throws an exception if there are no matches in this list.
   public void printMatches()
      throws TennisDatabaseRuntimeException { // Leave handling of exception to caller
      this.matchesList.printMatches();
   }
}
// © 2018 Jeremy Maxey-Vesperman