package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LoadTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://www.testingyes.com")
    .userAgentHeader("Mozilla/5.0")

  val headers = Map(
    "Accept" -> "text/html",
    "Accept-Language" -> "en-US,en;q=0.5",
    "Connection" -> "keep-alive"
  )

  val paths = List(
    "/onlineshop/",
    "/onlineshop/prices-drop",
    "/onlineshop/new-products",
    "/onlineshop/best-sales",
    "/onlineshop/content/1-delivery",
    "/onlineshop/content/2-legal-notice",
    "/onlineshop/content/3-terms-and-conditions-of-use",
    "/onlineshop/content/4-about-us",
    "/onlineshop/content/5-secure-payment",
    "/onlineshop/contact-us",
    "/onlineshop/sitemap",
    "/onlineshop/stores",
    "/onlineshop/identity",
    "/onlineshop/login?back=identity",
    "/onlineshop/order-history",
    "/onlineshop/login?back=history",
    "/onlineshop/credit-slip",
    "/onlineshop/login?back=order-slip",
    "/onlineshop/addresses",
    "/onlineshop/login?back=addresses"
  )

  val scn = scenario("Load Test Simulation")
    .repeat(10) { // 10 iteraciones por usuario = 20 totales (2 users)
      foreach(paths, "path") {
        exec(
          http("GET ${path}")
            .get("${path}")
            .headers(headers)
        ).pause(1)
      }
    }

  setUp(
    scn.inject(
      rampUsers(2) during (10) // 10s ramp-up
    )
  ).protocols(httpProtocol)
}
