package com.zarina.fibonacci.rest

import com.twitter.concurrent.AsyncStream
import com.twitter.finagle.Thrift
import com.twitter.util.Future
import com.zarina.fibonacci.rest.FibonacciRestService.clientServicePerEndpoint
import com.zarina.fibonacci.thrift.FibonacciService
import com.zarina.fibonacci.thrift.FibonacciService.GetFibonacciAt

trait FibonacciThiftConnector {
  val clientServicePerEndpoint: FibonacciService.ServicePerEndpoint =
    Thrift.client.servicePerEndpoint[FibonacciService.ServicePerEndpoint](
      "localhost:8080",
      "fibonacci-server"
    )

  def getNext(i: Int): Future[Long] = clientServicePerEndpoint.getFibonacciAt(GetFibonacciAt.Args(i))

  def mkStream(i: Int, limit: Long): AsyncStream[Long] = AsyncStream.fromFuture(getNext(i)).flatMap { n =>
    if (n > limit)
      AsyncStream.empty
    else
      n +:: mkStream(i + 1, limit)
  }


}
