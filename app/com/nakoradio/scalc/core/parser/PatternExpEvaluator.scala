package com.nakoradio.scalc.core.parser

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.Reader
import javax.inject._
import scala.BigDecimal

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
