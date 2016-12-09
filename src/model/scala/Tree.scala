package model.scala

sealed abstract class Tree {
  
  def apply() = new Leaf(0)
  
  def addLeaf() : Tree = this match {
    case Leaf(n) => Node(n , Leaf(0) :: Nil)
    case Node(n, Nil) => Node(n, Leaf(0) :: Nil)
    case Node(n, xs) => Node(n, xs ++ (Leaf(0) :: Nil))
  }
  
  
  def getNumLeaves(t : Tree) : Int = this match {
    case Leaf(n) => 1
    case Node(n, xs) => numLeaves(xs)
  }
  
  def numLeaves(ts : List[Tree]) : Int = ts match {
    case Nil => 0
    case t :: ts => getNumLeaves(t) + numLeaves(ts)
  }
}

case class Leaf(n : Int) extends Tree
case class Node(n : Int, ns : List[Tree]) extends Tree