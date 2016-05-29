package com.nakoradio.scalc.core.parser

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.Reader
import javax.inject._
import scala.BigDecimal

class PatternExpEvaluator extends ExpressionEvaluator {

  def apply(input: String): EvaluatorResult = {

    try {
      val cleanInput = clean(input)
      val parser = new PatternParser()
      parser.parseAll(parser.expression, cleanInput) match {
        case parser.Success(value, _)        => EvaluatorSuccess(value)
        case parser.NoSuccess(message, next) => EvaluatorFailure(parser.nicerError(cleanInput, message, next))
      }
    } catch {
      case ex: ParserException => EvaluatorFailure(ex.message)
      case e: Exception        => EvaluatorFailure("Unexpected error")
    }
  }

  // TODO: These should be included in the grammar
  def clean(input: String): String = {
    input.replaceAll("\\s+", " ") // First strip consequent whitespace
      .replaceAll("""(\d)\s?\(""", "$1*(") //then add missing '*' between number and parenthesis
      .replaceAll("""\)\s?(\d)""", ")*$1") //then add missing '*' between parenthesis and number
      .replaceAll("""\)\s?\(""", ")*(") // then add missing '*' between parenthesis
  }

}
