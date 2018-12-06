package hw10

import org.scalactic.Tolerance._
import org.scalatest.FlatSpec
import hw10._

class hw10Spec extends FlatSpec {
  import TreeMap._
  
  val n1 = Node(1, 1, empty, Node(2, 2, empty, empty))
  val n2 = Node(2, 2, Node(1, 1, empty, empty), empty)
  val n3 = Node(1, 3, empty, Node(2, 2, empty, empty))
  val n4 = Node(1, 1, Node(-1, 5, empty, empty), Node(2, 3, empty, empty))
  val n5 = Node(1, 1, empty, Node(2, 3, empty, empty))

  "foldLeft" should "implement an in-order fold of the tree" in {

    def strictlySorted[V](t: TreeMap[V]): Boolean =
      t.foldLeft((true, Double.MinValue)){
        case ((b, min), (k, _)) => (b && min < k, k)
      }._1

    assert(strictlySorted(n1))
    assert(strictlySorted(n2))
    assert(strictlySorted(n4))
  }
  
  "equals" should "compare maps structurally" in {
    assert(n1 === n2)
    assert(n1 !== n4)
    assert(n1 !== n5)
  }
  
  
  "get" should "retrieve the value for the given key" in {
    assert(n1.get(1) === Some(1))
    assert(n1.get(2) === Some(2))
    assert(n1.get(3) === None)
    assert(n2.get(1) === Some(1))
    assert(n2.get(2) === Some(2))
  }
  
  "add" should "insert the key/value pair into the map" in {
    assert(TreeMap(1 -> 1, 2 -> 2) === n1)
    assert(n1 + (1 -> 3) === n3)
    assert(n1 + (2 -> 3) === n5)
    assert(n2 + (2 -> 3) === n5)
    assert(n1 + (1 -> 1) === n1)
    assert(n1 + (3 -> "5") === Map(1 -> 1, 2 -> 2, 3 -> "5"))
  }
  
  "delete" should "remove the key and associated value from the map" in {
    assert(n1 - 1 === Map(2 -> 2))
    assert(n2 - 1 === Map(2 -> 2))
    assert(n1 - 2 === Map(1 -> 1))
    assert(n2 - 2 === Map(1 -> 1))
    assert(n1 - 3 === n1)
    assert(n4 - (-1) === n5)
    assert(empty - 1 === empty)
  }
  

}
