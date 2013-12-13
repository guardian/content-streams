package com.gu.streams

import com.gu.openplatform.contentapi.model.ItemResponse
import com.gu.util.liveblogs.Parser

object Items {
  implicit class RichItemResponse(itemResponse: ItemResponse) {
    /** Note: Unsafe unless called on a response for a single item query. There is currently no way to tell this based
      * on how the types are codified in the API.
      */
    def body = itemResponse.content.get.safeFields.get("body").get

    def bodyBlocks = Parser.parse(body)
  }
}
