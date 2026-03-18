package acetoys.pageobjects;

import java.util.Map;

import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.core.CoreDsl.csv;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.feed;
import static io.gatling.javaapi.core.CoreDsl.substring;
import io.gatling.javaapi.core.FeederBuilder;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Category {

    private static final FeederBuilder<String> categoryFeeder = csv("data/categoryDetails.csv").circular();

    public static ChainBuilder productListByCategory =
    feed(categoryFeeder)
    .exec(
        http("Load Products List Page - Category: #{categoryName}")
        .get("/category/#{categorySlug}")
        .check(substring("#{categoryName}"))
        .check(css("#CategoryName").isEL("#{categoryName}"))
    );

    public static ChainBuilder cyclePageOfProducts =
    exec(session -> {
        int currentPageNumber = session.getInt("productsListPageNumber");
        int totalPages = session.getInt("categoryPages");
        boolean morePages = currentPageNumber < totalPages;
        System.out.println("More pages?" + morePages);
        return session.setAll(Map.of(
            "currentPageNumber", currentPageNumber,
            "nextPageNumber", (currentPageNumber + 1),
            "morePages", morePages
        ));
    })
    .asLongAs("#{morePages}").on(
            exec(http("Load Page #{currentPageNumber} of Products - Category: #{categoryName}}")
        .get("/category/#{categorySlug}?page=#{currentPageNumber}")
        .check(css(".page-item.active").isEL("#{nextPageNumber}")))
        .exec(session -> {
            int currentPageNumber = session.getInt("currentPageNumber");
            int totalPages = session.getInt("categoryPages");
            currentPageNumber++;
            boolean morePages = currentPageNumber < totalPages;
            return session.setAll(Map.of(
                "currentPageNumber", currentPageNumber,
                "nextPageNumber", (currentPageNumber + 1),
                "morePages", morePages
            ));
        })
        );
    
    // removed cause the above method got parametrized
    // public static ChainBuilder allProductsPage =
    // exec(
    //     http("Load Products List Page - Category: #{categoryName}")
    //     .get("/category/#{categorySlug}")
    //     .check(substring("All Products"))
    //     .check(css("#CategoryName").is("#categoryName"))
    // );

    // removed cause the above method got parametrized
    // public static ChainBuilder productListByCategory_BabiesToys=
    // exec(
    //     http("Load Products List Page - Category Babies Toys")
    //     .get("/category/babies-toys")
    //     .check(css("#CategoryName").is("Babies Toys"))
    // );

    public static ChainBuilder nextPageOfAllProducts_1 =
    exec(
        http("Load Next Page of Products List - Category All Products - Page 1")
        .get("/category/all?page=1")
        .check(css(".page-item.active").is("2"))
    );

    public static ChainBuilder nextPageOfAllProducts_2 =
    exec(
        http("Load Next Page of Products List - Category All Products - Page 2")
        .get("/category/all?page=2")
        .check(css(".page-item.active").is("3"))

    );
}