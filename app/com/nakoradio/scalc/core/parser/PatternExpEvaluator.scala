package com.nakoradio.scalc.core.parser

import scala.util.parsing.combinator.RegexParsers

class PatternExpEvaluator extends RegexParsers with ExpressionEvaluator {

  def eval(input: String): EvaluatorResult = {
    EvaluatorResult(true, 42)
  }

}