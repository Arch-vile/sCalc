package com.nakoradio.scalc.core.parser

import scala.collection.mutable.Stack

class ReversePolishEvaluator extends ExpressionEvaluator {

  val parser = new ShuntingYardParser()

  def apply(input: String): EvaluatorResult = {

    val result = new Stack[BigDecimal]

    try {
      parser(input).reverse.foreach { token =>
        token match {
          case n: NumberTerm => result.push(n.value)
          case o: Operator   => result.push(o.eval(result.pop, result.pop))
        }
      }
      if (result.size != 1) {
        EvaluatorFailure("Malformed input")
      } else {
        EvaluatorSuccess(result.pop)
      }
    } catch {
      case ex: ShuntException => EvaluatorFailure(ex.message)
      case e: Exception       => EvaluatorFailure("Unexpected error")
    }

  }

}
