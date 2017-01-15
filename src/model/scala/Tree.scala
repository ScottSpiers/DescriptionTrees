package model.scala

import collection.JavaConverters._;

sealed abstract class Tree {
  
  
  def setAllLeafValues(n : Int) : Tree = this match {
    case (Empty()) => Empty()
    case (Leaf(m)) => Leaf(n)
    case (Node(m, xs)) => Node(m, setAllLeafValues(n, xs))
  }
  
  private def setAllLeafValues(n : Int, xs : List[Tree]) : List[Tree] = xs match {
    case Nil => Nil
    case Leaf(m) :: xs => Leaf(n) :: setAllLeafValues(n, xs)
    case Node(m, ys) :: xs => Node(m, setAllLeafValues(n, ys)) :: setAllLeafValues(n, xs)
    case x :: xs => x :: setAllLeafValues(n, xs)
  }
  
  def setNodeValue(n : Int, i : Int) : Tree = (this, i) match {
    case (Empty(), i) => Empty()
    case (Leaf(m), i) => Leaf(m)
    case (Node(m, xs), 0) => Node(n, xs)
    case (Node(m, xs), i) => Node(m, setNodeValue(n, i-1, xs))
  }
  
  private def setNodeValue(n : Int, i : Int, xs : List[Tree]) : List[Tree] = (xs, i) match {
    case (Nil, i) => Nil
    case (Leaf(m) :: xs, i) => (Leaf(m) :: setNodeValue(n, i, xs))
    case (Node(m, ys) :: xs, 0) => (Node(n, ys) :: xs)
    case (Node(m, ys) :: xs, i) => if (i <= numNodes(ys)) {
      (Node (m, setNodeValue(n, i- 1, ys)) :: xs)
    }
    else {
      (Node(m, ys) :: setNodeValue(n, i- numNodes(ys) -1, xs))
    }
  }
  
  private def numNodes(xs : List[Tree]) : Int = xs match {
    case Nil => 0
    case Empty() :: xs => 0 + numNodes(xs)
    case Leaf(n) :: xs => 0 + numNodes(xs)
    case Node(n, ys) :: xs => 1 + numNodes(ys) + numNodes(xs)
  }
  
  def addRoot() : Tree = this match {
    case Empty() => Leaf(0)
    case Leaf(n) => Node(0, Leaf(n) :: Nil)
    case Node(n, xs) => Node(0, (Node(n, xs) :: Nil))
  }
  
  def addLeaf() : Tree = this match {
    case Empty() => Leaf(0)
    case Leaf(n) => Node(n , Leaf(0) :: Nil)
    case Node(n, xs) => Node(n, xs ++ (Leaf(0) :: Nil))
  }
  
  def addLeafToLeaf(i : Int) : Tree = (this, i) match  {
    case (Empty(), i) => Empty()
    case (Leaf(n), 0) => Node(n, (Leaf(0) :: Nil))
    case (Leaf(n), i) => Leaf(n)
    case (Node(n, xs), i) => Node(n, addLeafToLeaf(xs, i))
  }
  
  private def addLeafToLeaf(xs : List[Tree], i : Int) : List[Tree] = (xs, i) match {
    case (Nil, i) => Nil
    case (Leaf(n) :: xs, 0) => Node(n, (Leaf(0) :: Nil)) :: xs
    case (Leaf(n) :: xs, i) => Leaf(n) :: addLeafToLeaf(xs, i-1)
    case ((Node(n, ys) :: xs), i) => if(i < numLeaves(ys)) {
      (Node(n, addLeafToLeaf(ys, i)) :: xs)
    }
    else {
      (Node(n, ys) :: addLeafToLeaf(xs, i- numLeaves(ys)))
    }
  }
  
  def addLeafToNode(i : Int) : Tree = (this, i) match {
    case (Empty(), i) => Empty()
    case (Leaf(n), i) => Leaf(n)
    case (Node(n, xs), 0) => Node(n, xs ++ (Leaf(0) :: Nil))
    case (Node(n, xs), i) => Node(n, addLeafToNode(xs, i-1))
  }
  
  private def addLeafToNode(xs : List[Tree], i : Int) : List[Tree] = (xs, i) match {
    case (Nil, i) => Nil
    case ((Leaf(n) :: xs), i) => Leaf(n) :: addLeafToNode(xs, i)
    case ((Node(n, ys) :: xs), 0) => Node(n, ys ++ (Leaf(0) :: Nil)) :: xs
    case ((Node(n, ys) :: xs), i) => if(i < numNodes(ys)) {
      (Node(n, addLeafToNode(ys, i-1)) :: xs)
    }
    else {
      (Node(n, ys) :: addLeafToNode(xs, i - numNodes(ys) - 1))
    }
  }
  
