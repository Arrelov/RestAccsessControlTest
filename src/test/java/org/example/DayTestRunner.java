package org.example;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(
        features = "src/test/java/org/example/dayFeature",
        glue = "org/example/daySteps",
        plugin = "pretty"
)

@RunWith(Cucumber.class)
public class DayTestRunner {
}
