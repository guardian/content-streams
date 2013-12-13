package com.gu.streams

import org.joda.time.DateTime

object Time {
  implicit val dateTimeOrdering = Ordering.fromLessThan[DateTime](_ isBefore _)

  val MinValue = new DateTime(0)
}
