/*
 *    Class Name: TennisMatchNode
 *    Interface:  N/A
 *    Package:    TennisDatabase
 *    Purpose:    Singly-Linked List match node. 
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   05/04/2018
 */

package TennisDatabase;

class TennisMatchNode {
   private TennisMatch match; // Tennis match object (reference)
   private TennisMatchNode next; // Pointer to next node
   
   /* Constructors */
   public TennisMatchNode (TennisMatch match) { this.match = match; }
   public TennisMatchNode (TennisMatch match, TennisMatchNode next) {
      this.match = match;
      this.next = next;
   }
   
   /* Setters */
   public void setMatch(TennisMatch match) { this.match = match; }
   public void setNext(TennisMatchNode next) { this.next = next; }
   
   /* Getters */
   public TennisMatch getMatch() { return this.match; }
   public TennisMatchNode getNext() { return this.next; }
}
// © 2018 Jeremy Maxey-Vesperman