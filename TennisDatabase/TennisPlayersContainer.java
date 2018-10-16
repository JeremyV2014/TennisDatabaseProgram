/*
 *    Class Name: TennisPlayersContainer
 *    Interface:  TennisPlayersContainerInterface
 *    Package:    TennisDatabase
 *    Purpose:    Provides a container to store TennisPlayerNodes as a binary search tree.
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   06/08/2018
 */

package TennisDatabase;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.LinkedList;
import java.io.*;

public class TennisPlayersContainer implements TennisPlayersContainerInterface {
   private TennisPlayerNode root;
   
   /* Constructors */
   // Default creates an empty container
   public TennisPlayersContainer() { this.setRoot(null); }
   
   // Initialize tree with root player object
   public TennisPlayersContainer( TennisPlayer rootItem ) {
      this.setRoot(new TennisPlayerNode(rootItem));
   }
   
   protected TennisPlayersContainer( TennisPlayerNode root ) {
      this.setRoot(root);
   }
   
   // Creates a container with subtrees
   public TennisPlayersContainer( TennisPlayerNode root, TennisPlayersContainer leftTree, TennisPlayersContainer rightTree) {
      this.setRoot(root);
      attachLeftSubtree(leftTree);
      attachRightSubtree(rightTree);
   }
   
   // Desc.:   Determines if tree is empty
   // Output:  Boolean representing whether or not this container is empty
   public boolean isEmpty() { return (this.root == null); }
   
   // Desc.:   Makes tree empty by removing pointer to root
   public void makeEmpty() { this.setRoot(null); }
   
   /* Setters */
   public void setRoot(TennisPlayerNode root) { this.root = root; }
   public void setRootPlayer( TennisPlayer player ) {
      if ( root != null ) {
         this.getRoot().setPlayer(player);
      } else {
         this.setRoot(new TennisPlayerNode(player));
      }
   }
   
   // Desc.:   Attaches a left child to root node if one currently doesn't exist
   // Input:   TennisPlayer object to create a node from and attach to the left of the root
   public void attachLeft( TennisPlayer tp ) {
      if (!this.isEmpty() && this.getRoot().getLeftChild() == null) {
         this.getRoot().setLeftChild(new TennisPlayerNode(tp));
      }
   }
   
   // Desc.:   Attaches a right child to root node if one currently doesn't exist
   // Input:   TennisPlayer object to create a node from and attach to the right of the root
   public void attachRight( TennisPlayer tp ) {
      if (!this.isEmpty() && this.getRoot().getRightChild() == null) {
         this.getRoot().setRightChild(new TennisPlayerNode(tp));
      }
   }
   
   // Desc.:   Attaches a subtree to the left child of root if one doesn't already exist
   // Input:   Subtree to attach to the left of the root
   public void attachLeftSubtree(TennisPlayersContainer leftTree)
      throws TennisDatabaseRuntimeException {
      if (this.isEmpty()) {
         throw new TennisDatabaseRuntimeException("Error! Tennis Player's Container is empty!");
      } else if (this.getRoot().getLeftChild() != null) {
         throw new TennisDatabaseRuntimeException("Error! Cannot overwrite left subtree!");
      } else {
         this.getRoot().setLeftChild(leftTree.getRoot());
         leftTree.makeEmpty(); // Remove secondary entry point to tree
      }
   }
   
   // Desc.:   Attaches a subtree to the right child of root if one doesn't already exist
   // Input:   Subtree to attach to the right of the root
   public void attachRightSubtree(TennisPlayersContainer rightTree)
      throws TennisDatabaseRuntimeException {
      if (this.isEmpty()) {
         throw new TennisDatabaseRuntimeException("Error! Tennis Player's Container is empty!");
      } else if (this.getRoot().getRightChild() != null) {
         throw new TennisDatabaseRuntimeException("Error! Cannot overwrite right subtree!");
      } else {
         this.getRoot().setRightChild(rightTree.getRoot());
         rightTree.makeEmpty(); // Remove secondary entry point to tree
      }
   }   
   
   /* Getters */
   public TennisPlayerNode getRoot()
      throws TennisDatabaseRuntimeException {
      if ( this.isEmpty() ) {
         throw new TennisDatabaseRuntimeException("Error! Tennis Player's Container is empty!");
      } else { return this.root; }
   }
   
