package uk.co.edgewords;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class GoogleSteps { //Class/file name is not important - Cucumber will scan all classes for potential step definition matches (annotated methods)

    private WebDriver driver;
    private final SharedDictionary dict;
    public GoogleSteps(SharedDictionary dict){
        this.dict = dict;
        this.driver = (WebDriver)dict.readDict("mydriver"); //Retreive the webdriver instance put in the dictionary in Hooks.class
    }

    //Multiple annotations can be used to deal with people phrasing the same inteded actions slightly differently
    @Given("I am on the Google search page") //The annotation marries up with the gherkin step in the feature file
    @Given("^I am at (?i)Googl(?-i)e$") //Using a regex and turning case sensitivity off and on
    @Given("^(?:I|i) am on Google$") //Dont actually want to pass through "I" or "i" so use a non capture group
    public void i_am_on_the_google_search_page() {
        driver.get("https://www.google.co.uk/");
        driver.findElement(By.id("L2AGLb")).click(); //Dismiss cookies - technical detail best handled here (not in feature file)
        String bodyText = driver.findElement(By.tagName("body")).getText();
        dict.addDict("myBodyText", bodyText);
    }



    @Then("{string} is the top result") //Then steps are validation points
    public void is_the_top_result(String desiredResult) throws InterruptedException {
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(
                ExpectedConditions.presenceOfElementLocated(By.id("botstuff")) //Page navigation at bottom of search results
        );
        String topResult = driver.findElement(By.cssSelector("#rso > div:nth-child(1)")).getText(); //Gets text of top result
        assertThat(topResult, containsString(desiredResult)); //Assert that the top result has the text we want
    }

    @Then("I see in the results")
    public void i_see_in_the_results(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.

        //Get the search results
        String searchResults = driver.findElement(By.id("rso")).getText();

        List<Map<String,String>> data = dataTable.asMaps(String.class, String.class);
        for (var row : data) {
            assertThat(searchResults, containsString(row.get("url")));
            assertThat(searchResults, containsString(row.get("description")));
        };
    }

}
