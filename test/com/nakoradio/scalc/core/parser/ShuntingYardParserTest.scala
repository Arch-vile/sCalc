package com.nakoradio.scalc.core.parser

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

import org.specs2.mutable.{ BeforeAfter, Specification }
import java.util.Base64
import javafx.scene.shape.ClosePath
import scala.collection.mutable.Stack
import com.sun.scenario.animation.NumberTangentInterpolator

@RunWith(classOf[JUnitRunner])
class ShuntingYardParserTest extends Specification {

  val parser = new ShuntingYardParser()

  "Parser" should {
    "parse numbers" in {
      parser("4") must beEqualTo(Stack(NumberTerm(4)))
      parser("-123.5") must beEqualTo(Stack(NumberTerm(-123.5)))
      parser("-.5") must beEqualTo(Stack(NumberTerm(-.5)))
      parser(".5") must beEqualTo(Stack(NumberTerm(.5)))
    }

    "parse simple operations" in {
      parser("4 + 3").reverse must beEqualTo(Stack(NumberTerm(4), NumberTerm(3), Add()))
      parser("4 - 3").reverse must beEqualTo(Stack(NumberTerm(4), NumberTerm(3), Subtract()))
      parser("4 * 3").reverse must beEqualTo(Stack(NumberTerm(4), NumberTerm(3), Multiply()))
      parser("4 / 3").reverse must beEqualTo(Stack(NumberTerm(4), NumberTerm(3), Divide()))
    }

    "parse special numbers in operations" in {
      parser("-.2 - .3 * +.4").reverse must beEqualTo(Stack(NumberTerm(-.2), NumberTerm(.3), NumberTerm(.4), Multiply(), Subtract()))
    }

    "parse double operation symbols" in {
      parser("4 - -3").reverse must beEqualTo(Stack(NumberTerm(4), NumberTerm(-3), Subtract()))
      parser("5 + -3").reverse must beEqualTo(Stack(NumberTerm(5), NumberTerm(-3), Add()))
      parser("6 - +3").reverse must beEqualTo(Stack(NumberTerm(6), NumberTerm(3), Subtract()))
      parser("7 * -3").reverse must beEqualTo(Stack(NumberTerm(7), NumberTerm(-3), Multiply()))
    }

    "parse repetive numbers" in {
      parser("-1 - 1 - 1").reverse must beEqualTo(Stack(NumberTerm(-1), NumberTerm(1), Subtract(), NumberTerm(1), Subtract()))
      parser("100 / 10 / 2").reverse must beEqualTo(Stack(NumberTerm(100), NumberTerm(10), Divide(), NumberTerm(2), Divide()))
    }

    "consider operator precedence" in {
      parser("1 - 2 * 3").reverse must beEqualTo(Stack(NumberTerm(1), NumberTerm(2), NumberTerm(3), Multiply(), Subtract()))
      parser("1 + 5 / 3").reverse must beEqualTo(Stack(NumberTerm(1), NumberTerm(5), NumberTerm(3), Divide(), Add()))
    }

    "parse parenthesis" in {
      parser("2 * ( 3 + 4)").reverse must beEqualTo(Stack(NumberTerm(2), NumberTerm(3), NumberTerm(4), Add(), Multiply()))
      parser("2 * ( 3 / (4+2) )").reverse must beEqualTo(Stack(NumberTerm(2), NumberTerm(3), NumberTerm(4), NumberTerm(2), Add(), Divide(), Multiply()))
      parser("((2) + 2) * 4").reverse must beEqualTo(Stack(NumberTerm(2), NumberTerm(2), Add(), NumberTerm(4), Multiply()))
    }

    "parse numbers paired to parenthesis" in {
      parser("2(3+4)").reverse must beEqualTo(Stack(NumberTerm(2), NumberTerm(3), NumberTerm(4), Add(), Multiply()))
    }

    "parse special cases" in {
      parser("-4 + 3").reverse must beEqualTo(Stack(NumberTerm(-4), NumberTerm(3), Add()))
    }

  }

}