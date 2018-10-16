/*
 *    Class Name: TennisDatabase
 *    Interface:  TennisDatabaseInterface
 *    Package:    TennisDatabase
 *    Purpose:    Database that contains tennis players, matches, and provides relevant functions for interfacing with the data 
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   06/08/2018
 */
 
package TennisDatabase;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.*;
import java.util.Scanner;

public class TennisDatabase implements TennisDatabaseInterface {
   TennisPlayersContainer tpc = new TennisPlayersContainer(); // Binary Search Tree that holds tennis player nodes
   TennisMatchesContainer tmc = new TennisMatchesContainer(); // JCF LinkedList that holds all tennis matches
   
   // Constructor - Default
   public TennisDatabase() {}
   
   // Desc.:   Method to load database from text file
   // Input:   File object
   // Output:  Status info regarding entry parsing. Throws exception if file does not exist
   public void loadFromFile (File file) throws FileNotFoundException {
      this.tpc = new TennisPlayersContainer();
      this.tmc = new TennisMatchesContainer();
      
      Scanner scanFile = new Scanner(file); // Create scanner object for file
      scanFile.useDelimiter("\r\n");
      
      while (scanFile.hasNext()) { // While there is another line to parse
         String line = scanFile.next(); // Get next line in file
         Scanner scanLine = new Scanner(line); // Create scanner object for current line
         scanLine.useDelimiter("/"); // Set scanner delimiter to '/'
         
         String lineTypeToken = "";
         if (scanLine.hasNext()) { lineTypeToken = scanLine.next().toUpperCase(); } // Get line type token if a token to scan exists
         
         String [] fields = new String[5];
         for (int i = 0; scanLine.hasNext() && i < 5; i++) { // Parse all fields on line and store into an array of arguments
            fields[i] = scanLine.next();
         }
         
         if (lineTypeToken.equals("PLAYER")) { // If this is a player line
            try {
               this.insertPlayer(fields[0], fields[1], fields[2], Integer.parseInt(fields[3]), fields[4]); // Add player to player container
            } catch (TennisDatabaseRuntimeException e) { // Player id already exists... Automatically update entry with new player data
               tpc.updatePlayer(fields[0], fields[1], fields[2], Integer.parseInt(fields[3]), fields[4]);
            } catch (NumberFormatException e) { // Birth year entry is not a number. Do not create player.
               System.out.println("Error parsing player entry. Birth year entry is NAN: " + fields[3] + "\r\nSkipping player entry.");
            } catch (NullPointerException e) { // Entry does not contain enough fields to create player
               System.out.println("Error parsing player entry. Invalid number of fields provided.\r\nSkipping player entry.");
            }
         } else if (lineTypeToken.equals("MATCH")) { // If this is a match line
            try {
               this.insertMatch( fields[0], fields[1], Integer.parseInt(fields[2].substring(0,4)), Integer.parseInt(fields[2].substring(4,6)),
                                 Integer.parseInt(fields[2].substring(6,8)), fields[3], fields[4]);
            } catch (TennisDatabaseRuntimeException e) {
               System.out.println(e.getMessage());
            } catch (NumberFormatException e) { // Date entry is not a number. Do not enter match.
               System.out.println("Error parsing match entry. Match date entry is NAN: " + fields[2] + "\r\nSkipping match entry.");
            } catch (IndexOutOfBoundsException e) { // Date field is too short to contain valid formatted date. Do not enter match.
               System.out.println("Error parsing match entry. Match date entry is too short to be valid: " + fields[2] + "\r\nSkipping match entry.");
            } catch (NullPointerException e) { // Entry does not contain enough fields to create match.
               System.out.println("Error parsing match entry. Invalid number of fields provided.\r\nSkipping match entry.");
            }
         }
      }
   }
   
   public void exportDatabase(File file) {
      try {
         file.delete();
         file.createNewFile();
         
         PrintWriter writer = new PrintWriter(file);
         
         tpc.treeToFile(writer);
         tmc.matchesToFile(writer);
         
         writer.close(); // Close file.
      } catch (IOException ex) {
      }
   }
   
   // Desc.:   Resets the database by creating new containers for tennis players and matches
   public void resetDatabase() {
      tpc = new TennisPlayersContainer();
      tmc = new TennisMatchesContainer();
   }
   
   // Desc.:   Insert a tennis player into the database.
   // Input:   All the data required for a tennis player.
   //          Warning: player id must be unique.
   // Output:  Throws an exception if player id is already in this container.
   public void insertPlayer( String id, String firstName, String lastName, int year, String country ) 
      throws TennisDatabaseRuntimeException {
      // Player id exists if a reference is returned. Throw exception.
      if (tpc.search(tpc, id)) { throw new TennisDatabaseRuntimeException("Error inserting new player! Player ID " + id + " already exists!"); }
      tpc.insertPlayer(new TennisPlayer(id, firstName, lastName, year, country)); // Otherwise... insert player
   }

