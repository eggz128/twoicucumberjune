package uk.co.edgewords;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.checkerframework.checker.regex.qual.Regex;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
public class JunitApiTests {

//    @Test
//    void firstRequestTest(){
//        RequestSpecification req = given().baseUri("http://localhost:2002/");
//        Response res = req.when().get("api/products");
//        ValidatableResponse val = res.then();
//        var statusCode = res.statusCode();
//        assertThat(Integer.toString(statusCode), startsWith("2"));
//        val.assertThat().statusCode(200);
////        res.then().assertThat().statusCode(200);
//    }
//
//    @Test
//    void shorterVersion(){
//        given().baseUri("http://localhost:2002/").
//                when().get("api/products")
//                .then().statusCode(200).log().body();
//    }
    @BeforeAll
    public static void setUpDefaultRequestSpec(){
        RequestSpecification spec = given();
        spec.baseUri("http://localhost");
        spec.port(2002);
        spec.contentType(ContentType.JSON);

        requestSpecification = spec;
    }

    @Test
    void testUsingDefaultSpec(){
        when().get("api/products/2")
                .then()
                .log().body()
                .log().headers()
                .statusCode(200)
                .body("name", equalTo("iPhone X"));

    }

}
