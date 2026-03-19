package acetoys.simulation;

import java.time.Duration;

import acetoys.pageobjects.Cart;
import acetoys.pageobjects.Category;
import acetoys.pageobjects.Customer;
import acetoys.pageobjects.Product;
import acetoys.pageobjects.StaticPages;
import acetoys.session.UserSession;
import static acetoys.session.UserSession.initSession;
import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.exec;

public class UserJourney {

    private static final Duration LOW_PAUSE = Duration.ofMillis(1000);
    private static final Duration HIGH_PAUSE = Duration.ofMillis(3000);

    public static final ChainBuilder browseStore =
    exec(UserSession.initSession)
    
    .exec(StaticPages.homepage)
    .pause(HIGH_PAUSE)

    .exec(StaticPages.ourStoryPage)
    .pause(LOW_PAUSE,HIGH_PAUSE)

    .exec(StaticPages.getInTouchPage)
    .pause(LOW_PAUSE,HIGH_PAUSE)

    .repeat(3).on(
        exec(Category.productListByCategory)
        .pause(LOW_PAUSE,HIGH_PAUSE)
        .exec(Category.cyclePageOfProducts)
        .pause(LOW_PAUSE,HIGH_PAUSE)
        .exec(Product.loadProductDetailsPage)
    );

    public static final ChainBuilder abandonBasket =
    exec(initSession)
    .exec(StaticPages.homepage)
    .pause(LOW_PAUSE, HIGH_PAUSE)
    .exec(Category.productListByCategory)
    .pause(LOW_PAUSE, HIGH_PAUSE)
    .exec(Product.loadProductDetailsPage)
    .pause(LOW_PAUSE, HIGH_PAUSE)
    .exec(Product.addProductToCart);

    public static final ChainBuilder completePurchase =
    exec(initSession)
    .exec(StaticPages.homepage)
    .pause(LOW_PAUSE, HIGH_PAUSE)
    .exec(Category.productListByCategory)
    .pause(LOW_PAUSE, HIGH_PAUSE)
    .exec(Product.loadProductDetailsPage)
    .pause(LOW_PAUSE, HIGH_PAUSE)
    .exec(Product.addProductToCart)
    .pause(LOW_PAUSE, HIGH_PAUSE)
    .exec(Cart.viewCart)
    .pause(LOW_PAUSE, HIGH_PAUSE)
    .exec(Cart.increaseQuantityInCart)
    .pause(LOW_PAUSE, HIGH_PAUSE)
    .exec(Cart.decreaseQuantityInCart)
    .pause(LOW_PAUSE, HIGH_PAUSE)
    .exec(Cart.checkout)
    .pause(LOW_PAUSE, HIGH_PAUSE)
    .exec(Customer.logout);

}

