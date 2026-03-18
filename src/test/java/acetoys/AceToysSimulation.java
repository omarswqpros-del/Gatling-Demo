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
    // removed it cause we don't need to attach this kind of accept header to all requests, the accept header is basically saying what kind of response we can accept from the server, and in this case we are saying that we can accept any kind of response, but we are also saying that we prefer to receive HTML, XHTML, XML, images, and signed exchanges, but we are also saying that we can accept any other kind of response with a lower priority. This is not necessary for all requests, especially for the API requests where we expect to receive JSON responses, so we can set the accept header only for the requests that need it.
    // .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    // this line tells the server that i can accept responses that are compressed using gzip, deflate, or br (Brotli), which can help to reduce the size of the response and improve the performance of the application. This is a common practice in web applications to optimize the network communication between the client and the server.
    .acceptEncodingHeader("gzip, deflate, br")
    // now the accept language header is a way to tell the server which language we prefer to receive the response in, in this case we are saying that we prefer to receive the response in English, but we are also saying that we can accept any other language with a lower priority.
    .acceptLanguageHeader("en-US,en;q=0.9");
    // not needed  cause the requests are not going from a browser they will be coming from a java script finally, we are setting the user agent header to tell the server who is making the request, in this case we are setting it to a generic user agent string that represents a typical web browser.
    // .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36");
  
    // commented these huge headers maps because they are not needed for all requests, and they are also not necessary for the performance testing, we can set the headers only for the requests that need them, and we can also set them in a more concise way using the http protocol builder, for example we can set the user agent header in the http protocol builder instead of setting it for each request, and we can also set the accept header only for the requests that need it, this way we can make our code cleaner and more maintainable.
  // private Map<CharSequence, String> headers_0 = Map.ofEntries(
  //   Map.entry("priority", "u=0, i"),
  //   Map.entry("sec-ch-ua", "Not:A-Brand\";v=\"99\", \"Google Chrome\";v=\"145\", \"Chromium\";v=\"145"),
  //   Map.entry("sec-ch-ua-mobile", "?0"),
  //   Map.entry("sec-ch-ua-platform", "Windows"),
  //   Map.entry("sec-fetch-dest", "document"),
  //   Map.entry("sec-fetch-mode", "navigate"),
  //   Map.entry("sec-fetch-site", "none"),
  //   Map.entry("sec-fetch-user", "?1"),
  //   Map.entry("upgrade-insecure-requests", "1")
  // );
  
  // private Map<CharSequence, String> headers_1 = Map.ofEntries(
  //   Map.entry("priority", "u=0, i"),
  //   Map.entry("sec-ch-ua", "Not:A-Brand\";v=\"99\", \"Google Chrome\";v=\"145\", \"Chromium\";v=\"145"),
  //   Map.entry("sec-ch-ua-mobile", "?0"),
  //   Map.entry("sec-ch-ua-platform", "Windows"),
  //   Map.entry("sec-fetch-dest", "document"),
  //   Map.entry("sec-fetch-mode", "navigate"),
  //   Map.entry("sec-fetch-site", "same-origin"),
  //   Map.entry("sec-fetch-user", "?1"),
  //   Map.entry("upgrade-insecure-requests", "1")
  // );
  
  // private Map<CharSequence, String> headers_7 = Map.ofEntries(
  //   Map.entry("accept", "*/*"),
  //   Map.entry("priority", "u=1, i"),
  //   Map.entry("sec-ch-ua", "Not:A-Brand\";v=\"99\", \"Google Chrome\";v=\"145\", \"Chromium\";v=\"145"),
  //   Map.entry("sec-ch-ua-mobile", "?0"),
  //   Map.entry("sec-ch-ua-platform", "Windows"),
  //   Map.entry("sec-fetch-dest", "empty"),
  //   Map.entry("sec-fetch-mode", "cors"),
  //   Map.entry("sec-fetch-site", "same-origin"),
  //   Map.entry("x-requested-with", "XMLHttpRequest")
  // );
  
  // private Map<CharSequence, String> headers_12 = Map.ofEntries(
  //   Map.entry("origin", "https://acetoys.uk"),
  //   Map.entry("priority", "u=0, i"),
  //   Map.entry("sec-ch-ua", "Not:A-Brand\";v=\"99\", \"Google Chrome\";v=\"145\", \"Chromium\";v=\"145"),
  //   Map.entry("sec-ch-ua-mobile", "?0"),
  //   Map.entry("sec-ch-ua-platform", "Windows"),
  //   Map.entry("sec-fetch-dest", "document"),
  //   Map.entry("sec-fetch-mode", "navigate"),
  //   Map.entry("sec-fetch-site", "same-origin"),
  //   Map.entry("sec-fetch-user", "?1"),
  //   Map.entry("upgrade-insecure-requests", "1")
  // );

