/*
 *    Interface Name:   TennisPlayerNodeInterface
 *    Extends:          N/A  
 *    Package:          TennisDatabase
 *    Purpose:          Provides specifications for the TennisPlayerNode class
 *    Developers:       Prof. Giuseppe Turini and Jeremy Maxey-Vesperman
 *    Modified:         06/08/2018
 */

package TennisDatabase;

// Interface (package-private) providing the specifications for the TennisPlayerNode class.
interface TennisPlayerNodeInterface {
   // Accessors (getters).
   public TennisPlayer getPlayer();
   public TennisPlayerNode getLeftChild();
   public TennisPlayerNode getRightChild();
   
   // Modifiers (setters).
   public void setLeftChild( TennisPlayerNode p );
   public void setRightChild( TennisPlayerNode n );
   
   // Desc.: Insert a TennisMatch object (reference) into this node.
   // Input: A TennisMatch object (reference).
   // Output: Throws an exception if match cannot be inserted in this list.
   public void insertMatch( TennisMatch m ) throws TennisDatabaseRuntimeException;
   
   // Desc.: Prints all tennis matches in the list of this player to the console.
   // Output: Throws an exception if there are no matches in this list.
   public void printMatches() throws TennisDatabaseRuntimeException;
}
// © 2018 Turini, Maxey-Vesperman