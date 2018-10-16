/*
 *    Interface Name:   TennisMatchesListInterface
 *    Interface:        N/A
 *    Package:          TennisDatabase
 *    Purpose:          Provides specifications for the TennisMatchesList class
 *    Developer:        Prof. Giuseppe Turini
 */

package TennisDatabase;

import java.lang.Object;

// Interface (package-private) providing the specifications for the TennisMatchesList class.
interface TennisMatchesListInterface {
   // Desc.: Insert a TennisMatch object (reference) into this list.
   // Input: A TennisMatch object (reference).
   // Output: Throws an exception if match cannot be inserted in this list.
   public void insertMatch( TennisMatch m ) throws TennisDatabaseRuntimeException;
   
   // Desc.: Prints all tennis matches in this list to the console.
   // Output: Throws an exception if there are no matches in this list.
   public void printMatches() throws TennisDatabaseRuntimeException;
}
// © 2018 Giuseppe Turini