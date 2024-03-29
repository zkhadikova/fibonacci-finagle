package com.zarina.fibonacci.calc

import scala.annotation.tailrec

object FibonacciSequenceCalculator {
  def calculateAt(index: Int): Long = {
    if(index < 0)
      throw new IllegalArgumentException("Index must be 0 or greater")
    @tailrec
    def calculateRec(index: Int, prev: Long, current: Long): Long = {
      if (index <= 0) current else calculateRec(index - 1, prev = prev + current, current = prev)
    }

    calculateRec(index, prev = 1, current = 0)
  }
}
