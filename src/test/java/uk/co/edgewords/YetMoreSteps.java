package uk.co.edgewords;

import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

public class YetMoreSteps {

    private final SharedDictionary dict;

    private WebDriver driver;

    public YetMoreSteps(SharedDictionary dict) {
        this.dict = dict;
        this.driver = (WebDriver)dict.readDict("mydriver");
    }

    //Now that the driver is retrieved via dependency injection steps for a scenario can be spread over multiple classes
    @When("I search for {string}")
    public void blah_blah_blahi_search_for(String searchTerm) throws InterruptedException {
        System.out.println((String)dict.readDict("myBodyText")); //bodyText is captured in GoogleSteps.class
        driver.findElement(By.cssSelector("#APjFqb")).click(); //Click the search box
        driver.findElement(By.cssSelector("#APjFqb")).sendKeys(searchTerm);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(".UUbT9 input[aria-label='Google Search']"))
        ).click();
    }
}
