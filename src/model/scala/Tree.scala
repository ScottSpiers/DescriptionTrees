package model.scala

import collection.JavaConverters._;

sealed abstract class Tree {
  
  def apply() = new Leaf(0)
  
  def addLeaf() : Tree = this match {
    case Empty() => Leaf(0)
    case Leaf(n) => Node(n , Leaf(0) :: Nil)
    case Node(n, Nil) => Node(n, Leaf(0) :: Nil)
    case Node(n, xs) => Node(n, xs ++ (Leaf(0) :: Nil))
  }
  
  def getNodes() : java.util.List[Tree] = this match {
    case Empty() => Nil.asJava
    case Leaf(n) => Nil.asJava
    case Node(n, Nil) => Nil.asJava
    case Node(n, xs) => xs.asJava
  }  
  
  def getChild(i : Int) : Tree = {
    var nodes = getNodes().asScala;
    if(!nodes.isEmpty) {
    	val node = nodes(i);
    	return node;      
    }
    
    return Empty();
  }
  
  def getNumLeaves(t : Tree) : Int = this match {
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