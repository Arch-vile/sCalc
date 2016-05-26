package services

trait ExpressionEvaluator {

  def apply(input: String): EvaluatorResult

}

case class EvaluatorResult(error: Boolean, result: BigDecimal)