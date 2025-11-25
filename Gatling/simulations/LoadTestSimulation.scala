package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LoadTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://www.testingyes.com")
    .inferHtmlResources()
    .userAgentHeader("Mozilla/5.0 (Gatling Benchmark)")
    .acceptHeader("text/html")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .connectionHeader("keep-alive")

  // ------------------------------------------
  // Paths igual que tu K6/JMeter/Pulse test
  // ------------------------------------------
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

  // ------------------------------------------
  // Scenario = Gatling equivalente a JMeter/K6
  // ------------------------------------------
  val scn = scenario("Load Test Simulation")
    .repeat(10) { // 10 iterations por usuario → igual que K6
      foreach(paths, "path") {
        exec(
          http("GET ${path}")
            .get("${path}")
            .check(status.is(200))
        )
      }
    }

  // ------------------------------------------
  // Injection model para equivalencia real
  // ------------------------------------------
  setUp(
    scn.inject(
      nothingFor(10),   // Delay inicial igual que K6 startTime=10
      atOnceUsers(2)    // 2 usuarios virtuales igual que K6/JMeter/Pulse
    )
  ).protocols(httpProtocol)
}
