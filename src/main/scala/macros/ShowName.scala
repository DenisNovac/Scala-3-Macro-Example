package macros

import scala.deriving._
import scala.compiletime._
import scala.quoted.*
import scala.quoted._


trait ShowName[T] {
  def templateName(p: String, source: Option[String]): String
}

object ShowName {

  // we are enclosing match inside ${ ... } like this so it will be calculated like in inline
  // but without inline's limitations
  inline private def _helper[T](inline label: String): ShowName[T] = 
    ${ ShowLabelMacro.showLabelExpr[T]('label) }

  // you need helper because you can't pass m.MirroredLabel inside ${ } directly
  inline given derived[T](using m: Mirror.Of[T]): ShowName[T] = 
    _helper[T](constValue[m.MirroredLabel])

}