package services.core.evaluator

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

import org.specs2.mutable.{ BeforeAfter, Specification }
import services.EvaluatorResult
import services.PatternExpEvaluator

@RunWith(classOf[JUnitRunner])
class PatternExpEvaluatorTest extends Specification {

  val evaluator = new PatternExpEvaluator()

  "Evaluator" should {

    "evaluate single number as is" in {
      evaluator("4") must beEqualTo(EvaluatorResult(true, 4))
    }

  }

}