## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[dotty-example-project](https://github.com/lampepfl/dotty-example-project/blob/master/README.md).


```scala
sealed trait Animal
object Animal {
  case class Cat(name: String, age: Int) extends Animal
  case class Dog(name: String, age: Int) extends Animal
}


enum Animal {
  case Cat(name: String, age: Int)
  case Dog(name: String, age: Int)
}
```