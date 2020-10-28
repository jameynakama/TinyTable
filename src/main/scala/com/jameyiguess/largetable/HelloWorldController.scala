package com.jameyiguess.largetable

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class HelloWorldController extends Controller {
  get("/hi") { request: Request =>
//    info("get hi")
    "Hello " + request.params.getOrElse("name", "unnamed")
  }

  post("/hi") { hiRequest: HiRequest =>
//    info("post hi")
    "Hello " + hiRequest.name + " with id " + hiRequest.id
  }
}