// removed as we parametrized the scenarios even
  // private final ScenarioBuilder scn = scenario("AceToysSimulation")
  //   .exec(UserSession.initSession)
  //   .exec(UserJourney.browseStore);




  // .exec(UserSession.initSession)
  //   // .exec(
  //   //   http("Load Home Page")
  //   //     .get("/")
  //   //     // gatling automatically do this check no need to do it
  //   //     .check(status().is(200))
  //   //     .check(status().not(404), status().not(405))
  //   //     .check(substring("<title>Ace Toys Online Shop</title>"))
  //   //     .check(css("#_csrf", "content").saveAs("csrfToken"))
  //   // )
  //   .exec(StaticPages.homepage)
  //   .pause(2)

  //   // .exec(
  //   //   http("Load Our Story Page")
  //   //     .get("/our-story")
  //   //     .check(regex("Our fictional toy store was founded online in \\d{4}"))
  //   // )
  //   .exec(StaticPages.ourStoryPage)
  //   .pause(2)

  //   // .exec(
  //   //   http("Load Get In Touch Page")
  //   //     .get("/get-in-touch")
  //   // )
  //   .exec(StaticPages.getInTouchPage)
  //   .pause(2)

  //   // .exec(
  //   //   http("Load Products List Page - Category All Products")
  //   //     .get("/category/all")
  //   // )
  //   .exec(Category.productListByCategory)
  //   .pause(2)

  //   // removed cause we got a parametrized method to load the products list page by category, so we can use that method to load the products list page for any category, and we can also use it to load the products list page for the "All Products" category, this way we can make our code cleaner and more maintainable.
  //   // .exec(
  //   //   http("Load Next Page of Products List - Category All Products - Page 1")
  //   //     .get("/category/all?page=1")
  //   // )
    
  //   // removed cause we did login with a session api that will preserve variables to track the dynamic data like page number so we can cycle through them
  //   // .exec(Category.nextPageOfAllProducts_1)
  //   // .pause(2)
  //   .exec(Category.cyclePageOfProducts)

  //   // removed cause we did modularize the test
  //   // .exec(
  //   //   http("Load Next Page of Products List - Category All Products - Page 2")
  //   //     .get("/category/all?page=2")
  //   // )
    
  //   // removed cause we did login with a session api that will preserve variables to track the dynamic data like page number so we can cycle through them
  //   // .exec(Category.nextPageOfAllProducts_2)
  //   .exec(Category.cyclePageOfProducts)
  //   .pause(2)
    
  //   // .exec(
  //   //   http("Load Product Details Page - Product: Darts Board")
  //   //     .get("/product/darts-board")
  //   // )
  //   .exec(Product.loadProductDetailsPage)
  //   .pause(2)
    
  //   // .exec(
  //   //   http("Add Product to Cart - ProductID: Darts Board")
  //   //     .get("/cart/add/19")
  //   // )
  //   .exec(Product.addProductToCart)
  //   .pause(2)
    
  //   // .exec(
  //   //   http("Load Products List Page - Category Babies Toys")
  //   //     .get("/category/babies-toys")
  //   // )
  //   .exec(Category.productListByCategory)
  //   .pause(2)
    
  //   // .exec(
  //   //   http("Add Product to Cart - ProductID: 11")
  //   //     .get("/cart/add/11")
  //   // )
  //   .exec(Product.addProductToCart)
  //   .pause(2)
      
  //   // .exec(
  //   //   http("Add Product to Cart - ProductID: 11")
  //   //     .get("/cart/add/11")
  //   // )
  //   .exec(Product.addProductToCart)
  //   .pause(2)
    
  //   // .exec(
  //   //   http("View Cart Page")
  //   //     .get("/cart/view")
  //   // )
  //   .exec(Cart.viewCart)
  //   .pause(2)

  //   // .exec(
  //   //   http("Login User")
  //   //     .post("/login")
  //   //     .formParam("_csrf", "#{csrfToken}")
  //   //     .formParam("username", "user1")
  //   //     .formParam("password", "pass")
  //   //     .check(css("#_csrf", "content").saveAs("csrfTokenLoggedIn"))
  //   // )

  //   // removed cause we log in the user in the view cart page if they are not already logged in, and we also set the customerLoggedIn session variable to true after logging in, so we can use that variable to check if the user is logged in or not, and we can also use it to set the headers for the requests that need authentication, this way we can make our code cleaner and more maintainable.
  //   // .exec(Customer.login)
    
  //   .exec(
  //     session -> {
  //       System.out.println(session);
  //       System.out.println("csrfTokenLoggedIn value is: " + session.getString("csrfTokenLoggedIn"));
  //       return session;
  //     }
  //   )
  //   .pause(2)

  //   // .exec(
  //   //   http("Increase Quantity of Product in Cart - ProductID: 19")
  //   //     .get("/cart/add/19?cartPage=true")
  //   // )
  //   .exec(Cart.increaseQuantityInCart)
  //   .pause(2)
    
  //   // .exec(
  //   //   http("Increase Quantity of Product in Cart - ProductID: 19")
  //   //     .get("/cart/add/19?cartPage=true")
  //   // )
  //   .exec(Cart.increaseQuantityInCart)
  //   .pause(2)
    
  //   // .exec(
  //   //   http("Subtract Quantity of Product in Cart - ProductID: 19")
  //   //     .get("/cart/subtract/19")
  //   // )
  //   .exec(Cart.decreaseQuantityInCart)
  //   .pause(2)
      
  //   // .
  //   .exec(Cart.checkout)
  //   .pause(2)

  //   // .exec(
  //   //   http("Logout User")
  //   //     .post("/logout")
  //   //     .formParam("_csrf", "#{csrfTokenLoggedIn}")
  //   // )
  //   .exec(Customer.logout)
  //   .pause(2);

  private static final String TEST_TYPE = System.getProperty("TEST_TYPE", "INSTANT_USERS");

  {
	  // setUp(TestScenario.defaultLoadTest.injectOpen(atOnceUsers(10))).protocols(httpProtocol);
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