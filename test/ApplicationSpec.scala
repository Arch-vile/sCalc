import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Your new application is ready.")
    }

  }

  "CountController" should {

    "return an increasing count" in {
      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "0"
      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "1"
      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "2"
    }

  }

  "EvaluatorController" should {

    "return successful evaluation with result" in {
      // NCsyLjEgLTQqMg== -> 4+2.1 -4*2 -> -1.9
      contentAsString(route(app, FakeRequest(GET, "/calculus?query=NCsyLjEgLTQqMg==")).get) mustBe "{\"error\":false,\"result\":-1.9}"
    }

    "return 200 for success evaluation" in {
      route(app, FakeRequest(GET, "/calculus?query=NCsyLjEgLTQqMg==")).map(status(_)) mustBe Some(200)
    }

    "return failed evaluation with message" in {
      contentAsString(route(app, FakeRequest(GET, "/calculus?query=bm9uc2Vuc2U=")).get) must
        startWith("{\"error\":true,\"message\":\"Parsing failed due to ")
    }

    "return bad request when missing query" in {
      route(app, FakeRequest(GET, "/calculus")).map(status(_)) mustBe Some(400)

    }

    "return bad request with error when base64 decode fails" in {
      contentAsString(route(app, FakeRequest(GET, "/calculus?query=a")).get) must
        startWith("{\"error\":true,\"message\":\"Format error in Base64 input")
      route(app, FakeRequest(GET, "/calculus?query=a")).map(status(_)) mustBe Some(400)
    }

    "return 400 for failed evaluation" in {
      route(app, FakeRequest(GET, "/calculus?query=nonsense")).map(status(_)) mustBe Some(400)
    }
  }

}
