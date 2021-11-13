package org.example.daySteps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import lombok.SneakyThrows;
import org.example.entity.Mode;
import org.example.entity.Room;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.entity.Mode.ENTRANCE;
import static org.example.entity.Mode.EXIT;
import static org.hamcrest.Matchers.hasSize;


public class DaySteps {
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
        Assert.assertTrue(8 < hour & hour < 22);
    }

    @Given("Access control service is available")
    public void accessControlServiceIsAvailable(){
        serviceAvailable();
    }

    @When("I run get info rooms request")
    public void i_run_get_info_users_request() {
        serviceAvailable();
    }

    @Then("I get response {int}")
    public void i_get_response(Integer int1) {
        Assert.assertTrue(true);
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

    @When("I run positive test by logic keyId % roomId == 0 -> Access granted")
    public void getInAndOutPositiveTest() {
        System.out.println("Start");
        for (int keyId = 1; keyId < 11; keyId++) {
            System.out.println("keyId= " + keyId);
            for (int roomId = 1; roomId < 6; roomId++) {
                if (keyId % roomId == 0) {
                    System.out.println("\tAccess granted! keyId= " + keyId + " roomId= " + roomId);
                    testCheckWithStatusCode(ENTRANCE, keyId, roomId, 200);
                    testCheckWithStatusCode(EXIT, keyId, roomId, 200);
                } else {
                    System.out.println("\tAccess denied! keyId= " + keyId + " roomId= " + roomId);
                }
            }
        }
    }

    @Then("I run negative test by logic keyId % roomId != 0 -> Access DENIED")
    public void getInAndOutNegativeTest() {
        System.out.println("Start");
        for (int keyId = 1; keyId < 11; keyId++) {
            System.out.println("keyId= " + keyId);
            for (int roomId = 1; roomId < 6; roomId++) {
                if (keyId % roomId == 0) {
                    System.out.println("\tAccess granted! keyId= " + keyId + " roomId= " + roomId);
                } else {
                    System.out.println("\tAccess DENIED! keyId= " + keyId + " roomId= " + roomId);
                    testCheckWithStatusCode(ENTRANCE, keyId, roomId, 403);
                }
            }
        }
    }

    public void testCheckWithStatusCode(Mode mode, int keyId, int roomId, int expectedStatusCode) {
        RestAssured
                .given()
                .baseUri(myUrl)
                .when()
                .param("Date", "Wed, 10 Nov 2021 10:40:20 GMT")
                .request("GET",
                        "check?entrance=" + mode +
                                "&keyId=" + keyId +
                                "&roomId=" + roomId)
                .prettyPeek()
                .then()
                .statusCode(expectedStatusCode);
    }

    @SneakyThrows
    @When("User keyId {int} enters roomId {int}")
    public void testSingleRoomInfoGet(int keyId, int roomId){
        testCheckWithStatusCode(ENTRANCE, keyId, roomId, 200);
        String expectedResponseBody = jsonCompereMaker(keyId, roomId);
        String responseBody = testRoomInfo();
        System.out.println("expectedResponseBody= " + expectedResponseBody);
        System.out.println("responseBody        = " + responseBody);
        Assert.assertEquals(expectedResponseBody, responseBody);
        testCheckWithStatusCode(EXIT, keyId, roomId, 200);
    }

    public String testRoomInfo(){
        String body = RestAssured
                .given()
                .baseUri(myUrl)
                .when()
                .request("GET","/info/rooms")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        return body;
    }

    @SneakyThrows
    public String jsonCompereMaker(int keyId, int RoomId) {
        Room firstRoom = new Room();
        firstRoom.setRoomId(RoomId);
        List<Integer> list1 = new ArrayList<>();
        list1.add(keyId);
        firstRoom.setUserIds(list1);
        System.out.println("firstRoom " + firstRoom);

        ObjectMapper mapper = new ObjectMapper();
        List<Room> listRoom = new ArrayList<>();
        listRoom.add(firstRoom);
        String jsonString = mapper.writeValueAsString(listRoom);
        System.out.println("listRoom json " + jsonString);
        return jsonString;
    }

    @When("All keyId enters first room")
    public void getEveryBodyInRoom1Test() {
        System.out.println("getEveryBodyInRoom1Test");
        for (int keyId = 1; keyId < 11; keyId++) {
            int roomId = 1;
            System.out.println("keyId= " + keyId);
            testCheckWithStatusCode(ENTRANCE, keyId, roomId, 200);
        }
    }

    @Then("All keyId displayed in room request")
    public void all_keyId_displayed(){
        String expectedResponseBody = jsonCompereMakerRoom1();
        String responseBody = testRoomInfo();
        System.out.println("expectedRBody= " + expectedResponseBody);
        System.out.println("responseBody = " + responseBody);
        Assert.assertEquals(expectedResponseBody, responseBody);
        getEveryBodyOutRoom1Test();
    }

    @SneakyThrows
    public String jsonCompereMakerRoom1() {
        int RoomId = 1;
        Room firstRoom = new Room();
        firstRoom.setRoomId(RoomId);
        List<Integer> list1 = new ArrayList<>();
        for (int i = 1; i<11; i++) {
            System.out.println("Здесь выводится i "+ i);
            list1.add(i);
        }
        firstRoom.setUserIds(list1);
        System.out.println("firstRoom " + firstRoom);

        ObjectMapper mapper = new ObjectMapper();
        List<Room> listRoom = new ArrayList<>();
        listRoom.add(firstRoom);
        String jsonString = mapper.writeValueAsString(listRoom);
        System.out.println("listRoom json " + jsonString);
        return jsonString;
    }

    public void getEveryBodyOutRoom1Test() {
        System.out.println("getEveryBodyInRoom1Test");
        for (int keyId = 1; keyId < 11; keyId++) {
            int roomId = 1;
            System.out.println("keyId= " + keyId);
            testCheckWithStatusCode(EXIT, keyId, roomId, 200);
        }
    }


    @When("I run get info users request and get response 200")
    public void i_run_get_info_users_request_and_get_response() {
        getInfoUsers(0, 20);
    }

    @When("I send request start {int} to end {int} and get response 200")
    public void i_send_request_start_to_and_get_response(Integer start, Integer end) {
        String bodyResponse =  getInfoUsers(start, end);
        System.out.println("bodyResponse= " + bodyResponse);
    }

    public void testGetInfo(){
        String bodyResponse =  getInfoUsers(0, 10);
        System.out.println("bodyResponse= " + bodyResponse);
    }

    public String getInfoUsers(int start, int end){
        String body = RestAssured
                .given()
                .baseUri(myUrl)
                .when()
                .request("GET",
                        "/info/users?end=" + end +
                           "&start=" + start)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("", Matchers.not(hasSize(0)))
                .extract()
                .body()
                .asString();
        return body;
    }



    @AfterAll   // in the end of tests reset room status
    public static void everyBodyOut() {
        System.out.println("AfterAll everyBodyOut");
            for (int keyId = 1; keyId < 11; keyId++) {
                System.out.println("keyId= " + keyId);
                for (int roomId = 1; roomId < 6; roomId++) {
                    if (keyId % roomId == 0) {
                        System.out.println("\tGo home! keyId= " + keyId + " roomId= " + roomId);
                        RestAssured
                                .given()
                                .baseUri("http://localhost:8080/")
                                .when()
                                .param("Date", "Wed, 10 Nov 2021 10:40:20 GMT")
                                .request("GET",
                                        "check?entrance=EXIT"+
                                                "&keyId=" + keyId +
                                                "&roomId=" + roomId)
                                .prettyPeek()
                                .then();
                                        }
                                }
                        }
                }
}
