
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/Features",
        extraGlue = {"framework", "Hooks"},
        plugin = {"pretty", "html:target/cucumber=html-report"},
        tags = "@AuthorsAPI")
                //"@StampDuty")

public class testRunner {}
