package com.gu.streams

import com.gu.openplatform.contentapi.{DispatchAsyncApi => Api}
import com.gu.openplatform.contentapi.DispatchAsyncApi.ItemQuery
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz.stream.Process
import Tasks.fromScalaFuture
import scala.concurrent.duration._
import Items._
import Time._
import Streams._

object ContentApiStreams {
  def ofQuery(query: ItemQuery) = Process.eval(fromScalaFuture(query.response))

  /** Stream of blocks for the live blog with the given ID */
  def liveBlogBlocks(itemId: String) = ofQuery(Api.item.itemId(itemId)) flatMap { response =>
    Process.emitAll(response.bodyBlocks.sortBy(_.publishedDateTime))
  }

  val everyTenSeconds = Process.awakeEvery(10 seconds)

  /** Stream of blocks for the live blog with the given ID - this will continue to poll the live blog every ten seconds
    * so long as the consumer continues to request items
    */
  def liveBlogUpdatesStream(itemId: String) = {
    val updateStream = liveBlogBlocks(itemId).repeat /** TODO make a 'repeatAfter' function?? */
    val lastPublishedStream = updateStream.map(_.publishedDateTime) |> maxSeen
    updateStream filterWith updateStream.zipWith(Process(Time.MinValue) ++ lastPublishedStream) {
      _.publishedDateTime isAfter _
    }
  }
}
