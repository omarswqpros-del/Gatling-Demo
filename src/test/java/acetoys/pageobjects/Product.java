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

    public static ChainBuilder loadProductDetailsPage =
    feed(productFeeder)
    .exec(
        http("Load Product Details Page - Product: #{name}")
        .get("/product/#{slug}")
        .check(css("#ProductDescription").isEL("#{description}"))
    );

    public static ChainBuilder addProductToCart =
    exec(increaseItemsInBasketForSession)
    .exec(increaseSessionBasketTotal)
    .exec(
        http("Add Product to Cart - ProductID: #{id}")
        .get("/cart/add/#{id}")
        .check(substring("You have <span>#{itemsInBasket}</span> products in your Basket"))
    );
    

    //removed to parametrize it and make it general
    // public static ChainBuilder loadProductDetailsPage_DartBoards =
    // exec(
    //     http("Load Product Details Page - Product: Darts Board")
    //     .get("/product/darts-board")
    //     .check(css("#ProductDescription").is("Get all your mates round for a few drinks and throw sharp objects at this darts board"))
    // );

    // removed to parametrize
    // public static ChainBuilder addProductToCart_19 =
    // exec(
    //     http("Add Product to Cart - ProductID: Darts Board")
    //     .get("/cart/add/19")
    //     .check(substring("You have <span>1</span> products in your Basket"))
    // );

    // removed to parametrized
    //     public static ChainBuilder addProductToCart_11 =
    // exec(
    //     http("Add Product to Cart - ProductID: Darts Board")
    //     .get("/cart/add/11")
    //     .check(substring("You have <span>2</span> products in your Basket"))
    // );

    // removed to make it parametrized
    //     public static ChainBuilder addProductToCart_11_2 =
    // exec(
    //     http("Add Product to Cart - ProductID: Darts Board")
    //     .get("/cart/add/11")
    //     .check(substring("You have <span>3</span> products in your Basket"))
    // );


}