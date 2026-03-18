package acetoys.simulation;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.percent;
import static io.gatling.javaapi.core.CoreDsl.randomSwitch;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import io.gatling.javaapi.core.ScenarioBuilder;

public class TestScenario {

    private static final Duration TEST_DURATION = Duration.ofSeconds(Integer.parseInt(System.getProperty("DURATION", "60")));

    // Helper method to keep logic DRY (Don't Repeat Yourself)
    // This represents the "User Loop" that runs for the duration of the test
    private static ScenarioBuilder createScenario(String name, double browse, double abandon, double purchase) {
        return scenario(name)
            .during(TEST_DURATION).on(
                randomSwitch().on(
                    // Modern Java DSL: Use percent(x).then(chain)
                    percent(browse).then(exec(UserJourney.browseStore)),
                    percent(abandon).then(exec(UserJourney.abandonBasket)),
                    percent(purchase).then(exec(UserJourney.completePurchase))
                )
            );
    }

    // 1. Default Load Test: Balanced weights
    public static ScenarioBuilder defaultLoadTest = 
        createScenario("Default Load Test", 60.0, 30.0, 10.0);

    // 2. High Purchase Load Test: Heavy weight on purchasing
    public static ScenarioBuilder highPurchaseLoadTest = 
        createScenario("High Purchase Load Test", 30.0, 30.0, 40.0);
}