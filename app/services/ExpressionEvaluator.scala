package services

import play.api.libs.json.JsString
import play.api.libs.json.Format
import play.api.libs.json.JsValue
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsSuccess

trait ExpressionEvaluator {

  def apply(input: String): EvaluatorResult

}

abstract class EvaluatorResult
case class EvaluatorSuccess(result: BigDecimal) extends EvaluatorResult
case class EvaluatorFailure(message: String) extends EvaluatorResult
