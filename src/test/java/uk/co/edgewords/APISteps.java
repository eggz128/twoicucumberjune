package uk.co.edgewords;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.when;

public class APISteps {

    private final SharedDictionary dict; //Field to hold local instance of the dictionary to be used by methods in this class

    public APISteps(SharedDictionary dict){ //Pico-container will init the SharedDictionary for us (if it hasn't done so already for another class)
        this.dict = dict; //take the Pico-container supplied dictionary and put it in this class's private field
    }
    @When("I request all products")
    public void i_request_all_products() {
        var response = when().get("/api/products");
        dict.addDict("response", response);
    }
    @Then("I get a status code of {int}")
    public void i_get_a_status_code_of(Integer expectedCode) {
        Response response = (Response)dict.readDict("response");
        response.then().statusCode(expectedCode);
    }
}
