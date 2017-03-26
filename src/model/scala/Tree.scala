package model.scala

import scala.collection.JavaConverters._

/**
 * @author Scott Spiers
 * University of Strathclyde
 * Final Year Project: Description Trees
 * Supervisor: Sergey Kitaev
 * 
 * Provides the definition of the Tree data type used within the DescriptionTrees application
 */
sealed abstract class Tree {
  
  /**
   * Set all leaf values to the integer provided
   */
  def setAllLeafValues(n : Int) : Tree = this match {
    case (Empty()) => Empty()
    case (Leaf(m)) => Leaf(n)
    case (Node(m, xs)) => Node(m, setAllLeafValues(n, xs)) //call helper function to access the rest of the tree
  }
  
  /**
   * Helper function for setting all leaf values
   */
  private def setAllLeafValues(n : Int, xs : List[Tree]) : List[Tree] = xs match {
    case Nil => Nil
    case Leaf(m) :: xs => Leaf(n) :: setAllLeafValues(n, xs)
    case Node(m, ys) :: xs => Node(m, setAllLeafValues(n, ys)) :: setAllLeafValues(n, xs)
    case x :: xs => x :: setAllLeafValues(n, xs) //catch all case
  }
  
  /**
   * Sets a specific node's value to that provided
   */
  def setNodeValue(n : Int, i : Int) : Tree = (this, i) match {
    case (Empty(), i) => Empty()
    case (Leaf(m), i) => Leaf(m)
    case (Node(m, xs), 0) => Node(n, xs)
    case (Node(m, xs), i) => Node(m, setNodeValue(n, i-1, xs)) //call helper method using decremented counter
  }
  
  /**
   * Helper function to set a specific nodes value
   */
  private def setNodeValue(n : Int, i : Int, xs : List[Tree]) : List[Tree] = (xs, i) match {
    case (Nil, i) => Nil
    case (Empty() :: xs, i) => Empty() :: setNodeValue(n, i, xs)  //not a Node don't decrement  
    case (Leaf(m) :: xs, i) => (Leaf(m) :: setNodeValue(n, i, xs)) //not a Node don't decrement
    case (Node(m, ys) :: xs, 0) => (Node(n, ys) :: xs) //the node we are looking for return
    case (Node(m, ys) :: xs, i) => if (i <= numNodes(ys, 0)) { //is the node we are looking for within the current node?
      (Node (m, setNodeValue(n, i- 1, ys)) :: xs) //if so decrement and search this node
    }
    else { //otherwise 
      (Node(m, ys) :: setNodeValue(n, i- numNodes(ys, 0) -1, xs)) //decrement for all noes within the current node and this node, then search the rest of the tree
    }
  }
  
  /**
   * Get the number of nodes
   */
  private def numNodes(xs : List[Tree], i : Int) : Int = xs match {
    case Nil => i
    case Empty() :: xs => numNodes(xs, i)
    case Leaf(n) :: xs => numNodes(xs, i)
    case Node(n, ys) :: xs => numNodes(ys, i + 1 + numNodes(xs, i))
  }
  
  /**
   * Makes the current tree the child of a new root node
   */
  def addRoot() : Tree = this match {
    case Empty() => Leaf(0)
    case Leaf(n) => Node(0, Leaf(n) :: Nil)
    case Node(n, xs) => Node(0, (Node(n, xs) :: Nil))
  }
  
  /**
   * Adds a leaf to the root node
   */
  def addLeaf() : Tree = this match {
    case Empty() => Leaf(0)
    case Leaf(n) => Node(n , Leaf(0) :: Nil)
    case Node(n, xs) => Node(n, xs ++ (Leaf(0) :: Nil))
  }
  
  /**
   * Adds a leaf to a specified leaf
   */
  def addLeafToLeaf(i : Int) : Tree = (this, i) match  {
    case (Empty(), i) => Empty()
    case (Leaf(n), 0) => Node(n, (Leaf(0) :: Nil)) //this is the leaf we want make it a node with one child
    case (Leaf(n), i) => Leaf(n) //no such leaf i just return
    case (Node(n, xs), i) => Node(n, addLeafToLeaf(xs, i)) //call helper function to access the rest of the tree
  }
  
  /**
   * Helper function for adding a leaf to a leaf
   */
  private def addLeafToLeaf(xs : List[Tree], i : Int) : List[Tree] = (xs, i) match {
    case (Nil, i) => Nil
    case ((Empty() :: xs), i) => Empty() :: addLeafToLeaf(xs, i) //not a leaf don't decrement
    case (Leaf(n) :: xs, 0) => Node(n, (Leaf(0) :: Nil)) :: xs //the leaf we are looking for, make it a node with one child and return
    case (Leaf(n) :: xs, i) => Leaf(n) :: addLeafToLeaf(xs, i-1) //not the leaf we are looking for, decrement and continue
    case ((Node(n, ys) :: xs), i) => if(i < numLeaves(ys,0)) { //is the leaf we are looking for in the current node
      (Node(n, addLeafToLeaf(ys, i)) :: xs) //if so look within this node for the leaf
    }
    else { //otherwise
      (Node(n, ys) :: addLeafToLeaf(xs, i- numLeaves(ys,0))) //decrement by the number of leaves within the node, and continue
    }
  }
  
