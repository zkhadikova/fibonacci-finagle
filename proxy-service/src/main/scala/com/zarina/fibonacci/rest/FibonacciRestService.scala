package com.zarina.fibonacci.rest

import com.twitter.concurrent.AsyncStream
import com.twitter.finagle.Http
import com.twitter.util.{Await, Future}
import com.twitter.io.Buf
import io.circe._
import io.finch._
import io.finch.syntax._
import shapeless._
import io.finch.circe._

object FibonacciRestService extends App with FibonacciThiftConnector {
  def encodeErrorList(es: List[Exception]): Json = {
    val messages = es.map(x => Json.fromString(x.getMessage))
    Json.obj("errors" -> Json.arr(messages: _*))
  }

  implicit val encodeException: Encoder[Exception] = Encoder.instance({
    case e: io.finch.Errors => encodeErrorList(e.errors.toList)
    case e: io.finch.Error =>
      e.getCause match {
        case e: io.circe.Errors => encodeErrorList(e.errors.toList)
        case err => Json.obj("message" -> Json.fromString(e.getMessage))
      }
    case e: Exception => Json.obj("message" -> Json.fromString(e.getMessage))
  })

  val fibonacci: Endpoint[AsyncStream[Buf]] = get("fibonacci" :: path[Long].shouldNot("be less than 1") {
    _ < 1
  }) { (num: Long) =>
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