  def addLeafAsFirstChild() : Tree = this match {
    case Empty() => Leaf(0)
    case Leaf(n) => Node(n, (Leaf(0) :: Nil))
    case Node(n, xs) => Node(n, Leaf(0) :: xs)
  }
  
  def addLeafAsChildAt(i : Int) : Tree = (this, i) match {
    case (Empty(), i) => Leaf(0)
    case (Leaf(n), i) => Node(n, (Leaf(0) :: Nil))
    case (Node(n, xs), 0) => Node(n, (Leaf(0) :: xs))
    case (Node(n, x :: xs), i) => Node(n, x :: addLeafToChildAt(xs, i-1))
  }
  
  private def addLeafToChildAt(xs : List[Tree], i : Int) : List[Tree] = (xs, i) match {
    case (Nil, i) => Nil
    case ((ts), 0) => (Leaf(0) ::  ts)
    case ((t :: ts), i) => t :: addLeafToChildAt(xs, i-1)
  }
  
  /*private def numLeaves(xs : List[Tree]) : Int = xs match {
    case Nil => 0
    case Leaf(n) :: xs => 1 + numLeaves(xs)
    case Node(n, ys) :: xs => 0 + numLeaves(ys) + numLeaves(xs)
  }*/
  
  def addNode(t : Tree) : Tree = this match {
    case Empty() => t
    case Leaf(n) => Node(n, t :: Nil)
    case Node(n, ns) => Node(n, ns ++ (t :: Nil))
  }
  
  def addSubtrees(t : Tree) : Tree = (this, t) match {
    case (Empty() , t) => t
    case (Leaf(n), Node(m, xs)) => Node(n, xs)
    case (Leaf(n), Leaf(m)) => Node(n, Leaf(m) :: Nil)
    case (Leaf(n), Empty()) => Leaf(n)
    case (Node(n, xs), Node(m, ys)) => Node(n, xs ++ ys)
    case (Node(n, xs), Leaf(m)) => Node(n, xs ++ (Leaf(m) :: Nil))
    case (Node(n, xs), Empty()) => Node(n, xs)
  }
  
  
  def getLeaves() : java.util.List[Tree] = this match {
    case Empty() => Nil.asJava
    case Leaf(n) => (Leaf(n) :: getLeaves(Nil)).asJava
    case Node(n, xs) => getLeaves(xs).asJava
  }
  
  private def getLeaves(l : List[Tree]) : List[Tree] = l match {
    case Nil => Nil
    case Leaf(n) :: xs => Leaf(n) :: getLeaves(xs)
    case Node(n, xs) :: ls => getLeaves(xs) ++ getLeaves(ls)
  }
  
  def getNodes() : java.util.List[Tree] = this match {
    case Empty() => Nil.asJava
    case Leaf(n) => Nil.asJava
    case Node(n, xs) => (Node(n, xs) :: getNodes(xs)).asJava
  }  
  
  def getNodes(xs : List[Tree]) : List[Tree] = xs match {
    case Nil => Nil
    case t :: ts => getNodes(ts) ++ t.getNodes().asScala
  }
  
  def getAllChildren() : java.util.List[Tree] = this match {
    case Empty() => Nil.asJava
    case Leaf(n) => Nil.asJava
    case Node(n, xs) => xs.asJava
  }
  
  def getChild(i : Int) : Tree = (this, i) match {
    case (Empty(), i) => Empty()
    case (Leaf(n), i) => Empty()
    case (Node(n, x :: xs), 0) => x
    case (Node(n, x :: xs), i) => getChild(i-1, xs)
  } 
    
   private def getChild(i : Int, l : List[Tree]) : Tree = (i, l) match {
     case (i, Nil) => Empty();
     case (0, t :: ts) => t
     case (i, t :: ts) => getChild(i-1, ts)
   }
  
  def getNumVertices(t : Tree) : Int = t match {
    case Empty() => 0
    case Leaf(n) => 1
    case Node(n, xs) => 1 + getNumVertices(xs)
  }
  
  private def getNumVertices(l : List[Tree]) : Int = l match {
    case Nil => 0
    case t :: ts => getNumVertices(t) + getNumVertices(ts)
  }
  
  def getNumLeaves(t : Tree) : Int = t match {
    case Empty() => 0
    case Leaf(n) => 1
    case Node(n, xs) => numLeaves(xs)
  }
  
  private def numLeaves(ts : List[Tree]) : Int = ts match {
    case Nil => 0
    case t :: ts => getNumLeaves(t) + numLeaves(ts)
  }
  
  def getNumChildren() : Int = this match {
    case Empty() => 0
    case Leaf(n) => 0
    case Node(n, xs) => xs.size
  }
  
  def getValue() : Int = this match {
    case Empty() => 0
    case Leaf(n) => n
    case Node(n, xs) => n
  }
  
}

case class Empty() extends Tree
case class Leaf(n : Int) extends Tree
case class Node(n : Int, ns : List[Tree]) extends Tree