/*
 *    Class Name: TennisMatch
 *    Interface:  TennisMatchInterface
 *    Package:    TennisDatabase
 *    Purpose:    Stores all data regarding a tennis match. Provides methods/functions to compute set score,
 *                compare to other tennis matches, and print the match's data to console
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   05/04/2018
 */
 
package TennisDatabase;

import java.util.Scanner;

public class TennisMatch implements TennisMatchInterface {
   private final String SET_DELIM = ",", GAME_DELIM = "-"; // Delimiter constants
   
   private TennisPlayer p1,
                        p2;
   private String tournament,
                  score;
   private MatchDate date;
   private int winner;
   
   /* Constructors */
   public TennisMatch (TennisPlayer p1, TennisPlayer p2, int year, int month, int day, String tournament, String score) {
      setP1(p1);
      setP2(p2);
      setDate(new MatchDate(year, month, day));
      setTournament(tournament);
      setScore(score);
   }
   
   /* Setters */
   public void setP1 (TennisPlayer p1) { this.p1 = p1; }
   public void setP2 (TennisPlayer p2) { this.p2 = p2; }
   public void setDate (MatchDate md) { this.date = md; }
   public void setDateYear (int year) { this.date.setYear(year); }
   public void setDateMonth (int month) { this.date.setMonth(month); }
   public void setDateDay (int day) { this.date.setDay(day); }
   public void setTournament (String tournament) { this.tournament = tournament.toUpperCase(); }
   public void setScore (String score) {
      this.score = score;
      MatchSetScore msc = computeSetScore(this.score); // Compute the number of games that each player won in set
      this.setWinner(msc.getWinner()); // Set the winner
   }
   private void setWinner (int winner) { this.winner = winner; }
   
   /* Getters */
   public TennisPlayer getPlayer1() { return this.p1; }
   public TennisPlayer getPlayer2() { return this.p2; }
   public String getPlayer1Name () { return (this.p1.getFirstName() + " " + this.p1.getLastName()); }
   public String getPlayer2Name () { return (this.p2.getFirstName() + " " + this.p2.getLastName()); }
   public String getPlayer1Id () { return this.p1.getId(); }
   public String getPlayer2Id () { return this.p2.getId(); }
   public MatchDate getDate () { return this.date; }
   public String getDateString () { return (this.getDateMonth() + "/" + this.getDateDay() + "/" + this.getDateYear()); }
   public int getDateYear () { return this.date.getYear(); }
   public int getDateMonth() { return this.date.getMonth(); }
   public int getDateDay() { return this.date.getDay(); }
   public String getTournament () { return this.tournament; }
   public String getScore () { return this.score; }
   public int getWinner() { return winner; }
   
   /* Functions / Methods */
   // Desc.:   Function computes set score
   // Input:   String representing the set score. Each match should be delimited by ",". Scores in each game should be delimited by "-"
   // Output:  MatchSetScore object containing the wins for each player. Throws exception if set score is invalid format
   private MatchSetScore computeSetScore(String set) 
      throws TennisDatabaseRuntimeException {      
      // Minimum possible length of set string is 3. Throw exception if this is not the case
      if (set.length() < 3) {
         throw new TennisDatabaseRuntimeException("Error parsing set! Match string is too short to be valid: " + set);
      }
      
      // Determine index of set delimiter
      int idx = set.indexOf(SET_DELIM);
      
      // Base Case
      if (idx == -1) { // There are no more games in set after this
         MatchSetScore score = new MatchSetScore();
         // Figure out which player won that game
         try { // Try computing the winner of the game
            // Increment games won counter of appropriate player
            if (computeGameWinner(set) == 1) {
               score.incP1();
            } else {
               score.incP2();
            }
         } catch (TennisDatabaseRuntimeException e) {
            System.out.println(e.getMessage()); // Warn user that we are skipping over this game if it is invalid
            System.out.println("Skipping game for set winner computation.");
         }
         return score;
      }
      
      // Recurrence Relation - More games in set after this game
      // Parse set string
      String game = set.substring(0, idx); // Store next game
      set = set.substring((idx + 1), set.length()); // Reduce size of problem by one game
      // Call to self
      MatchSetScore score = computeSetScore(set); // Store reference to MatchSetScore created by base case
      // Figure out which player won the game
      // Increment games won counter of appropriate player
      try {
         if (computeGameWinner(game) == 1) {
            score.incP1();
         } else {
            score.incP2();
         }
      } catch (TennisDatabaseRuntimeException e) {
         System.out.println(e.getMessage()); // Warn user that we are skipping over this game if it is invalid
         System.out.println("Skipping game for set winner computation.");
      }
      return score; // Return MatchSetScore reference to Caller
   }
   