  /**
   * Adds a leaf to a node
   */
  def addLeafToNode(i : Int) : Tree = (this, i) match {
    case (Empty(), i) => Empty()
    case (Leaf(n), i) => Leaf(n) //not a node can't add
    case (Node(n, xs), 0) => Node(n, xs ++ (Leaf(0) :: Nil)) //The node we're looking for, add a leaf to it
    case (Node(n, xs), i) => Node(n, addLeafToNode(xs, i-1)) //Not the node we're looking for, decrement and call helper function
  }
  
  /**
   * Helper function to add a leaf to a node
   */
  private def addLeafToNode(xs : List[Tree], i : Int) : List[Tree] = (xs, i) match {
    case (Nil, i) => Nil
    case (Empty() :: xs, i) => Empty() :: addLeafToNode(xs, i) //not a node continue
    case ((Leaf(n) :: xs), i) => Leaf(n) :: addLeafToNode(xs, i) //not a node continue
    case ((Node(n, ys) :: xs), 0) => Node(n, ys ++ (Leaf(0) :: Nil)) :: xs //the node we're looking for, add a leaf to it
    case ((Node(n, ys) :: xs), i) => if((i - 1) < numNodes(ys, 0)) { //if the node we want is in the current node 
      (Node(n, addLeafToNode(ys, i-1)) :: xs) //decrement and look within the node
    }
    else { //otherwise
      (Node(n, ys) :: addLeafToNode(xs, i - numNodes(ys, 0) - 1)) //search the rest of the tree
    }
  }
  
  /**
   * Adds a leaf as the first child of the root
   */
  def addLeafAsFirstChild() : Tree = this match {
    case Empty() => Leaf(0)
    case Leaf(n) => Node(n, (Leaf(0) :: Nil))
    case Node(n, xs) => Node(n, Leaf(0) :: xs)
  }
  
  /**
   * Adds a leaf as the ith child of the root
   */
  def addLeafAsChildAt(i : Int) : Tree = (this, i) match {
    case (Empty(), i) => Leaf(0) //Not a node just add the leaf
    case (Leaf(n), i) => Node(n, (Leaf(0) :: Nil)) // Not a node just add the leaf
    case (Node(n, Nil), i) => Node(n, Leaf(0) :: Nil) // No children just add the leaf
    case (Node(n, xs), 0) => Node(n, (Leaf(0) :: xs)) //the position we're looking for add the leaf
    case (Node(n, x :: xs), i) => Node(n, x :: addLeafToChildAt(xs, i-1)) //not the position we're looking for derement and call the helper function
  }
  
  /**
   * Helper function to add a leaf as the ith child to the root
   */
  private def addLeafToChildAt(xs : List[Tree], i : Int) : List[Tree] = (xs, i) match {
    case (Nil, i) => Nil
    case ((t :: ts), 0) => (Leaf(0) :: t ::  ts)
    case ((t :: ts), i) => t :: addLeafToChildAt(ts, i-1)
  }
  
  /**
   * Adds a node with all of its structure to the root
   */
  def addNode(t : Tree) : Tree = this match {
    case Empty() => t
    case Leaf(n) => Node(n, t :: Nil)
    case Node(n, ns) => Node(n, ns ++ (t :: Nil))
  }
    
  /**
   * Gets the leaves of the tree as a Java list
   */
  def getLeaves() : java.util.List[Tree] = this match {
    case Empty() => Nil.asJava
    case Leaf(n) => (Leaf(n) :: getLeaves(Nil)).asJava //require call to getLeaves due to conversion to Java
    case Node(n, xs) => getLeaves(xs).asJava // call helper to get the rest of the leaves from the tree
  }
  
  /**
   * Helper function to get leaves of the tree
   */
  private def getLeaves(l : List[Tree]) : List[Tree] = l match {
    case Nil => Nil
    case Empty() :: xs => Nil
    case Leaf(n) :: xs => Leaf(n) :: getLeaves(xs) //we have a leaf add it
    case Node(n, xs) :: ls => getLeaves(xs) ++ getLeaves(ls) //node add all leaves within the node and those within the rest of the tree
  }
  
  /**
   * Gets the nodes of the tree
   */
  def getNodes() : java.util.List[Tree] = this match {
    case Empty() => Nil.asJava //no nodes
    case Leaf(n) => Nil.asJava //no nodes
    case Node(n, xs) => (Node(n, xs) :: getNodes(xs)).asJava //add the current node and call the helper function to get the rest
  }  
  
  /**
   * Helper function to get all nodes
   */
  private def getNodes(xs : List[Tree]) : List[Tree] = xs match {
    case Nil => Nil
    case t :: ts => getNodes(ts) ++ t.getNodes().asScala 
  }
  
