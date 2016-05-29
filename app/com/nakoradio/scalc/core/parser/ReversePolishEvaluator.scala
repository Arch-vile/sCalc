package com.nakoradio.scalc.core.parser

import scala.collection.mutable.Stack

class ReversePolishEvaluator extends ExpressionEvaluator {

  val parser = new ShuntingYardParser()

  def apply(input: String): EvaluatorResult = {

    val result = new Stack[BigDecimal]

    parser(input).foreach { token =>
      token match {
        case n: NumberTerm => result.push(n.value)
      }
    }

    return EvaluatorSuccess(result.pop)

  }

}