   // Desc.:   Function to compute the winner of a specified game
   // Input:   String representing one game score delimited by "-"
   // Output:  Integer representing the player (Player 1 or Player 2) that won the game.
   //          Throws exception if game string is misformatted, points are NAN, or winner can't be determined
   private int computeGameWinner (String game) 
      throws TennisDatabaseRuntimeException {      
      // Minimum possible length of game string is 3. Throw exception if this isn't the case
      if (game.length() < 3) {
         throw new TennisDatabaseRuntimeException("Error parsing game! Game string is too short to be valid: " + game);
      }
      
      int idx = game.indexOf(GAME_DELIM); // Determine index of game delimiter
      // Throw exception if there is no delimiter
      if (idx == -1) { 
         throw new TennisDatabaseRuntimeException("Error parsing game! No delimiter found in current game: " + game);
      }
      
      int p1Score, p2Score;
      try { // try parsing scores as integers
         p1Score = Integer.parseInt(game.substring(0, idx));
         p2Score = Integer.parseInt(game.substring((idx + 1), game.length()));
      } catch (NumberFormatException e){ // throw a more informative exception
         throw new TennisDatabaseRuntimeException("Error parsing game! One or both scores of current game is NAN: " + game);
      }
      
      // Can't computer game winner if points are equal. Throw exception
      if (p1Score == p2Score) {
         throw new TennisDatabaseRuntimeException("Error computing game winner! Points are equal: " + game);
      }
      
      return ((p1Score > p2Score) ? 1 : 2); // Return the winner of the game
   }
   
   // Desc.:   Prints this tennis match on the console.
   public void print() {
      TennisPlayer player1 = this.getPlayer1();
      TennisPlayer player2 = this.getPlayer2();
      String msg = ( this.date.getDateStr() + ", " + player1.getFirstName() + " " + player1.getLastName() + " vs. " + player2.getFirstName() + " " + player2.getLastName() + ", " + this.getTournament() + ", " + 
                     this.getScore() + ", WINNER: " + ((this.getWinner() == 1) ? player1.getFirstName() + " " + player1.getLastName() : player2.getFirstName() + " " + player2.getLastName()));
      System.out.println(msg);
   }
   
   // Desc.:   Determines sorting order of this TennisMatch compared to an input TennisMatch.
   //          Sorting order: Identical reference -> Newer Date -> Tournament -> Score -> Player 1 ID -> Player 2 ID
   // Input:   TennisMatch object (reference)
   // Output:  Integer indicating sorting order:
   //             Negative = this TennisMatch comes BEFORE input TennisMatch
   //             Zero = this TennisMatch is EQUAL to input TennisMatch
   //             Positve = this TennisMatch comes AFTER input TennisMatch
   public int compareTo(TennisMatch tm) {
      final int BEFORE = -1;
      final int EQUAL = 0;
      final int AFTER = 1;
      
      // Optimization to first check if we are comparing the same object
      if (this == tm) { return EQUAL; }
      
      // Compare Date
      int comparison = this.getDate().compareTo(tm.getDate());
      if (comparison == -1) { return BEFORE; }
      if (comparison == 1) { return AFTER; }
      
      // Compare by Tournament
      comparison = this.getTournament().compareTo(tm.getTournament());
      if (comparison != EQUAL) { return comparison; }
      
      // Compare by Score
      comparison = this.getScore().compareTo(tm.getScore());
      if (comparison != EQUAL) { return comparison; }
      
      // Compare by Player 1 ID
      comparison = this.getPlayer1Id().compareTo(tm.getPlayer1Id());
      if (comparison != EQUAL) { return comparison; }
      
      // Compare by Player 2 ID
      comparison = this.getPlayer2Id().compareTo(tm.getPlayer2Id());
      if (comparison != EQUAL) { return comparison; }
      
      // Contents of players are equal
      return EQUAL;
   }
}
// © 2018 Jeremy Maxey-Vesperman