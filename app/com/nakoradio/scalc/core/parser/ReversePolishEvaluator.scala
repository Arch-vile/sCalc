package com.nakoradio.scalc.core.parser

import scala.collection.mutable.Stack

class ReversePolishEvaluator extends ExpressionEvaluator {

  val parser = new ShuntingYardParser()

  def apply(input: String): EvaluatorResult = {

    val result = new Stack[BigDecimal]

    parser(input).reverse.foreach { token =>
      token match {
        case n: NumberTerm => result.push(n.value)
        case Add()         => result.push(result.pop + result.pop)
        case Subtract()    => result.push(-1 * (result.pop - result.pop))
        case Multiply()    => result.push(result.pop * result.pop)
        case Divide()      => result.push(1 / result.pop * result.pop)
      }
    }

    return EvaluatorSuccess(result.pop)

  }

}