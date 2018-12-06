package hw10

import scala.collection.immutable.AbstractMap
import scala.language.implicitConversions

object hw10 {
  
  sealed abstract class TreeMap[+A] extends AbstractMap[Int, A] {
    def add[B >: A](kv: (Int, B)): TreeMap[B] = {
      val (key, value) = kv
      
      ???
    }

    def deleteMin: ((Int, A), TreeMap[A]) = {
      require(!this.isEmpty, "Empty map")
      
      ???
    }
    
    def delete(key: Int): TreeMap[A] = ???

    def get(key: Int): Option[A] = this match {
      case Leaf() => None
      case Node(key1, value, left, right) =>
        if (key == key1) Some(value)
        else if (key < key1) left.get(key)
        else right.get(key)
    }
    
    def +[A1 >: A](kv: (Int, A1)): TreeMap[A1] = this.add(kv)

    def -(k: Int): TreeMap[A] = this.delete(k)
    
    override def toList: List[(Int, A)] = this match {
      case Leaf() => Nil
      case Node(key, value, left, right) => 
        left.toList ::: (key, value) :: right.toList
    }

    def iterator: Iterator[(Int, A)] = toList.iterator
  }
  
  case class Leaf() extends TreeMap[Nothing]
  case class Node[+A](key: Int, value: A, 
                      left: TreeMap[A], right: TreeMap[A]) extends TreeMap[A]

  object TreeMap {
    def empty[A]: TreeMap[A] = Leaf()
    
    def apply[A](kvs: (Int, A)*): TreeMap[A] = 
      kvs.toSeq.foldLeft(empty[A])(_ + _)
  }
    
  
}
