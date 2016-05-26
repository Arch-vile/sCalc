package services

import scala.util.parsing.combinator.RegexParsers
import javax.inject._

@Singleton
class PatternExpEvaluator extends ExpressionEvaluator {

  def apply(input: String): EvaluatorResult = {
    val parser = new PatternParser()
    parser.parseAll(parser.expression, input) match {
      case parser.Success(value, _) => EvaluatorResult(false, value)
    }
  }

  class PatternParser extends RegexParsers {

    def number: Parser[Int] = """[0-9]+""".r ^^ { _.toInt }

    def expression: Parser[Int] = number

  }

}