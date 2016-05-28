package services

import scala.util.parsing.combinator.RegexParsers

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.Reader

import javax.inject._

@Singleton
class PatternExpEvaluator extends ExpressionEvaluator {

  def apply(input: String): EvaluatorResult = {
    val cleanInput = clean(input)
    val parser = new PatternParser()
    try {
      parser.parseAll(parser.expression, cleanInput) match {
        case parser.Success(value, _)        => EvaluatorSuccess(value)
        case parser.NoSuccess(message, next) => EvaluatorFailure(parser.nicerError(cleanInput, message, next))
      }
    } catch {
      case ex: ParserException => EvaluatorFailure(ex.message)
      case e: Exception        => EvaluatorFailure("Unexpected error")
    }
  }

  def clean(input: String): String = {
    input.replaceAll("\n", "")
  }

}

case class ParserException(message: String) extends Exception

class PatternParser extends RegexParsers {

  final val NUMBER_REGEXP = "[-+]?(\\d+(\\.\\d*)?|\\.\\d+)"

  def number: Parser[BigDecimal] = NUMBER_REGEXP.r ^^ {
    BigDecimal(_)
  }

  def multipliable: Parser[BigDecimal] = "(" ~> summedTerms <~ ")" | number

  def mul: Parser[BigDecimal] = multipliable ~ rep(("*" | "/") ~ multipliable) ^^ {
    case base ~ list => list.foldLeft(base) {
      case (z, "*" ~ n) => z * n
      case (z, "/" ~ n) => if (n != 0) { z / n } else {
        throw ParserException("Division by zero")
      }
    }
  }

  def term: Parser[BigDecimal] = mul | number

  def summedTerms: Parser[BigDecimal] = term ~ rep(("+" | "-") ~ term) ^^ {
    case base ~ list => list.foldLeft(base) {
      case (z, "+" ~ s) => z + s
      case (z, "-" ~ s) => z - s
    }
  }

  def expression: Parser[BigDecimal] = summedTerms

  def nicerError(input: String, message: String, next: Reader[Char]): String = {
    // Until proper error reporting from parser, lets make number parsing errors a bit user friendly
    val nice = message.replace(NUMBER_REGEXP, "number").replaceAll("string matching regex ", "")
    f"Parsing failed due to [$nice] on input [$input] at position [${next.pos.column}]"
  }
}
