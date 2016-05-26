package com.nakoradio.scalc.core.parser

trait ExpressionEvaluator {

  def eval(input: String): EvaluatorResult

}

case class EvaluatorResult(success: Boolean, value: BigDecimal)