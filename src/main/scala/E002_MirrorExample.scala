import scala.deriving._
import scala.compiletime._

case class Person(name: String, age: Int)

object E002_MirrorExample extends App {

  /* Runtime */

  def showPerson(p: Person) =
    showFieldsForProduct[Person]
      .zip(p.asInstanceOf[Product].productIterator.toList)
      .toMap

  /* Compile-time */

  inline def showFieldsForProduct[T](using m: Mirror.Of[T]): List[String] =
    inline m match {
      case s: Mirror.SumOf[T] => ???
      case p: Mirror.ProductOf[T] =>
        println(constValue[m.MirroredLabel].toString)  // m.MirroredLabel = Person extends String
        showFieldsNames[m.MirroredElemLabels]
    }

  inline def showFieldsNames[T <: Tuple]: List[String] =
    inline erasedValue[T] match {
      case _: EmptyTuple => Nil
      case _: (t *: tail) => constValue[t].toString :: showFieldsNames[tail]
    }


  println(showPerson(Person("Sam", 32)))
  //Person
  //Map(name -> Sam, age -> 32)
}





