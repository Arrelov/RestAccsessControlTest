package org.example.twilightTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(
        features = "src/test/java/org/example/twilightTest/feature/NightPart.feature",
        glue = "org/example/twilightTest/twilightSteps/night",
        plugin = "pretty"
)

@RunWith(Cucumber.class)
public class NightPartTestRunner {
}
