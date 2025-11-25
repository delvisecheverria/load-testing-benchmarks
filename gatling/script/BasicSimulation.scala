import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTestSimulation extends Simulation {

  val baseUrl = "http://www.testingyes.com"

  val httpProtocol = http
    .baseUrl(baseUrl)
    .userAgentHeader("Mozilla/5.0")
    .acceptHeader("text/html")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .header("Connection", "keep-alive")

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

  // 🔥 10 iteraciones por usuario (igual que k6 y Pulse)
  val scn = scenario("Load Test Benchmark")
    .repeat(10) {
      foreach(paths, "path") {
        exec(
          http("GET ${path}")
            .get("${path}")
        ).pause(1)
      }
    }

  // 🔥 ramp-up de 10s hasta 2 usuarios
  setUp(
    scn.inject(
      rampUsers(2) during (10.seconds)
    )
  ).protocols(httpProtocol)
}

