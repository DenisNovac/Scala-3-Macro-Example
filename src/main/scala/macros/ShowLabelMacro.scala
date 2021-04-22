package macros

import scala.deriving._
import scala.compiletime._
import scala.quoted.*
import scala.quoted._

object ShowLabelMacro {

  def showLabelExpr[T](label: Expr[String])(using Quotes)(using Type[T]): Expr[ShowName[T]] = {
    // rules for class naming
    val twoWords = "(Two)([a-zA-Z]+)(Mapping)([a-zA-Z]+)".r
    val testRegex = "(Test)(Mapping)([a-zA-Z]+)".r
    val simpleRegex = "([a-zA-Z]+)(Mapping)([a-zA-Z]+)".r

    label.value match {

      case Some(testRegex(doc, _, _)) =>
        '{ // you don't need ToExpr instance with '{ } quotes, but everything outside of it must be lifted
          val d = ${Expr(doc)}  // lift to Expr, since doc is outside of '{ }
          new ShowName[T] {
            def templateName(p: String, source: Option[String]): String =
              s"${p}.template.$d${source.map(s => s".$s").getOrElse("")}".toLowerCase
          }
        }

      case Some(twoWords(suggest, suggestType, _, _)) =>
        '{
          val s = ${Expr(suggest)}
          val st = ${Expr(suggestType)}
          new ShowName[T] {
            def templateName(p: String, source: Option[String]): String =
              s"${p}.template.$s.$st".toLowerCase
          }
        }

      case Some(simpleRegex(docType, _, _)) =>
        '{
          val dt = ${Expr(docType)}
          new ShowName[T] {
            def templateName(p: String, source: Option[String]): String =
              s"${p}.template.$dt".toLowerCase
          }
        }

      case Some(value) =>
        quotes.reflect.report.throwError(s"Can't produce such name: $value")

      case None =>
        quotes.reflect.report.throwError(s"None was returned as: ${label.show}")
    }
  }
}
