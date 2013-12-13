package com.gu.streams

import scalaz.stream.Process
import Streams._
import org.specs2.{Specification, ScalaCheck}

class StreamsSpec extends Specification with ScalaCheck {
  def is = "maxSeen" ! prop { (xs: List[Int]) =>
    (Process.emitSeq(xs) |> maxSeen).toList == xs.foldLeft(List.empty[Int]) { (acc, x) =>
      (acc.headOption.getOrElse(x) max x) :: acc
    }.reverse
  }
}
