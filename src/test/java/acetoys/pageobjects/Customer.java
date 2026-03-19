package acetoys.pageobjects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.feed;
import static io.gatling.javaapi.http.HttpDsl.http;
import scala.util.Random;


public class Customer {

    private static final Iterator<Map<String, Object>> loginFeeder =
    Stream.generate((Supplier<Map<String,Object>>) () -> {
        Random rand = new Random();
        int userId = rand.nextInt(3 - 1 + 1) + 1;
        HashMap<String, Object> hmap = new HashMap<>();
        hmap.put("userId", "user" + userId);
        hmap.put("password", "pass");
        return hmap;
    }).iterator();

    public static final ChainBuilder login =
    feed(loginFeeder)
    .exec(
        http("Login User")
        .post("/login")
        .formParam("_csrf", "#{csrfToken}")
        .formParam("username", "#{userId}")
        .formParam("password", "#{password}")
        .check(css("#_csrf", "content").saveAs("csrfTokenLoggedIn"))
    )

    .exec(session -> session.set("customerLoggedIn", true))
    .exec(session -> {
        System.out.println(session.getString("userId"));
        return session;
    });

    // we added the randomSwitch to simulate some users logging out and some not, to add a bit of variety to the simulation
    public static final ChainBuilder logout =
        exec(
            http("Logout User")
            .post("/logout")
            .formParam("_csrf", "#{csrfTokenLoggedIn}")
            .check(css("a#NavbarHeaderLink.nav-link[href='/login']").exists())
            );
}