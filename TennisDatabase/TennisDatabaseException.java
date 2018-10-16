/*
 *    Class Name: TennisDatabaseException
 *    Extends:    java.lang.Exception
 *    Package:    TennisDatabase
 *    Purpose:    Exception for TennisDatabase package. Used to represent critical runtime errors.
 *    Developer:  Prof. Giuseppe Turini
 */

package TennisDatabase;

// Custom (checked) exception for the TennisDatabase package, representing critical runtime errors (that must be handled).
public class TennisDatabaseException extends java.lang.Exception {
   // Desc.: Constructor.
   // Input: Description of the runtime error.
   public TennisDatabaseException( String s ) { super(s); }
}
// © 2018 Giuseppe Turini