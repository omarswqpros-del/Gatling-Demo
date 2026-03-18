package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.regex;
import static io.gatling.javaapi.core.CoreDsl.substring;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class StaticPages {

    public static ChainBuilder homepage =
    exec(
        http("Load Home Page")
        .get("/")
        // gatling automatically do this check no need to do it
        .check(status().is(200))
        .check(status().not(404), status().not(405))
        .check(substring("<title>Ace Toys Online Shop</title>"))
        .check(css("#_csrf", "content").saveAs("csrfToken"))
    );

    public static ChainBuilder ourStoryPage =
    exec(
        http("Load Our Story Page")
        .get("/our-story")
        .check(regex("Our fictional toy store was founded online in \\d{4}"))
    );

    public static ChainBuilder getInTouchPage =
    exec(
        http("Load Get In Touch Page")
        .get("/get-in-touch")
        .check(substring("as we are not actually a real store!"))
    );
}