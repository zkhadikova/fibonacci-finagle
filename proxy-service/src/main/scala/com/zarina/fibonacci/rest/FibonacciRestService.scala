package com.zarina.fibonacci.rest

import com.twitter.concurrent.AsyncStream
import com.twitter.finagle.Thrift
import com.zarina.fibonacci.thrift.FibonacciService
import com.zarina.fibonacci.thrift.FibonacciService.GetFibonacciAt
import com.twitter.finagle.Http
import com.twitter.util.{Await, Future}
import com.twitter.io.Buf
import io.finch._
import io.finch.syntax._
import shapeless._
import io.finch.circe._

object FibonacciRestService extends App {
  val clientServicePerEndpoint: FibonacciService.ServicePerEndpoint =
    Thrift.client.servicePerEndpoint[FibonacciService.ServicePerEndpoint](
      "localhost:8080",
      "fibonacci-server"
    )

  def getNext(i: Int): Future[Long] =
    clientServicePerEndpoint.getFibonacciAt(GetFibonacciAt.Args(i))

  def mkStream(i: Int, limit: Long): AsyncStream[Long] = AsyncStream.fromFuture(getNext(i)).flatMap { n =>
    if(n > limit)
      AsyncStream.empty
    else
      n +:: mkStream(i + 1, limit)
  }

  val fibonacci: Endpoint[AsyncStream[Buf]] = get("fibonacci" :: path[Long]) { (num: Long) =>
   val sequence = mkStream(0, num).map(n => Buf.Utf8(n.toString + "\n"))
   Ok(sequence)
  } handle {
    case e: Exception => BadRequest(e)
  }

  val intro: Endpoint[String] = get("fibonacci") {
    Ok("Welcome to fibonacci calculator. Please use /fibonacci/<number>")
  }

  val routes: Endpoint[String :+: AsyncStream[Buf] :+: CNil] = intro :+: fibonacci
  Await.ready(Http.server.withStreaming(true).serve(":8081", routes.toService))
}
