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
  }

}