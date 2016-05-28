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

  // Basic operations
  "For simple operations evaluator" should {
    "evaluate single number as is" in {
      evaluator("4") must beEqualTo(EvaluatorSuccess(4))
    }

    "evaluate simple sum" in {
      evaluator("5 + 1") must beEqualTo(EvaluatorSuccess(6))
    }

    "evaluate simple subtract" in {
      evaluator("5 - 1") must beEqualTo(EvaluatorSuccess(4))
    }

    "evaluate simple multiply" in {
      evaluator("3 * 2") must beEqualTo(EvaluatorSuccess(6))
    }
  }

  // Combinations
  "For combinations evaluator" should {

    "evaluate sum for odd count of numbers" in {
      evaluator("5 + 1 + 5") must beEqualTo(EvaluatorSuccess(11))
    }

    "evaluate sum for even count of numbers" in {
      evaluator("5 + 1 + 5 + 1") must beEqualTo(EvaluatorSuccess(12))
    }

    "evaluate multiple subtract" in {
      evaluator("5 - 5 - 1") must beEqualTo(EvaluatorSuccess(-1))
    }

    "evaluate sum and subtracts" in {
      evaluator("55 + 12 - 231 - 1323 + 52 + 32131 - 22") must beEqualTo(EvaluatorSuccess(30674))
    }

    "evaluate odd terms in multiply" in {
      evaluator("3 * 2 * 2") must beEqualTo(EvaluatorSuccess(12))
    }

    "evaluate term precedence " in {
      evaluator("3 + 2 * 2") must beEqualTo(EvaluatorSuccess(7))
    }

    "evaluate mixed multiply and sums" in {
      evaluator("55 + 12 * 231 * 1323 - 52 + 3211 * 22") must beEqualTo(EvaluatorSuccess(3738001))
    }

  }

  // Parenthesis
  "For parenthesis evaluator" should {
    "evaluate parenthesis" in {
      evaluator("1 + ( 2 + 2 ) * 4") must beEqualTo(EvaluatorSuccess(17))
    }

    "evaluate  nested parenthesis" in {
      evaluator("1 + ( ( 2 + 3 ) + 2 ) * 4") must beEqualTo(EvaluatorSuccess(29))
    }

    "evaluate full parenthesis" in {
      evaluator("( 1 + 2 * 3 )") must beEqualTo(EvaluatorSuccess(7))
    }
  }

  // Special cases
  "For special cases evaluator" should {
    "evaluate without whitespace" in {
      evaluator("(1+2*3)") must beEqualTo(EvaluatorSuccess(7))
    }
  }

  // Error handling
  "For errors evaluator" should {
    "return error status when evaluation fails" in {
      evaluator("4 + a") must haveClass[EvaluatorFailure]
    }
  }
}