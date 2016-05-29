package com.nakoradio.scalc.core.parser

import scala.collection.mutable.Stack

class ReversePolishEvaluator extends ExpressionEvaluator {

  val parser = new ShuntingYardParser()

  def apply(input: String): EvaluatorResult = {

    val result = new Stack[BigDecimal]

    println(parser(input))

    parser(input).reverse.foreach { token =>
      token match {
        case n: NumberTerm => result.push(n.value)
        case o: Operator   => result.push(o.eval(result.pop, result.pop))
      }
    }

    EvaluatorSuccess(result.pop)

  }

}

object HelloWorld {
  def main(args: Array[String]): Unit = {
    val e = new ReversePolishEvaluator()
    println(e.apply("-1-(-1 - 1 - 1)-1"))
  }
}