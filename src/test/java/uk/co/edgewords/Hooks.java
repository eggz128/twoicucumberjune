package uk.co.edgewords;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class Hooks {
    private WebDriver driver; //Field to hold WebDriver instance - shareable between methods in this class

//    protected static WebDriver getDriver(){
//        //Can do some security checks here before returning the driver
//        return driver;
//    }

    private final SharedDictionary dict; //Field to hold local instance of the dictionary to be used by methods in this class

    public Hooks(SharedDictionary dict){ //Pico-container will init the SharedDictionary for us (if it hasn't done so already for another class)
        this.dict = dict; //take the Pico-container supplied dictionary and put it in this class's private field
    }

    @Before("@GUI") //Runs before each and every scenario
    public void setUp(){ //Test CI
        String browser = System.getProperty("browser");
        System.out.println("Browser from commandline: " + browser);
        if(browser==null){browser="";}
        switch (browser) {
            case "firefox" -> driver = new FirefoxDriver();
            case "chrome" -> driver = new ChromeDriver();
            default -> {
                System.out.println("Invalid browser selection. Using Edge");
                driver = new EdgeDriver();
            }
        }


        dict.addDict("mydriver", driver); //Put the driver in the dictionary - to be extracted in other classes that need it
        //dict.addDict("mydriver", "Steve"); //DANGER: A string is not a WebDriver - so casting back later will fail at runtime - no compiler warnings
    }

    @After("@GUI") //Runs after each and every scenario - even if steps in the scenario fail
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000); //Just so we can observe the results, keep the browser open for 2 secs
        driver.quit(); //before cleanup (quit)
    }

    @Before("@API")
    public void setUpAPI(){
        RequestSpecification spec = given();
        spec.baseUri("http://localhost");
        spec.port(2002);
        spec.contentType(ContentType.JSON);

        requestSpecification = spec;
    }
}
