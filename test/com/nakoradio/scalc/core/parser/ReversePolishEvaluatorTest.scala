package com.nakoradio.scalc.core.parser

import org.junit.runner._
import org.specs2.runner.JUnitRunner

import org.specs2.mutable.{ Specification }

/**
 * Note: We are not mocking ShuntingParser for testing the ReversePolishEvaluator, this
 * is because these units are tightly related and testing at higher level makes testing
 * more complex statements less cubersome.
 * However, some of these tests should be transferred to the ShuntingParser, now there is a bit
 * too much overlapping between the tests of these two.
 */
@RunWith(classOf[JUnitRunner])
class ReversePolishEvaluatorTest extends Specification {

  val evaluator = new ReversePolishEvaluator()

  "Calculating numbers" should {
    "handle single number expressions" in {
      evaluator("4") must beEqualTo(EvaluatorSuccess(4))
      evaluator("-4.4") must beEqualTo(EvaluatorSuccess(-4.4))
    }
  }

  "For simple operations evaluator" should {

    "evaluate simple integer expressions to correct values" in {
      evaluator("5 + 1") must beEqualTo(EvaluatorSuccess(6))
      evaluator("5 - 1") must beEqualTo(EvaluatorSuccess(4))
      evaluator("3 * 2") must beEqualTo(EvaluatorSuccess(6))
      evaluator("7 / 2") must beEqualTo(EvaluatorSuccess(3.5))
      evaluator("-2 / -3").asInstanceOf[EvaluatorSuccess].result.doubleValue() must beCloseTo(0.66666, 0.0001)
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
      evaluator("3 + 2 * 3") must beEqualTo(EvaluatorSuccess(9))
      evaluator("3+2*3") must beEqualTo(EvaluatorSuccess(9))
    }

    "evaluate mixed multiply and sums" in {
      evaluator("55 + 12 * 231 * 1323 - 52 + 3211 * 22") must beEqualTo(EvaluatorSuccess(3738001))
      evaluator("1+(2*4/4+2(3-1*3)/2/3+3)*(3/-2-4+1*1*-4((2*2*(1-5))*2/4))") must beEqualTo(EvaluatorSuccess(133.5))
    }

  }

  // Parenthesis
  "For parenthesis evaluator" should {
    "evaluate parenthesis" in {
      evaluator("1 + ( 2 + 2 ) * 4") must beEqualTo(EvaluatorSuccess(17))
      evaluator("-1-1(-1-1-1)-1") must beEqualTo(EvaluatorSuccess(1))
    }

    "evaluate  nested parenthesis" in {
      evaluator("1 + ( ( 2 + 3 ) + 2 ) * 4") must beEqualTo(EvaluatorSuccess(29))
      evaluator("2 * (23/(33))- 23 * (23)").asInstanceOf[EvaluatorSuccess].result.doubleValue() must beCloseTo(-527.6060606, 0.0001)
      evaluator("2 * (23/(3*3))- 23 * ((2*(3)))").asInstanceOf[EvaluatorSuccess].result.doubleValue() must beCloseTo(-132.88888, 0.0001)
    }

    "evaluate full parenthesis" in {
      evaluator("( 1 + 2 * 3 )") must beEqualTo(EvaluatorSuccess(7))
    }

    "evaluate number as multiplier in conjunction with parentheses" in {
      evaluator("2 ( 1 + 2 )") must beEqualTo(EvaluatorSuccess(6))
      evaluator("(1 + 2)2") must beEqualTo(EvaluatorSuccess(6))
      evaluator("-1(-1-1-1)-1") must beEqualTo(EvaluatorSuccess(2))
    }

    "evaluate operations on expression on parentheses" in {
      evaluator("(1+2)*(3+4)") must beEqualTo(EvaluatorSuccess(21))
      evaluator("(1+2)-(3+4)") must beEqualTo(EvaluatorSuccess(-4))
      evaluator("2(1+2)(3+4)") must beEqualTo(EvaluatorSuccess(42))
      evaluator("(1+2)+(2+4)") must beEqualTo(EvaluatorSuccess(9))
      evaluator("(5+5)/(1+1)") must beEqualTo(EvaluatorSuccess(5))
      //evaluator("(-(1+2)+1)") must beEqualTo(EvaluatorSuccess(-2))
    }

  }

  // Special cases
  "For special cases evaluator" should {
    "evaluate regardless of white space" in {
      evaluator("(3+2*3)") must beEqualTo(EvaluatorSuccess(9))
      evaluator(" ( 1                 +2\n*\t3   \t)") must beEqualTo(EvaluatorSuccess(7))
    }

    "evaluate adding of negative numbers" in {
      evaluator("2 + -4") must beEqualTo(EvaluatorSuccess(-2))
      evaluator("3+-4") must beEqualTo(EvaluatorSuccess(-1))
    }
    "evaluate subtraction of negative numbers" in {
      evaluator("2 - -4") must beEqualTo(EvaluatorSuccess(6))
      evaluator("3--4") must beEqualTo(EvaluatorSuccess(7))
    }

    "Avoid double precision problem" in {
      evaluator("0.585 * 3.2") must beEqualTo(EvaluatorSuccess(1.872))
    }

  }

  // Error handling
  "For errors evaluator" should {
    "catch and wrap errors from parser" in {
      evaluator("2+a") must beEqualTo(EvaluatorFailure("Parsing failed due to [unrecoqnized token 'a'] on input [2+a]"))

    }

    "return error status when evaluation fails" in {
      evaluator("4 + a") must haveClass[EvaluatorFailure]
      evaluator("4---1") must haveClass[EvaluatorFailure]
      evaluator("4+5 5") must haveClass[EvaluatorFailure]
    }

    "Divide by zero" in {
      evaluator("2 / 0") must beEqualTo(EvaluatorFailure("division by zero"))
    }
  }

}