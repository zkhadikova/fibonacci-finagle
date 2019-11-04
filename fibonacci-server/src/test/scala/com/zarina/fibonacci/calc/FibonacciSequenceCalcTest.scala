package com.zarina.fibonacci.calc

import org.scalatest._

class FibonacciSequenceCalcTest extends FlatSpec {

  "Fibonacci" should "return zero on zero" in {
    val number = FibonacciSequenceCalculator.calculateAt(0)
    assert(number === 0)
  }

  "Fibonacci" should "calculate sequence correctly" in {
    val number = FibonacciSequenceCalculator.calculateAt(10)
    assert(number === 55)
  }

  "Fibonacci" should "throw on negative parameter" in {
    assertThrows[IllegalArgumentException] {
      FibonacciSequenceCalculator.calculateAt(-1)
    }
  }
}