   // Desc.:   Detaches left child from the root node, removes the reference from root, and returns it
   // Output:  TennisPlayersContainer representing the left subtree of the root. Throws an exception
   //          if the container is empty
   public TennisPlayersContainer detachLeftSubtree()
      throws TennisDatabaseRuntimeException {
      if (this.isEmpty()) {
         throw new TennisDatabaseRuntimeException("Error! Tennis Player's Container is empty!");
      } else {
         TennisPlayersContainer leftTree = new TennisPlayersContainer(this.getRoot().getLeftChild());
         this.getRoot().setLeftChild(null);
         return leftTree;
      }
   }
   
   // Desc.:   Detaches right child form the root node, removes the reference from root, and returns it
   // Output:  TennisPlayersContainer representing the right subtree of the root. Throws an exception
   //          if the container is empty
   public TennisPlayersContainer detachRightSubtree()
      throws TennisDatabaseRuntimeException {
      if (this.isEmpty()) {
         throw new TennisDatabaseRuntimeException("Error! Tennis Player's Container is empty!");
      } else {
         TennisPlayersContainer rightTree = new TennisPlayersContainer(this.getRoot().getRightChild());
         this.getRoot().setRightChild(null);
         return rightTree;
      }
   }
   
   // Desc.:   Gets the left subtree of the root of this tree
   // Output:  TennisPlayersContainer object representing the left subtree of the root
   public TennisPlayersContainer getLeftSubtree()
      throws TennisDatabaseRuntimeException {
      if (this.isEmpty()) {
         throw new TennisDatabaseRuntimeException("Error! Tennis Player's Container is empty!");
      } else {
         return new TennisPlayersContainer(this.getRoot().getLeftChild());
      }
   }
   
   // Desc.:   Gets the right subtree of the root of this tree
   // Output:  TennisPlayersContainer object representing the right subtree of the root
   public TennisPlayersContainer getRightSubtree()
      throws TennisDatabaseRuntimeException {
      if (this.isEmpty()) {
         throw new TennisDatabaseRuntimeException("Error! Tennis Player's Container is empty!");
      } else {
         return new TennisPlayersContainer(this.getRoot().getRightChild());
      }
   }
   
   /* Binary Search Tree Operations */
   // Desc.:   Performs a search for a TennisPlayer with the search Id in the binary search tree
   // Input:   TennisPlayersContainer to search and the String representation of the uid
   // Output:  Boolean indicating if a matching player was found or not
   public boolean search( TennisPlayersContainer tpc, String searchId ) {
      if (tpc.isEmpty()) { return false; }
      else {
         int compare = searchId.toUpperCase().compareTo(tpc.getRoot().getPlayer().getId());
         if (compare == 0) { return true; }
         else if (compare < 0) {
            return search(tpc.getLeftSubtree(), searchId);
         } else {
            return search(tpc.getRightSubtree(), searchId);
         }
      }
   }
   
   // Desc.:   Retrieves the TennisPlayerNode associated with the search ID
   // Input:   TennisPlayersContainer to traverse through and a String representing the uid of the player
   // Output:  TennisPlayerNode that contains the player with the uid matching search ID
   public TennisPlayerNode retrievePlayer( TennisPlayersContainer tpc, String searchId ) {
      if (tpc.isEmpty()) { return null; }
      else {
         // Case insensitive comparison
         int compare = searchId.toUpperCase().compareTo(tpc.getRoot().getPlayer().getId());
         
         if (compare == 0) { return tpc.getRoot(); }
         else if (compare < 0) {
            return retrievePlayer(tpc.getLeftSubtree(), searchId);
         } else {
            return retrievePlayer(tpc.getRightSubtree(), searchId);
         }
      }
   }
   
   // Desc.:   Method for TennisPlayersContainer to insert a TennisPlayer
   // Input:   TennisPlayerNode (representing the root to start) and the TennisPlayer to be inserted
   // Output:  TennisPlayerNode that is to be the new root for the tree
   private TennisPlayerNode insertPlayer( TennisPlayerNode node, TennisPlayer player ) {
      if (node == null) {
         node = new TennisPlayerNode(player, null, null);
      } else if (player.compareTo(node.getPlayer()) < 0) {
         node.setLeftChild(insertPlayer(node.getLeftChild(), player));
      } else {
         node.setRightChild(insertPlayer(node.getRightChild(), player));
      }
      return node;
   }
   