   // Desc.:   Update a tennis player node in the database with new tennis player.
   // Input:   All the data required for a tennis player.
   public void updatePlayer( String id, String firstName, String lastName, int year, String country )
      throws TennisDatabaseRuntimeException {
      // Player can't be updated if it doesn't exist already. Throw exception if this is the case.
      if (!tpc.search(tpc, id)) { throw new TennisDatabaseRuntimeException("Error attempting to update player! Player doesn't exist with id: " + id); }
      
      tpc.updatePlayer(id, firstName, lastName, year, country); // Otherwise... update player
   }
   
   // Desc.:   Delete a tennis player from the database.
   // Input:   UID of tennis player to delete.
   // Output:  Throws an exception if player can't be found.
   public void deletePlayer(String id)
      throws TennisDatabaseRuntimeException {
      tpc.deletePlayer(id);
   }

   // Desc.:   Insert a tennis match into the database.
   // Input:   All the data required for a tennis match.
   //          Warning: match score must be valid.
   // Output:  Throws an exception if match score is not valid.
   public void insertMatch( String idPlayer1, String idPlayer2, int year, int month, int day, String tournament, String score ) 
      throws TennisDatabaseRuntimeException {
      try {
         TennisPlayerNode p1Node = tpc.retrievePlayer(tpc, idPlayer1);
         TennisPlayerNode p2Node = tpc.retrievePlayer(tpc, idPlayer2);
         
         // Create player nodes if they don't exist
         if (p1Node == null) {
            TennisPlayer player = new TennisPlayer(idPlayer1);
            tpc.insertPlayer(player);
            p1Node = tpc.retrievePlayer(tpc, idPlayer1);
         }
         if (p2Node == null) {
            TennisPlayer player = new TennisPlayer(idPlayer2);
            tpc.insertPlayer(player);
            p2Node = tpc.retrievePlayer(tpc, idPlayer2);
         }
         
         TennisMatch m = new TennisMatch(p1Node.getPlayer(), p2Node.getPlayer(), year, month, day, tournament, score); // Create tennis match with input data
         tmc.insertMatch(m); // Add match to match container
         tpc.insertMatch(m); // Add match to matches list
      } catch (TennisDatabaseRuntimeException e) { // Pass along error message and inform user that match couldn't be inserted.
         throw new TennisDatabaseRuntimeException(e.getMessage() + "\r\nMatch could not be inserted.");
      }
   }
   
   // Desc.:   Prints all tennis players in the database to the console (sorted by id, alphabetically).
   // Output:  Throws an exception if there are no players in the database.
   public void printAllPlayers()
      throws TennisDatabaseRuntimeException { // Exception thrown by tennis players container if there are no players
      tpc.printAllPlayers();
   }
   
   // Desc.:   Prints all tennis matches in the database to the console (sorted by date, most recent first).
   // Output:  Throws an exception if there are no tennis matches in the database. Leave handling to caller.
   public void printAllMatches()
      throws TennisDatabaseRuntimeException { // Exception thrown by tennis matches container if there are no matches. Leave handling to caller.
      tmc.printAllMatches();
   }
   
   // Desc.:   Prints all tennis matches of input tennis player (id) to the console (sorted by date, most recent first).
   // Input:   The id of the tennis player.
   // Output:  Throws an exception if the tennis player (id) does not exists, or if there are no tennis matches.
   public void printMatchesOfPlayer( String playerId )
      throws TennisDatabaseRuntimeException { // Exception thrown by tennis players container if tennis player id doesn't exist or player has no matches
      tpc.printMatchesOfPlayer(playerId);
   }
   
   // Desc.:   Get an ObservableList of players for tableview
   // Output:  ObservableList of TennisPlayers for tableview to update its content with.
   public ObservableList<TennisPlayer> getPlayersList() {
      return tpc.getPlayersList();
   }
   
   // Desc.:   Get an ObservableList of matches for tableview
   // Output:  ObservableList of TennisMatches for tablewview to update its content with.
   public ObservableList<TennisMatch> getMatchesList() {
      return tmc.getMatchesList();
   }
   
   // Desc.:   Get an OberservableList of matches of a particular player for tableview
   // Input:   String representing the uid of the player's whos matches are to be retrieved
   // Output:  ObservableList of TennisMatches of player. Throws an exception if 
   //          player does not exist 
   public ObservableList<TennisMatch> getMatchesOfPlayer( String playerId )
      throws TennisDatabaseRuntimeException {
      return tpc.getMatchesOfPlayer(playerId);
   }
}
// © 2018 Jeremy Maxey-Vesperman