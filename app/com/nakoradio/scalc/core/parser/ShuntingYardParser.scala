package com.nakoradio.scalc.core.parser

import scala.collection.mutable.Stack

class ShuntingYardParser {

  final val INTEGER_REGEXP = """([-+]?\d+)""".r
  final val DECIMAL_REGEXP = """([-+]?\d+\.\d+)""".r

  def apply(input: String): Stack[Term] = {

    val outputQueue = new Stack[Term]
    val operators = new Stack[Operator]
    println(prepare(input))
    prepare(input).split(" ").map {
      case INTEGER_REGEXP(v) => NumberTerm(BigDecimal(v.toString))
      case DECIMAL_REGEXP(v) => NumberTerm(BigDecimal(v.toString))
      case "+"               => Add()
      case "-"               => Subtract()
      case "*"               => Multiply()
      case "/"               => Divide()
      case "("               => OpenParenth()
      case ")"               => CloseParenth()
      case y: String         => throw new Exception("E: [" + y.toString + "]")

    }.foreach(token => token match {
      case term: NumberTerm => numeric(term, outputQueue)
      case p: OpenParenth   => openParenthesis(operators)
      case p: CloseParenth  => closeParenthesis(outputQueue, operators)
      case o: Operator      => handleOperator(o, outputQueue, operators)
      case nrt              => throw new Exception("X: [" + nrt + "]")
    })

    while (!operators.isEmpty) {
      outputQueue.push(operators.pop);
    }

    outputQueue
  }

  def numeric(value: NumberTerm, outputQueue: Stack[Term]) = {
    outputQueue.push(value)
  }

  def openParenthesis(operators: Stack[Operator]) = {
    operators.push(OpenParenth())
  }

  def closeParenthesis(outputQueue: Stack[Term], operators: Stack[Operator]) = {
    while (operators.top != OpenParenth()) {
      outputQueue.push(operators.pop)
    }
    operators.pop
  }

  def handleOperator(o1: Operator, outputQueue: Stack[Term], operators: Stack[Operator]) = {
    while (!operators.isEmpty && precedence(o1) <= precedence(operators.top)) {
      outputQueue.push(operators.pop)
    }
    operators.push(o1)
  }

  def precedence(op: Operator): Int = op match {
    case OpenParenth() | CloseParenth() => 0
    case Add() | Subtract()             => 1
    case Multiply() | Divide()          => 2
  }

  // Will prepare the input to be correctly space separated list of terms
  def prepare(input: String): String = {
    input.replaceAll("""\s""", "") // Remove all white space
      .replaceAll("([\\+\\-\\*\\/\\(\\)])", " $1 ") // Separate operators
      .replaceAll("""  ([\\+\\-]) """, " $1") // Reunite explicit +/- modifiers to numbers
      .replaceAll("^ - ", "-") // Remove accidental whitespace on operator at start of statement
      .replaceAll("""\s+""", " ") // Clen consequent white spaces
      .trim() // Any extra white space at beginning
  }

}

abstract class Term
case class NumberTerm(value: BigDecimal) extends Term

abstract class Operator() extends Term {
  def eval(value: BigDecimal*): BigDecimal
}
case class Add() extends Operator {
  def eval(value: BigDecimal*): BigDecimal = {
    value.reduceRight((s, n) => s + n)
  }
}
case class Subtract() extends Operator {
  def eval(value: BigDecimal*): BigDecimal = {
    value.reduceRight((s, n) => n - s)
  }
}
case class Multiply() extends Operator {
  def eval(value: BigDecimal*): BigDecimal = {
    value.reduceRight((s, n) => s * n)
  }
}
case class Divide() extends Operator {
  def eval(value: BigDecimal*): BigDecimal = {
    value.reduceRight((s, n) => n / s)
  }
}
case class OpenParenth() extends Operator {
  def eval(value: BigDecimal*): BigDecimal = {
    throw new Exception("Parenthesis dont do evaluation")
  }
}
case class CloseParenth() extends Operator {
  def eval(value: BigDecimal*): BigDecimal = {
    throw new Exception("Parenthesis dont do evaluation")
  }
}
