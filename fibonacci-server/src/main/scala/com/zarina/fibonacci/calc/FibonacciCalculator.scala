package com.zarina.fibonacci.calc
import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}
import com.zarina.fibonacci.thrift.FibonacciService
object FibonacciCalculator extends App {
    val service = new FibonacciService[Future] {
      def getFibonacciAt(index: Int): Future[Long] = {
        val response = FibonacciSequenceCalculator.calculateAt(index)
        Future.value(response)
      }
    }

  val server = Thrift.server.serveIface(":8080", service)

  Await.ready(server)

}
