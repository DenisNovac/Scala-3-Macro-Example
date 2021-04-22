package macros

import scala.deriving._
import scala.compiletime._
import scala.quoted.*


/* Should pass the compile */

case class TestMappingTemplate() derives ShowName {
  //...
}

case class TwoWordsMappingTemplate() derives ShowName {
  // ...
}

case class SimpleMappingTemplate() derives ShowName {
  // ...
}

/* should fail on compile */

case class SomeOtherThing() derives ShowName {
  // ...
}


object Main extends App {


  extension[T](t: T) {
    def getTemplateName(p: String, source: Option[String] = Some(""))(using ShowName[T]) =
      summon[ShowName[T]].templateName(p, source)
  }


  println(TestMappingTemplate().getTemplateName("pref", Some("source")))  

  println(SimpleMappingTemplate().getTemplateName("pref"))

  println(TwoWordsMappingTemplate().getTemplateName("pref"))

  //println(SomeOtherThing().getTemplateName())

}


