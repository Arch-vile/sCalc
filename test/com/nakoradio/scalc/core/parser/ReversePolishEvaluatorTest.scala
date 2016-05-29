package com.nakoradio.scalc.core.parser

import org.junit.runner._
import org.specs2.runner.JUnitRunner

import org.specs2.mutable.{ Specification }

@RunWith(classOf[JUnitRunner])
class ReversePolishEvaluatorTest extends Specification {

  val parser = new ReversePolishEvaluator()

  "Parser" should {
    "parse numbers" in {
      parser("4") must beEqualTo(EvaluatorSuccess(4))
      parser("-123.5") must beEqualTo(EvaluatorSuccess(-123.5))
    }

    "parse simple expression" in {
      parser("4 + 3") must beEqualTo(EvaluatorSuccess(7))
      parser("4 - 3") must beEqualTo(EvaluatorSuccess(1))
      parser("4 * 3") must beEqualTo(EvaluatorSuccess(12))
      parser("5 / 2") must beEqualTo(EvaluatorSuccess(2.5))

    }
  }

}