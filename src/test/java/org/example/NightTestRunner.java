package org.example;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(
        features = "src/test/java/org/example/nightFeature",
        glue = "org/example/nightSteps",
        plugin = "pretty"
)

@RunWith(Cucumber.class)
public class NightTestRunner {
}
