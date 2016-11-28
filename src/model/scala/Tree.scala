package model.scala

sealed abstract class Tree {
  
}

case class Leaf(n : Int) extends Tree
case class Node(n : Int, ns : List[Tree]) extends Tree