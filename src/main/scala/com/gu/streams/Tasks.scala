package com.gu.streams

import scala.concurrent.{ExecutionContext, Future}
import scalaz.concurrent.Task
import scalaz.Scalaz._
import scala.util.{Failure, Success}

object Tasks {
  def fromScalaFuture[A](fa: => Future[A])(implicit context: ExecutionContext): Task[A] = Task.async {
    register => fa onComplete {
      case Success(a) => register(a.right)
      case Failure(error) => register(error.left)
    }
  }
}
