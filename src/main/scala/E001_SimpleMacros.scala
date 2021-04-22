
object E001_SimpleMacros extends App {

  inline def matchType[T]: String =
    inline scala.compiletime.erasedValue[T] match {
      case _: String => "Is is a String"
      case _: Int => "It is an Int"
      case _ => throw new Error("")
    }

  println(matchType[Int])

  println("It is an int")

}



