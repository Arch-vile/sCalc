package controllers

import play.api.libs.json._
import javax.inject._
import play.api._
import play.api.mvc._

import services.Counter
import services.ExpressionEvaluator
import services.EvaluatorResult

@Singleton
class EvaluatorController @Inject() (evaluator: ExpressionEvaluator) extends Controller {

  implicit val resultImplicitWrites = Json.writes[EvaluatorResult]

  def evaluate(expression: String) = Action {
    Ok(Json.toJson(evaluator(expression)))
  }

}
