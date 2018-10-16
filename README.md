Assignment2 is a GUI tool for loading/viewing/modifying tennis databases. The database maintains the tennis players in a binary search tree. Each node contains the player and a singly-linked list of tennis matches that the player participated in. The database maintains tennis matches in a sorted JavaCollectionsFramework LinkedList. Database can be loaded from a text file containing entries for players and matches. User can insert new matches and players as well as remove players through the interface. If a player id already exists, the player's information is updated instead. This allows the user to make updates to players that may have incomplete/ incorrect entries, etc. User is allowed to enter "duplicate" matches as the developer determined that, although it is unlikely, it is possible that two players could have participated in a tennis match on the same day at the same tournament with the same score. Furthermore, the user can export the database to a text file, preserving the structure of the tennis player tree.<br>
	
<b>Explanation of JCF Class Chosen for TennisMatchesContainer Implementation:</b><br>
<blockquote>The JCF LinkedList provided a number of advantages as the candidate data structure. One advantage is that it is reference-based. This is important because tennis matches need to be pointed to by two separate entities; the TennisMatchesContainer as well as the TennisMatchesList in the TennisPlayerNode. By using a reference-based data structure, we can make a change to the match in only one of these containers and have it affect the other. This will be useful if the modification of Tennis Matches were implemented. Furthermore, the matches don't need to be duplicated to be stored in both containers. Finally, because it is a linked list, it can grow infinitely large without requiring resizing. For these reasons, I chose not to use the Vector, ArrayDeque, nor the ArrayList. Utilizing a Stack makes no sense for this container as there is a need to keep the matches sorted by date and this structure is not useful for these types of operation.</blockquote><br>

<b>AUTHORS</b><br>
	&emsp;Jeremy Maxey-Vesperman<br>
	&emsp;Prof. Giuseppe Turini<br>
<b>DATABASE FILE FORMAT</b><br>
	&emsp;Database can be loaded from text (.txt) files. Each player/match entry must be on separate lines (CR+LF) and use the following format:<br>
	&emsp;&emsp;<i>Player entry</i>:<br>
		&emsp;&emsp;&emsp;PLAYER/[Unique identifier]/[First name]/[Last name]/[Year of birth (YYYY)]/[Country]<br>
	&emsp;&emsp;<i>Match entry:</i><br>
		&emsp;&emsp;&emsp;MATCH/[UID of Player 1]/[UID of Player 2]/[Match date (YYYYMMDD)]/[Tournament name]/[Set score (P1Match1-P2Match1,P1Match2-P2Match2,...)]<br>
<b>OPERATING INSTRUCTIONS</b><br>
  <blockquote>1. Import a Database: "File" -> "Import Database" -> Choose file -> "Open"<br>
	2. Export a Database: "File" -> "Export Database" -> Choose file -> "Save"<br>
	3. Reset the Database:	"File" -> "Reset Database"<br>
	4. View All Players: If not on All Tennis Players screen, press "Display All Players"<br>
	5. View All Matches: If not on All Tennis Matches screen, press "Display All Matches"<br>
	6. View Matches of Player: Navigate to All Tennis Players screen, select a Tennis Player from the table. Press "Display Player's Matches"<br>
	7. Add Player: Navigate to All Tennis Players screen, fill in all text fields, press "Add Player"<br>
	8. Remove Player: Navigate to All Tennis Players screen, select a Tennis Player from the table. Press "Remove Selected Player"<br>
	9. Add Match: Navigate to All Tennis Matches screen, fill in all text fields, press "Add Match"<br>
	10. Exit: "File" -> "Exit"</blockquote><br>
