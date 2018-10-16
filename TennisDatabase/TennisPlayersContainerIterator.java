/*
 *    Class Name: TennisPlayersContainerIterator
 *    Interface:  java.util.Iterator<TennisPlayerNode>
 *    Package:    TennisDatabase
 *    Purpose:    Provides an iterator to traverse the TennisPlayersContainer binary search tree.
 *    Developer:  Jeremy Maxey-Vesperman
 *    Modified:   06/08/2018
 */

package TennisDatabase;

import java.util.LinkedList;

public class TennisPlayersContainerIterator implements java.util.Iterator< TennisPlayerNode > {
   private TennisPlayersContainer binTree; // the binary tree to iterate through
   private TennisPlayerNode currNode; // current node in traversal of tree
   private LinkedList< TennisPlayerNode > queue; // for traversal
   
   // Constructor - Default initializes data structures for the interator
   public TennisPlayersContainerIterator( TennisPlayersContainer tpc ) {
      binTree = tpc;
      currNode = null;
      
      // Empty queue = no traversal type selected, or end of traversal
      queue = new LinkedList< TennisPlayerNode > ();
      
   }
   
   // Desc.:   Function provides the caller the ability to check if the queue has a next item to get
   // Output:  Boolean indicating whether the queue has a next item
   public boolean hasNext() { return !queue.isEmpty(); }
   
   // Desc.:   Retrieves the next TennisPlayerNode from queue
   // Output:  Returns next TennisPlayerNode object. Throws exception if there is no next
   public TennisPlayerNode next() throws java.util.NoSuchElementException {
      currNode = queue.remove();
      return currNode;
   }
   
   // Desc.:   Unsupported operation for this implementation.
   // Output:  Throws an exception when called.
   public void remove() throws UnsupportedOperationException {
      throw new UnsupportedOperationException( "TennisPlayersContainerIterator does not support remove() operation!" );
   }
   
   /* Traversal methods */
   // Preorder
   // Desc.:   Sets the traversal method of the binary search tree to preorder and fills the queue appropriately
   public void setPreorder() {
      queue.clear();
      preorder(binTree.getRoot());
   }
   
   // Desc.:   Method traverses the bst and creates a preorder queue of TennisPlayerNodes
   public void preorder( TennisPlayerNode tpNode ) {
      if (tpNode != null) {
         queue.add(tpNode);
         preorder(tpNode.getLeftChild());
         preorder(tpNode.getRightChild());
      }
   }
   
   // Inorder
   // Desc.:   Sets the traversal method of the binary search tree to inorder and fills the queue appropriately
   public void setInorder() {
      queue.clear();
      inorder(binTree.getRoot());
   }
   
   // Desc.:   Method traverses the bst and creates an inorder queue of TennisPlayerNodes
   public void inorder( TennisPlayerNode tpNode ) {
      if (tpNode != null) {
         inorder(tpNode.getLeftChild());
         queue.add(tpNode);
         inorder(tpNode.getRightChild());
      }
   }
   
   // Postorder
   // Desc.:   Sets the traversal method of the binary search tree to postorder and fills the queue appropriately
   public void setPostorder() {
      queue.clear();
      postorder(binTree.getRoot());
   }
   
   // Desc.:   Method traverses the bst and creates a postorder queue of TennisPlayerNodes
   public void postorder( TennisPlayerNode tpNode ) {
      if (tpNode != null) {
         postorder(tpNode.getLeftChild());
         postorder(tpNode.getRightChild());
         queue.add(tpNode);
      }
   }
}
// © 2018 Jeremy Maxey-Vesperman