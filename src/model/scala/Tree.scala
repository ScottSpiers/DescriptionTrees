package model.scala

import collection.JavaConverters._;

sealed abstract class Tree {
  
  
  def setValue(n : Int, i : Int) : Tree = (this, i) match {
    case (Empty(), i) => Empty()
    case (Leaf(m), 0) => Leaf(n)
    case (Leaf(m), i) => setValue(n, i-1)
    case (Node(m, xs), 0) => Node(n, xs)
    case (Node(m, xs), i) => Node(m, setValue(n, i-1, xs))
  }
  
  private def setValue(n : Int, i : Int, xs : List[Tree]) : List[Tree] = (xs, i) match {
    case (Nil, i) => Nil
    case (Leaf(m) :: xs, 0) => Leaf(n) :: xs
    case (Node(m, ys) :: xs, 0) => Node(n, ys) :: xs
    case(Node(m, ys) :: xs, i) => Node(m, setValue(n, i, ys)) :: xs
    case (x :: xs, i) => x :: setValue(n, i -1, xs)
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
    case (Leaf(n), i) => Empty()
    case (Node(n, Leaf(m) :: xs), 0) => Node(n, Node(m, (Leaf(0) :: Nil)) :: xs)
    case (Node(n, Leaf(m) :: xs), i) => Node(n, (Leaf(m) :: addLeafToLeaf(xs, i -1)))
    case (Node(n, Node(m, ys) :: xs), i) => Node(n, (Node(m, ys) :: addLeafToLeaf(xs, i)))
  }
  
  private def addLeafToLeaf(xs : List[Tree], i : Int) : List[Tree] = (xs, i) match {
    case (Nil, i) => Nil
    case (Leaf(n) :: xs, 0) => Node(n, (Leaf(0) :: Nil)) :: xs
    case (Leaf(n) :: xs, i) => Leaf(n) :: addLeafToLeaf(xs, i-1)
    case (Node(n, (Leaf(m) :: xs)) :: ys, 0) => Node(n, Node(m, Leaf(0) :: Nil) :: xs) :: ys
    case (Node(n, (Leaf(m) :: xs)) :: ys, i) => Node(n, (Leaf(m) :: xs)) :: addLeafToLeaf(ys, i-1)
    case (Node(n, Node(m, xs) :: ys) :: zs, i) => Node(n, Node(m, xs) :: ys) :: addLeafToLeaf(zs, i)
  }
  
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
  
  def getLeaves(l : List[Tree]) : List[Tree] = l match {
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
  
}

case class Empty() extends Tree
case class Leaf(n : Int) extends Tree
case class Node(n : Int, ns : List[Tree]) extends Tree