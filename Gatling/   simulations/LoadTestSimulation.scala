package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTestSimulation extends Simulation {

  // -------------------------------------------------------
  // HTTP Protocol
  // -------------------------------------------------------
  val httpProtocol = http
    .baseUrl("http://www.testingyes.com")
    .userAgentHeader("Mozilla/5.0")
    .acceptHeader("text/html")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .connectionHeader("keep-alive")

  // -------------------------------------------------------
  // Rutas a llamar (mismas que en k6 / JMeter / Pulse)
  // -------------------------------------------------------
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

  // -------------------------------------------------------
  // Escenario: 10 iteraciones por usuario sobre todos los paths
  // -------------------------------------------------------
  val scn = scenario("Load Test Simulation")
    .repeat(10) {
      foreach(paths, "path") {
        exec(
          http("GET ${path}")
            .get("${path}")
            .check(status.in(200, 301, 302))
        )
      }
    }

  // -------------------------------------------------------
  // Inyección:
  //  - esperar 10s (ramp-up alignment con JMeter/k6/Pulse)
  //  - lanzar 2 usuarios a la vez
  // -------------------------------------------------------
  setUp(
    scn.inject(
      nothingFor(10.seconds),
      atOnceUsers(2)
    )
  ).protocols(httpProtocol)
}

