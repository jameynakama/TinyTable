package com.jameyiguess.tinytable

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.annotations.{QueryParam, RouteParam}

import scala.collection.SortedMap

case class Entry(key: String, value: String, action: String)

case class KeyRequestFromRoute(@RouteParam key: String)
case class RangeRequestFromQueryParam(
                         @QueryParam start: String,
                         @QueryParam end: String,
                       )

case class ObjectRequestForPost(key: String, value: String)

class TinyTableController extends Controller {
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

  get("/database/:key") { request: KeyRequestFromRoute =>
    info("get database object")
    theDatabase.getOrElse(request.key, response.notFound)
  }

  get("/database/range") { request: RangeRequestFromQueryParam =>
    info("get database range")
    theDatabase.range(request.start, request.end)
  }

  post("/database") { obj: ObjectRequestForPost =>
    info("post database object")
    val entry = Entry(obj.key, obj.value, "create")
    theDatabase = theDatabase.updated(obj.key, obj.value)
    WriteAheadLogger.writeToDisk(entry)
    Map[String, String] {obj.key -> obj.value}
  }
}
