package acetoys.pageobjects;

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

public class Product {

    private static final FeederBuilder<Object> productFeeder =
    jsonFile("data/productDetails.json").random();

    public static final ChainBuilder loadProductDetailsPage =
    feed(productFeeder)
    .exec(
        http("Load Product Details Page - Product: #{name}")
        .get("/product/#{slug}")
        .check(css("#ProductDescription").isEL("#{description}"))
    );

    public static final ChainBuilder addProductToCart =
    exec(increaseItemsInBasketForSession)
    .exec(increaseSessionBasketTotal)
    .exec(
        http("Add Product to Cart - ProductID: #{id}")
        .get("/cart/add/#{id}")
        .check(substring("You have <span>#{itemsInBasket}</span> products in your Basket"))
    );
}