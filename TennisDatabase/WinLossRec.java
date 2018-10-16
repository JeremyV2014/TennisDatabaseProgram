/*
 *    Class Name: WinLossRec
 *    Interface:  N/A
 *    Package:    TennisDatabase
 *    Purpose:    Provides a container to store win loss records for tennis players.
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   05/04/2018
 */

package TennisDatabase;

class WinLossRec {
   private int win, loss;
   
   public WinLossRec() { }
   
   /* Setters */
   public void incWin() { this.win++; }
   public void incLoss() { this.loss++; }
   public void decWin() { this.win--;}
   public void decLoss() { this.loss--; }
   
   /* Getters */
   public int getWins() { return this.win; }
   public int getLosses() { return this.loss; }
   
   /* Functions / Methods */
   // Desc.:   Formats Win/Loss record as a string
   // Output:  Formatted Win/Loss record string
   public String toString() { return (win + "/" + loss + " (WIN/LOSS)"); }
}
// © 2018 Jeremy Maxey-Vesperman