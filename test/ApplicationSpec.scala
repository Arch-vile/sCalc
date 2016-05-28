import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "EvaluatorController" should {

    // NCsyLjEgLTQqMg== -> 4+2.1 -4*2 -> -1.9
    "return 200 for success evaluation and result" in {
      val eval = route(app, FakeRequest(GET, "/calculus?query=NCsyLjEgLTQqMg==")).get
      status(eval) mustBe OK
      contentType(eval) mustBe Some("application/json")
      contentAsString(eval) mustBe "{\"error\":false,\"result\":-1.9}"
    }

    // bm9uc2Vuc2U= -> nonsense
    "return failed evaluation when cannot calculate given input" in {
      val eval = route(app, FakeRequest(GET, "/calculus?query=bm9uc2Vuc2U=")).get
      status(eval) mustBe 400
      contentType(eval) mustBe Some("application/json")
      contentAsString(eval) must startWith("{\"error\":true,\"message\":\"Parsing failed due to ")
    }

    "return bad request when missing query argument" in {
      route(app, FakeRequest(GET, "/calculus")).map(status(_)) mustBe Some(400)
    }

    "return bad request with error when base64 decode fails" in {
      val eval = route(app, FakeRequest(GET, "/calculus?query=a=")).get
      status(eval) mustBe 400
      contentType(eval) mustBe Some("application/json")
      contentAsString(eval) must startWith("{\"error\":true,\"message\":\"Format error in Base64 input")
    }

  }

}
