package acetoys.pageobjects;

import static acetoys.session.UserSession.decreaseItemsInBasketForSession;
import static acetoys.session.UserSession.decreaseSessionBasketTotal;
import static acetoys.session.UserSession.increaseItemsInBasketForSession;
import static acetoys.session.UserSession.increaseSessionBasketTotal;
import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.feed;
import static io.gatling.javaapi.core.CoreDsl.jsonFile;
import static io.gatling.javaapi.core.CoreDsl.substring;
import io.gatling.javaapi.core.FeederBuilder;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Cart {

    private static final FeederBuilder<Object> productFeeder = 
    jsonFile("data/productDetails.json").random();

    public static final ChainBuilder viewCart =
    feed(productFeeder)
    .doIf(session -> !session.getBoolean("customerLoggedIn"))
    .then(exec(Customer.login))
    .exec(
        http("View Cart Page")
        .get("/cart/view")
        .check(css("#CategoryHeader").is("Cart Overview"))
    );

    public static final ChainBuilder increaseQuantityInCart =
    exec(increaseItemsInBasketForSession)
    .exec(increaseSessionBasketTotal)
    .exec(
        http("Increase Quantity of Product in Cart - Product Name: #{name}")
        .get("/cart/add/#{id}?cartPage=true")
        .check(css("#grandTotal").isEL("$#{basketTotal}"))

    );

    public static final ChainBuilder decreaseQuantityInCart =
    exec(decreaseItemsInBasketForSession)
    .exec(decreaseSessionBasketTotal)
    .exec(
        http("Subtract Quantity of Product in Cart - Product Name: #{name}")
        .get("/cart/subtract/#{id}")
        .check(css("#grandTotal").isEL("$#{basketTotal}"))
    );

    public static final ChainBuilder checkout=
    exec(
        http("Checkout")
        .get("/cart/checkout")
        .check(substring("Your products are on their way to you now!!"))
    );

    

    
}