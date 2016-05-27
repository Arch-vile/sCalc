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

    "evaluate simple sum" in {
      evaluator("5 + 1") must beEqualTo(EvaluatorSuccess(6))
    }

    "evaluate sum for odd count of numbers" in {
      evaluator("5 + 1 + 5") must beEqualTo(EvaluatorSuccess(11))
    }

    "evaluate sum for even count of numbers" in {
      evaluator("5 + 1 + 5 + 1") must beEqualTo(EvaluatorSuccess(12))
    }

    "evaluate simple subtract" in {
      evaluator("5 - 1") must beEqualTo(EvaluatorSuccess(4))
    }

    "evaluate multiple subtract" in {
      evaluator("5 - 5 - 1") must beEqualTo(EvaluatorSuccess(-1))
    }

    "evaluate sum and subtracts" in {
      evaluator("55 + 12 - 231 - 1323 + 52 + 32131 - 22") must beEqualTo(EvaluatorSuccess(30674))
    }

    "return error status when evaluation fails" in {
      evaluator("4 + a") must haveClass[EvaluatorFailure]
    }

  }

}