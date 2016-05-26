package controllers

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.functional.syntax._
import play.api.libs.json.Writes._
import play.api.libs.json.util._

import javax.inject._
import play.api._
import play.api.mvc._

import services.Counter
import services.ExpressionEvaluator
import services.EvaluatorResult
import services.EvaluatorSuccess
import services.EvaluatorFailure

@Singleton
class EvaluatorController @Inject() (evaluator: ExpressionEvaluator) extends Controller {

  implicit val resultWriter = new Writes[EvaluatorResult] {
    def writes(result: EvaluatorResult): JsValue = {
      result match {
        case EvaluatorSuccess(v) => Json.obj("error" -> false, "result" -> v)
        case EvaluatorFailure(m) => Json.obj("error" -> true, "message" -> m)
      }

    }
  }

  implicit val succeessWriter = new Writes[EvaluatorSuccess] {
    def writes(result: EvaluatorSuccess): JsValue = {
      Json.obj("error" -> false, "result" -> result.result)
    }
  }

  def evaluate(expression: String) = Action {

    evaluator(expression) match {
      case s: EvaluatorSuccess => Ok(Json.toJson(s))
      case f: EvaluatorFailure => BadRequest(Json.toJson(f))
    }
  }

}
