package services

import javax.inject.Singleton
import com.nakoradio.scalc.core.parser.EvaluatorResult
import com.nakoradio.scalc.core.parser.PatternExpEvaluator

@Singleton
class ArithmeticEvaluator {

  val patternEval = new PatternExpEvaluator()

  def apply(input: String): EvaluatorResult = {
    patternEval(input)
  }

}