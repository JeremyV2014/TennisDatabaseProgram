/*
 *    Class Name: MatchSetScore
 *    Interface:  N/A
 *    Package:    TennisDatabase
 *    Purpose:    Provides a container for storing tennis match set wins for each player.
 *                Provides function to determine winner
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   05/04/2018
 */
 
package TennisDatabase;

public class MatchSetScore {
   private int p1 = 0, p2 = 0; // Player games won
   
   /* Constructors*/
   public MatchSetScore () { }
   
   /* Setters */
   public void incP1 () { p1++; }
   public void incP2 () { p2++; }
   
   /* Getters */
   public int getP1Wins() { return this.p1; }
   public int getP2Wins() { return this.p2; }
   public int getWinner () 
      throws TennisDatabaseRuntimeException {
      // Winner can't be determined if games won are equal. Throw exception
      if (this.getP1Wins() == this.getP2Wins()) {
         throw new TennisDatabaseRuntimeException("Error determining set winner! Games won for each player are equal.");
      }
      
      return (p1 > p2) ? 1 : 2; } // Return winning player
}
// © 2018 Jeremy Maxey-Vesperman