package org.example.twilightTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(
        features = "src/test/java/org/example/twilightTest/feature/DayPart.feature",
        glue = "org/example/twilightTest/twilightSteps/day",
        plugin = "pretty"
)

@RunWith(Cucumber.class)
public class DayPreConditionTestRunner {
}