  /**
   * Gets all the children of the root node
   */
  def getAllChildren() : java.util.List[Tree] = this match {
    case Empty() => Nil.asJava //no children
    case Leaf(n) => Nil.asJava //no children
    case Node(n, xs) => xs.asJava //return its children as a Java list
  }
  
  /**
   * Gets the child at position i
   */
  def getChild(i : Int) : Tree = (this, i) match {
    case (Empty(), i) => Empty() //No child
    case (Leaf(n), i) => Empty() //No child
    case (Node(n, Nil), i) => Empty() //No child
    case (Node(n, x :: xs), 0) => x //We have the child we want
    case (Node(n, x :: xs), i) => getChild(i-1, xs) //decrement and call helper function
  } 
    
  /**
   * Helper function to get child at position i
   */
   private def getChild(i : Int, l : List[Tree]) : Tree = (i, l) match {
     case (i, Nil) => Empty() //no child
     case (0, t :: ts) => t //the child we want
     case (i, t :: ts) => getChild(i-1, ts) //decrement and continue
   }
  
   /**
    * Gets the number of vertices
    */
  def getNumVertices(i : Int) : Int = this match {
    case Empty() => i //this is not a vertice
    case Leaf(n) => i + 1 //increment
    case Node(n, xs) => getNumVertices(xs, i + 1) //call the helper function
  }
  
  /**
   * Helper function to get num vertices
   */
  private def getNumVertices(l : List[Tree], i : Int) : Int = l match {
    case Nil => i //no more vertices
    case t :: ts => t.getNumVertices(getNumVertices(ts, i)) //count the number of vertices in the rest of the tree and then in the current leaf or node
  }
  
  /**
   * Gets the number of leaves
   */
  def getNumLeaves(t : Tree, i :Int) : Int = t match {
    case Empty() => i
    case Leaf(n) => i + 1
    case Node(n, xs) => numLeaves(xs, i) //call the helper function to count the leaves in the rest of the tree
  }
  
  /**
   * Helper function for getting the number of leaves
   */
  private def numLeaves(ts : List[Tree], i : Int) : Int = ts match {
    case Nil => i
    case t :: ts => getNumLeaves(t, numLeaves(ts, i)) //count the leaves in the rest of the tree and then in the current leaf or node
  }
  
  /**
   * Gets the number of children of the root
   */
  def getNumChildren() : Int = this match {
    case Empty() => 0 //no children
    case Leaf(n) => 0 //no children
    case Node(n, xs) => xs.size //simply return the list size
  }
  
  /**
   * Gets the roots value
   */
  def getValue() : Int = this match {
    case Empty() => 0 //no value
    case Leaf(n) => n 
    case Node(n, xs) => n
  }
  
  /**
   * Gets the width at the specified level
   */
  def getWidth(i : Int) : Int = (this, i) match {
    case (Empty(), i) => 0 //no width
    case (Leaf(n), 0) => 1 //width of 1
    case (Leaf(n), i) => 0 //not the correct level
    case (Node(n, xs), 0) => 1 //width of 1
    case (Node(n, xs), i) => getWidth(xs, i-1) //not the correct level, decrement then call the helper function
  }
  
  /**
   * Helper function for getting the width at level i
   */
  private def getWidth(xs : List[Tree], i : Int) : Int = (xs, i) match {
    case (Nil, i) => 0 //no width
    case (Empty() :: xs, i) => getWidth(xs, i) //nothing here continue on this level
    case ((Leaf(n) :: xs), 0) => 1 + getWidth(xs, 0) //width of 1 plus continue
    case ((Leaf(n) :: xs), i) => getWidth(xs, i) //not the correct level, continue
    case ((Node(n, ys) :: xs), 0) => 1 + getWidth(xs, 0) //width of 1 plus continue
    case ((Node(n, ys) :: xs), i) => getWidth(ys, i-1) + getWidth(xs, i) //not the correct level, decerement and continue down the tree, then search the rest of the tree
  }
  
  /**
   * Gets the depth of the tree
   */
  def getDepth() : Int = this match {
    case Empty() => 0 //no depth
    case Leaf(n) => 0 //no depth
    case Node(n, Nil) => 0 //no depth
    case Node(n, xs) => 1 + getDepth(xs) //depth of at least 1, call helper function to continue
  }
  
  /**
   * Helper function to return the max depth
   */
  private def getDepth(xs : List[Tree]) : Int = {
    var max = 0; //keep track of the max
    for((Node(n, ys)) <- xs) { // for every node in the tree
      var cur = 0;
      if(ys.size > 0) { //if the node has children
    	  cur +=  1; //we have a depth of at least 1
      }
      cur += getDepth(ys); // continue down the path
      
      if(cur > max) { //if the current depth is greater than max
        max = cur; //set max to cur
      }
    }
    return max;  
  }
}

case class Empty() extends Tree
case class Leaf(n : Int) extends Tree
case class Node(n : Int, ns : List[Tree]) extends Tree