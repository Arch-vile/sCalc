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
    def sum: Parser[Int] = (number | sum) ~ "+" ~ number ^^ {
      case x ~ "+" ~ y => x + y
    }

    def sumOfSum: Parser[Int] = sum ~ rep("+" ~ (sum | number)) ^^ {
      case sum ~ list => list.foldLeft(sum) {
        case (z, "+" ~ s) => z + s
      }
    }

    def expression: Parser[Int] = sumOfSum | number

  }

  def errorOut(input: String, message: String, next: Reader[Char]): String = {
    f"Parsing failed due to [$message] on input [$input] at position [${next.pos.column}]"
  }

}