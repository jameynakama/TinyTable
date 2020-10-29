package com.jameyiguess.tinytable

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}

import scala.collection.SortedMap

case class KeyRequest(@RouteParam key: String)
case class RangeRequest(
                         @QueryParam start: String,
                         @QueryParam end: String,
                       )

class HelloWorldController extends Controller {
  //  var theDatabase: SortedMap[String, String] = SortedMap.empty[String, String]
  var theDatabase: SortedMap[String, String] = SortedMap(
    "ant" -> "the ant value",
    "bear" -> "the bear value",
    "cat" -> "the cat value",
    "deer" -> "the deer value",
    "elephant" -> "the elephant value",
    "fish" -> "the fish value",
    "gnat" -> "the gnat value",
    "hawk" -> "the hawk value",
    "ibex" -> "the ibex value",
    "jellyfish" -> "the jellyfish value",
    "kiwi" -> "the kiwi value"
  )

  get("/database") { _: Request =>
    info("get database")
    theDatabase
  }

  get("/database/:key") { request: KeyRequest =>
    info("get database object")
    theDatabase.getOrElse(request.key, response.notFound)
  }

  get("/database/range") { request: RangeRequest =>
    info("get database")
    theDatabase.range(request.start, request.end)
  }

  post("/database") { obj: ObjectRequest =>
    info("post database object")
    theDatabase = theDatabase.updated(obj.key, obj.value)
    theDatabase.get(obj.key)
  }
}
