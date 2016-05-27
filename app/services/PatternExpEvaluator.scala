package services

import scala.util.parsing.combinator.RegexParsers

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.Reader

import javax.inject._

@Singleton
class PatternExpEvaluator extends ExpressionEvaluator {

  def apply(input: String): EvaluatorResult = {
    val parser = new PatternParser()
    parser.parseAll(parser.expression, input) match {
      case parser.Success(value, _)        => EvaluatorSuccess(value)
      case parser.NoSuccess(message, next) => EvaluatorFailure(errorOut(input, message, next))
    }
  }

  class PatternParser extends RegexParsers {

    def number: Parser[Int] = """[0-9]+""".r ^^ { _.toInt }
    def term: Parser[Int] = number

    def summedTerms: Parser[Int] = term ~ rep(("+" | "-") ~ term) ^^ {
      case base ~ list => list.foldLeft(base) {
        case (z, "+" ~ s) => z + s
        case (z, "-" ~ s) => z - s
      }
    }

    def expression: Parser[Int] = summedTerms

  }

  def errorOut(input: String, message: String, next: Reader[Char]): String = {
    f"Parsing failed due to [$message] on input [$input] at position [${next.pos.column}]"
  }

}