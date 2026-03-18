package acetoys;

import acetoys.simulation.TestPopulation;
import static io.gatling.javaapi.core.CoreDsl.AllowList;
import static io.gatling.javaapi.core.CoreDsl.DenyList;
import static io.gatling.javaapi.core.CoreDsl.forAll;
import static io.gatling.javaapi.core.CoreDsl.global;
import io.gatling.javaapi.core.Simulation;
import static io.gatling.javaapi.http.HttpDsl.http;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class AceToysSimulation extends Simulation {

  // parameterize the url for command line and script
  private static final String BASEURL = System.getProperty("baseURL", "https://acetoys.uk");


  private HttpProtocolBuilder httpProtocol = http
  // base url is defined in the protocol to make it easier to change for different environments (e.g. staging, production)
    .baseUrl(BASEURL)
    // this is the list that we denied when using the recorder UI we prevented the static resources
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    // this line tells the server that i can accept responses that are compressed using gzip, deflate, or br (Brotli), which can help to reduce the size of the response and improve the performance of the application. This is a common practice in web applications to optimize the network communication between the client and the server.
    .acceptEncodingHeader("gzip, deflate, br")
    // now the accept language header is a way to tell the server which language we prefer to receive the response in, in this case we are saying that we prefer to receive the response in English, but we are also saying that we can accept any other language with a lower priority.
    .acceptLanguageHeader("en-US,en;q=0.9");

  private static final String TEST_TYPE = System.getProperty("TEST_TYPE", "INSTANT_USERS");
  {
	  if (TEST_TYPE.equals("INSTANT_USERS")) {
      setUp(TestPopulation.instantUsers).protocols(httpProtocol)
      .assertions(
        global().responseTime().mean().lt(3),
        global().successfulRequests().percent().gt(99.0),
        forAll().responseTime().max().lt(5)
      );
    } else if(TEST_TYPE.equals("RAMP_USERS")) {
      setUp(TestPopulation.rampUsers).protocols(httpProtocol);
    } else if (TEST_TYPE.equals("COMPLEX_INJECTION")) {
      setUp(TestPopulation.complextInjection).protocols(httpProtocol);
    } else if (TEST_TYPE.equals("CLOSED_MODEL")) {
      setUp(TestPopulation.closedModel).protocols(httpProtocol);
    }
    else {
      setUp(TestPopulation.instantUsers).protocols(httpProtocol)
      .assertions(
        global().responseTime().mean().lt(3),
        global().successfulRequests().percent().gt(99.0),
        forAll().responseTime().max().lt(5)
        );
    }
  }
}