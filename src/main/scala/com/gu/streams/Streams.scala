package com.gu.streams

import scalaz.stream._

object Streams {
  def maxSeen[A: Ordering] = process1.scan1[A] { List(_, _).max }

  implicit class RichProcess[S[_], A](process: Process[S, A]) {
    /** Filters the stream with another boolean stream */
    def filterWith[T[_]](filterStream: Process[T, Boolean]) = process.zip(filterStream).filter(_._2).map(_._1)
  }
}
