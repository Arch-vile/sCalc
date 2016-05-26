package com.nakoradio.scalc.core.parser

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

import org.specs2.mutable.{ BeforeAfter, Specification }

@RunWith(classOf[JUnitRunner])
class PatternExpEvaluatorTest extends Specification {

  val evaluator = PatternExpEvaluator;

  "Evaluator" should {

    "evaluate single number as is" in {
      evaluator("4") must beEqualTo(EvaluatorResult(true, 4))
    }

  }

}