package services

import javax.inject.Singleton
import com.nakoradio.scalc.core.parser.EvaluatorResult
import com.nakoradio.scalc.core.parser.PatternExpEvaluator
import com.nakoradio.scalc.core.parser.ReversePolishEvaluator
import com.nakoradio.scalc.core.parser.EvaluatorFailure

@Singleton
class ArithmeticEvaluator {

  val grammarEval = new PatternExpEvaluator()
  val polishEval = new ReversePolishEvaluator()

  def eval(input: String, evaluatorType: String): EvaluatorResult = {
    evaluatorType match {
      case "polish"  => polishEval(input)
      case "grammar" => grammarEval(input)
      case _         => EvaluatorFailure("Unrecoqnized evaluator type")
    }
  }

}