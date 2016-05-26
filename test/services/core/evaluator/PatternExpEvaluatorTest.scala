package services.core.evaluator

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

import org.specs2.mutable.{ BeforeAfter, Specification }
import services.EvaluatorResult
import services.PatternExpEvaluator
import services.EvaluatorSuccess
import services.EvaluatorFailure

@RunWith(classOf[JUnitRunner])
class PatternExpEvaluatorTest extends Specification {

  val evaluator = new PatternExpEvaluator()

  "Evaluator" should {

    "evaluate single number as is" in {
      evaluator("4") must beEqualTo(EvaluatorSuccess(4))
    }

    "return error status when evaluation fails" in {
      evaluator("4 + a") must beEqualTo(EvaluatorFailure("Parsing failed due to [end of input expected] on input [4 + a] at position [3]"))
    }

  }

}