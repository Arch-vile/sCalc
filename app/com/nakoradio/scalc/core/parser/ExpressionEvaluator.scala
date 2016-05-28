package com.nakoradio.scalc.core.parser

trait ExpressionEvaluator {

  def apply(input: String): EvaluatorResult

}

abstract class EvaluatorResult
case class EvaluatorSuccess(result: BigDecimal) extends EvaluatorResult
case class EvaluatorFailure(message: String) extends EvaluatorResult
