/*
 *    Interface Name:   TennisDatabaseException
 *    Purpose:          Interface that provides the specifications for the MatchDate class
 *    Extends:          Comparable
 *    Package:          TennisDatabase
 *    Developer:        Jeremy Maxey-Vesperman
 *    Modified:         05/04/2018
 */
 
package TennisDatabase;

import java.lang.Object;

interface MatchDateInterface extends Comparable<MatchDate> {
   /* Setters */
   public void setYear(int year) throws TennisDatabaseRuntimeException;
   public void setMonth(int month) throws TennisDatabaseRuntimeException;
   public void setDay(int day) throws TennisDatabaseRuntimeException;
   
   /* Getters */
   public int getYear();
   public int getMonth();
   public int getDay();
   public String getDateStr();
}
// © 2018 Jeremy Maxey-Vesperman