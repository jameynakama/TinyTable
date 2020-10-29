package com.jameyiguess.largetable

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.annotations.RouteParam

import scala.collection.SortedMap

case class KeyFromRouteRequest(@RouteParam key: String)

class HelloWorldController extends Controller {
  var theDatabase: SortedMap[String, String] = SortedMap.empty[String, String]

  get("/database") { _: Request =>
    info("get database")
    theDatabase
  }

  get("/database/:key") { request: KeyFromRouteRequest =>
    info("get database object")
    theDatabase.getOrElse(request.key, response.notFound)
  }

  post("/database") { obj: ObjectRequest =>
    info("post database object")
    theDatabase = theDatabase.updated(obj.key, obj.value)
    theDatabase.get(obj.key)
  }
}
