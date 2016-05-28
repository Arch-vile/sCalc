package com.nakoradio.scalc.core.parser

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

import org.specs2.mutable.{ BeforeAfter, Specification }
import java.util.Base64
import sun.misc.BASE64Decoder
import javafx.scene.shape.ClosePath
import scala.collection.mutable.Stack

@RunWith(classOf[JUnitRunner])
class ShuntingYardParserTest extends Specification {

  val parser = new ShuntingYardParser()

  "Parser" should {
    "parse number" in {
      parser("4") must beEqualTo(Stack(NumberTerm(4)))
      parser("-123.5") must beEqualTo(Stack(NumberTerm(-123.5)))
    }

    "parse simple operations" in {
      parser("4 + 3") must beEqualTo(Stack(Add(), NumberTerm(3), NumberTerm(4)))
      parser("4 - 3") must beEqualTo(Stack(Subtract(), NumberTerm(3), NumberTerm(4)))
      parser("4 * 3") must beEqualTo(Stack(Multiply(), NumberTerm(3), NumberTerm(4)))
      parser("4 / 3") must beEqualTo(Stack(Divide(), NumberTerm(3), NumberTerm(4)))
    }

    "parse complex operations" in {
      parser("4 + 3") must beEqualTo(Stack(Add(), NumberTerm(3), NumberTerm(4)))
    }

    "consider operator precedence" in {
      parser("1 - 2 * 3") must beEqualTo(Stack(Subtract(), Multiply(), NumberTerm(3), NumberTerm(2), NumberTerm(1)))
    }

    "parse parenthesis" in {
      parser("2 * ( 3 + 4 )") must beEqualTo(Stack(Multiply(), Add(), NumberTerm(4), NumberTerm(3), NumberTerm(2)))

    }

  }

}