   // Desc.:   Wrapper method for insertion of TennisPlayer by outside classes
   // Input:   TennisPlayer to be inserted
   public void insertPlayer ( TennisPlayer player ) {
      if (this.isEmpty()) {
         this.setRoot(insertPlayer(null, player));
      } else {
         insertPlayer(this.getRoot(), player);
      }
   }
   
   // Desc.:   Method to update items in an existing node within the tree
   // Input:   All the data necessary to update the player
   public void updatePlayer (String id, String firstName, String lastName, int year, String country)
      throws TennisDatabaseRuntimeException {
      TennisPlayerNode pNode = this.retrievePlayer(this, id);
      if (pNode == null) { throw new TennisDatabaseRuntimeException("Error updating player! Player does not exist!"); }
      else {
         TennisPlayer p = pNode.getPlayer();
         p.setFirstName(firstName);
         p.setLastName(lastName);
         p.setYear(year);
         p.setCountry(country);
      }
   }
   
   // Desc.:   Method to remove player node matching the passed uid String
   // Input:   UID of TennisPlayer to be removed
   public void deletePlayer( String id )
      throws TennisDatabaseRuntimeException {  
      this.setRoot(this.deletePlayer(this.getRoot(), id));
   }
   
   // Desc.:   Method for TennisPlayersContainer to remove a TennisPlayer
   // Input:   Root node for the tree and the id of the player to be removed
   // Output:  New root for the tree
   private TennisPlayerNode deletePlayer( TennisPlayerNode rootNode, String id )
      throws TennisDatabaseRuntimeException {
      if (rootNode == null) {
         throw new TennisDatabaseRuntimeException("Error deleting player! Player not found!");
      } else {
         int compare = id.compareTo(rootNode.getPlayer().getId());
         if (compare == 0) {
            TennisPlayerNode newRoot = this.deletePlayerNode(rootNode);
            return newRoot;
         } else if (compare < 0) {
            TennisPlayerNode newLeft = this.deletePlayer(rootNode.getLeftChild(), id);
            rootNode.setLeftChild(newLeft);
            return rootNode;
         } else {
            TennisPlayerNode newRight = this.deletePlayer(rootNode.getRightChild(), id);
            rootNode.setRightChild(newRight);
            return rootNode;
         }
      }
   }
   
   // Desc.:   Method for TennisPlayersContainer to remove a TennisPlayerNode
   // Input:   Node of the tennis player to be deleted
   // Output:  TennisPlayerNode
   private TennisPlayerNode deletePlayerNode( TennisPlayerNode pNode ) {
      if (pNode.getLeftChild() == null) {
         if (pNode.getRightChild() == null) { return null; } // Leaf case
         else { return pNode.getRightChild(); } // Node has only a right child
      } else if (pNode.getRightChild() == null) { return pNode.getLeftChild(); } // Node has only a left child
      else { // Node has two children
         // Find next inorder successor
         TennisPlayerNode replacementNode = this.findLeftMost(pNode.getRightChild());
         TennisPlayerNode replacementRightChild = this.deleteLeftMost(pNode.getRightChild());
         replacementNode.setLeftChild(pNode.getLeftChild()); // Point replacement node to left subtree of node to be deleted
         pNode = replacementNode; // Replace the node
         pNode.setRightChild(replacementRightChild); // Link replacement right subtree
         return pNode;
      }
   }
   
   // Desc.:   Method for finding the left-most node in the tree
   // Input:   Entry point to begin search
   // Output:  Left-most node in the tree
   private TennisPlayerNode findLeftMost( TennisPlayerNode pNode ) {
      if (pNode.getLeftChild() == null) { return pNode; }
      else { return this.findLeftMost(pNode.getLeftChild()); }
   }
   
   // Desc.:   Method for removing the parent's pointer to the left-most node in the tree
   // Output:  TennisPlayerNode that is to be the right child replacement
   private TennisPlayerNode deleteLeftMost( TennisPlayerNode pNode ) {
      if (pNode.getLeftChild() == null) { return pNode.getRightChild(); }
      else {
         TennisPlayerNode replacementLeftChild = this.deleteLeftMost(pNode.getLeftChild());
         pNode.setLeftChild(replacementLeftChild);
         return pNode;
      }
   }
   
