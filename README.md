# Homework 10 (20 Points)

The deadline for Homework 10 is Thursday, December 13, 6pm. The late
submission deadline is Monday, December 17, 6pm.

The purpose of this homework assignment is to practice the use of
generics.

## Getting the code template

Before you perform the next steps, you first need to create your own
private copy of this git repository. To do so, click on the link
provided in the announcement of this homework assignment on
Piazza. After clicking on the link, you will receive an email from
GitHub, when your copy of the repository is ready. It will be
available at
`https://github.com/nyu-pl-fa18/hw10-<YOUR-GITHUB-USERNAME>`.  
Note that this may take a few minutes.

* Open a browser at `https://github.com/nyu-pl-fa18/hw10-<YOUR-GITHUB-USERNAME>` with your Github username inserted at the appropriate place in the URL.
* Choose a place on your computer for your homework assignments to reside and open a terminal to that location.
* Execute the following git command: <br/>
  ```git clone https://github.com/nyu-pl-fa18/hw10-<YOUR-GITHUB-USERNAME>.git```<br/>
  ```cd hw10```

The code template is provided in the file

```
src/main/scala/pl/hw10/hw10.scala
```

relative to the root directory of the repository. Follow the
instructions in the notes for Class 2 to import the project into
InteliJ (or use your other favorite IDE or editor to work on the assignment).

The file

```
src/test/scala/pl/hw10/hw10Spec.scala
```

contains unit tests that help you to ensure that the code that you
write is correct. You can run the unit tests directly from within
InteliJ: right-click on the file in the "Project" overview and choose
"Run 'hw10Spec'". After running the test suite, the IDE will show you
a summary indicating which tests failed (if any).

Alternatively, you can run the tests by executing the command `test`
in the sbt shell. You can do the latter either from within the IDE or
on the command line: open a terminal in the project directory and
execute `sbt`. When the prompt of the sbt shell appears type `test`.

Feel free to add additional test cases to the file `hw10Spec.scala`.


## Submitting your solution

Once you have completed the assignment, you can submit your solution
by pushing the modified code template to GitHub. This can be done by
opening a terminal in the project's root directory and executing the
following commands:

```bash
git add .
git commit -m "solution"
git push
```

You can replace "solution" by a more meaningful commit message.

Refresh your browser window pointing at
```
https://github.com/nyu-pl-fa18/hw10-<YOUR-GITHUB-USERNAME>/
```
and double-check that your solution has been uploaded correctly.

You can resubmit an updated solution anytime by reexecuting the above
git commands. Though, please remember the rules for submitting
solutions after the homework deadline has passed.


## Problem 1: Generic Functional Maps (20 Points)

The goal of this homework is to implement a generic functional map
data structure `TreeMap` that stores key/value pairs using binary
search trees. We want our data structure to be consistent with the
interface of functional maps in Scala's standard API. This interface
is described by the trait `Map[K,+A]` whose documentation you can
find
[here](https://www.scala-lang.org/api/current/scala/collection/Map.html). The
trait takes two type parameters: `K` stands for the type of the keys
stored in the map and `A` stands for the type of the associated
values. Note that this trait is covariant in the value type `A`. To
simplify matters a bit, we fix the type of keys to be `Int` in our
tree map implementation. That is, we will implement a class
`TreeMap[+A]` that extends `Map[Int,A]`.

The `Map` trait provides a large number of methods to interact with
map data structures including complex higher-order methods to iterate
over maps such as `foldLeft` and `foldRight`. Rather than implementing
all of these methods from scratch, we build upon a *skeleton
implementation* of a map data structure that is already provided by
the Scala standard library. This skeleton implementation is given by
the abstract class `AbstractMap[K,+A]` whose documentation you can
find
[here](https://www.scala-lang.org/api/current/scala/collection/immutable/AbstractMap.html).

`AbstractMap` implements all of the methods of `Map` in terms of four
basic methods that every concrete map data structure needs to provide:

* Adding a given key/value pair `(key, value)` into the map: this
  operation returns the new map with the pair `(key, value)`
  inserted. If the old map already contained a binding `(key, value1)`
  for some other value `value1`, then `value1` is replaced by `value`.
* Deleting the key/value pair associated with a given key `key` from the
  map: this operation returns the new map where the pair `(key,
  value)` in the map has been removed if such a pair
  exists. Otherwise, the map is returned unchanged.
* Retrieving the value associated with a given key `key` from the map:
  if a pair `(key, value)` exists in the map, this operation returns
  `Some(value)`, otherwise `None`.
* Obtaining an *iterator* object from the map that abstractly
  describes a traversal over all key/value pairs stored in the map.

Your task will be to implement the first two of these methods. The
other two are already implemented for you.

We implement the binary search trees that we use for our `TreeMap`
data structure as an algebraic data type. In Scala, algebraic data
types are implemented using so-called *case classes*. Here, is how the
case class definition of our binary search tree looks like:

```scala
case class Leaf() extends TreeMap[Nothing]
case class Node[+A](key: Int, value: A, 
                    left: TreeMap[A], right: TreeMap[A]) extends TreeMap[A]
```

The instances of the case class `Leaf` are the leaf nodes of the
tree. That is, these objects describe empty maps. The instances of the
case class `Node` are the inner nodes of the tree. They store the left
and right subtrees as well as a key/value pair. The type `TreeMap` is
similar to the OCaml ADT `'a tree` that we used
in
[Class 9](https://github.com/nyu-pl-fa18/class09#polymorphic-adts). The
only difference is that `TreeMap` is covariant in its value type `A`. 

Note that `Leaf` nodes extend `TreeMap[Nothing]`. The type `Nothing`
is the smallest type in Scala's type hierarchy. That is, it is a
subtype of all other types. This means that `Leaf` instances are also
instances of `TreeMap[A]` for any type `A`.

Similar to ADTs in OCaml, we can pattern match on instances of case
classes using Scala's match expressions, similar to match expressions
in OCaml. The code template contains some examples (see methods
`TreeMap.get` and `TreeMap.toList`.

The actual implementations of the methods of the map data structure
are given in the abstract class `TreeMap` that looks like this:

```scala
  abstract class TreeMap[+A] extends AbstractMap[Int, A] {
  
    def add[A1 >: A](kv: (Int, A1)): TreeMap[A1] = ???

    def deleteMin: ((Int, A), TreeMap[A]) = ???

    def delete(key: Int): TreeMap[A] = ???
    
    ...
  }

```

1. Complete the implementation of the three methods `add`,
   `deleteMin`, and `delete`. The specification of method `deleteMin`
   is as follows: if the current instance `this` is not empty, then it
   should remove the binding `(key, value)` from `this` such that
   `key` is the smallest key among all the bindings in `this`. The
   method should return this pair as well as the new tree map obtained
   after the removal. If `this` is empty, then `deleteMin` should
   fail with an exception (this is already implemented for you). The
   method `deleteMin` will come in handy in your implementation of
   `delete`.
   
   Make sure that your implementations maintain the invariant that the
   trees are strictly sorted according to their keys (remember that a
   map data structure only stores at most one value per key). **(15 Points)**


1. Provide a short code snippet that demonstrates why `TreeMap` cannot be
   contravariant in its type parameter `A`. That is, your code should
   be well typed under the assumption that `TreeMap` is contravariant
   in `A` but would lead to an error if it were allowed to execute
   (e.g. because the code would try to call some method on an instance
   of a class that does not have that method). **(5 Points)**
