package org.example.nightSteps;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.example.entity.Mode;
import org.junit.Assert;

import java.time.LocalTime;

public class NightSteps {
    String myUrl = "http://localhost:8080/";

    @BeforeAll
    public static void serviceAvailable1(){     // quit tests if service offline
        RestAssured
                .given()
                .baseUri("http://localhost:8080/")
                .when()
                .request("GET","/info/rooms")
                .then()
                .statusCode(200);
    }

    @BeforeAll
    public static void timeTest(){
        int hour = LocalTime.now().getHour();
        System.out.println("hour= " + hour);
            Assert.assertFalse(8 < hour & hour < 22);
    }

    @Given("Access control service is available")
    public void accessControlServiceIsAvailable(){
        serviceAvailable();
    }

    public void serviceAvailable(){
        RestAssured
                .given()
                .baseUri(myUrl)
                .when()
                .request("GET","/info/rooms")
                .then()
                .statusCode(200);
    }

    int requestStatusCode;
    @When("I send request mode {string} for keyId {int} roomId {int}")
    public void i_send_request_with_and(String mode, int keyId, int roomId) {
        requestStatusCode = testCheckStatusCode(Mode.valueOf(mode), keyId, roomId);
    }

    @Then("Access control service response {int}")
    public void access_control_service_response(int statusCode) {
        Assert.assertEquals(statusCode, requestStatusCode);
    }

    public int testCheckStatusCode(Mode mode, int keyId, int roomId) {
        int statusCodeAnswer = RestAssured
                .given()
                .baseUri(myUrl)
                .when()
                .request("GET",
                        "check?entrance=" + mode +
                                "&keyId=" + keyId +
                                "&roomId=" + roomId)
                .prettyPeek()
                .then()
                .extract()
                .statusCode();
//        System.out.println("statusCodeAnswer= " + statusCodeAnswer);
        return statusCodeAnswer;
    }

}