   // Desc.:   Insert a TennisMatch object (reference) into this container.
   // Input:   A TennisMatch object (reference).
   // Output:  Throws an exception if the tennis match score is not valid.
   public void insertMatch ( TennisMatch m )
      throws TennisDatabaseRuntimeException { 
      // Search for the match players in this list
      TennisPlayerNode p1 = this.retrievePlayer(this, m.getPlayer1().getId());
      TennisPlayerNode p2 = this.retrievePlayer(this, m.getPlayer2().getId());
      
      // Insert match in each player node
      p1.insertMatch(m);
      p2.insertMatch(m);
   }
   
   // Desc.:   Internal method to prints all players
   // Input:   TennisPlayersContainer to traverse for printing all players
   // Output:  All player's printed to the console
   private void printAllPlayers(TennisPlayersContainer tpc) {
      // Traverse the binary search tree in inorder.
      if( !tpc.isEmpty() ) {
         printAllPlayers( tpc.getLeftSubtree() );
         tpc.getRoot().getPlayer().print();
         printAllPlayers( tpc.getRightSubtree() );
      }
   }
   
   // Desc.:   Public method for printing all the players in this TennisPlayersContainer.
   // Output:  All player's printed to the console. Throws exception if container is empty.
   public void printAllPlayers()
      throws TennisDatabaseRuntimeException {
      // Throw exception if there are no players in this container
      if (this.isEmpty()) { throw new TennisDatabaseRuntimeException("Error printing all players! No players to print!"); }
      else {
         printAllPlayers(this);
      }
   }
   
   // Desc.:   Prints all tennis matches of input tennis player (id).
   // Input:   The id of the tennis player.
   // Output:  Throws an exception if the tennis player (id) does not exits, or if there are no tennis matches.
   public void printMatchesOfPlayer( String playerId )
      throws TennisDatabaseRuntimeException {
         TennisPlayerNode pNode = this.retrievePlayer(this, playerId);
         if (pNode == null) {
            throw new TennisDatabaseRuntimeException("Error printing matches for player! Player with ID " + playerId.toUpperCase() + " does not exist.");
         }
         pNode.printMatches();
   } 
   
   // Desc.:   Creates an inorder ObservableList of TennisPlayer items within the binary search tree for tableview.
   // Output:  Inorder ObservableList of TennisPlayer objects
   public ObservableList<TennisPlayer> getPlayersList() {
      TennisPlayersContainerIterator tpci = new TennisPlayersContainerIterator(this);
      tpci.setInorder();
      LinkedList<TennisPlayer> playersList = new LinkedList<TennisPlayer>();
      while (tpci.hasNext()) {
         TennisPlayer p = tpci.next().getPlayer();
         playersList.add(p);
      }
      return FXCollections.observableList(playersList);
   }
   
   // Desc.:   Creates an ObservableList of TennisMatch objects of a player
   // Input:   String representing the uid of the player who's matches should be printed
   // Output:  ObservableList of TennisMatch objects. Throws an exception if player doesn't exist in tree
   public ObservableList<TennisMatch> getMatchesOfPlayer(String id)
      throws TennisDatabaseRuntimeException {
      TennisPlayerNode pNode = this.retrievePlayer(this, id);
      if (pNode == null) { throw new TennisDatabaseRuntimeException("Error getting matches of player! Player does not exist."); }
      
      return pNode.getMatches();
   }
   
   // Desc.:   Writes all tennis player objects to text file in preorder to preserve underlying structure for reimportation
   // Input:   Pre-configured PrintWrite object for writing to the text file
   // Output:  All tennis player information formatted and exported to a text file
   public void treeToFile(PrintWriter writer) {
      TennisPlayersContainerIterator iter = new TennisPlayersContainerIterator(this);
      iter.setPreorder();
      
      while(iter.hasNext()) { // Binary search tree traversal using iterator.
         TennisPlayerNode pNode = iter.next();
         TennisPlayer p = pNode.getPlayer();
         String uid = p.getId();
         String first = p.getFirstName();
         String last = p.getLastName();
         String year = String.format("%04d", p.getBirthYear());
         String country = p.getCountry();
         
         writer.println("PLAYER/" + uid + "/" + first + "/" + last + "/" + year + "/" + country);
      }
   }
}
// © 2018 Jeremy Maxey-Vesperman