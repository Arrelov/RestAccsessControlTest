package org.example.daySteps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.entity.Mode;
import org.example.metods.NightTimeCheck;
import org.example.metods.getCheck.CheckReturnStatusCode;
import org.example.metods.getCheck.CheckWithStatusCode;
import org.example.metods.getInfoRooms.GetInfoRooms;
import org.example.metods.getInfoRooms.JsonCompareMakerRoom1;
import org.example.metods.getInfoRooms.ServiceAvailable;
import org.junit.Assert;


import static org.example.entity.Mode.ENTRANCE;
import static org.example.entity.Mode.EXIT;


public class AccessControl {

    @BeforeAll
    public static void serviceIsAvailable(){
        ServiceAvailable serv = new ServiceAvailable();
        serv.serviceAvailable();                        // ignore test if service offline
        NightTimeCheck night = new NightTimeCheck();
        Assert.assertFalse(night.nightTime());          // ignore test if night
    }

    int requestStatusCode;
    @When("I send request mode {string} for keyId {int} roomId {int}")
    public void i_send_request_with_and(String mode, int keyId, int roomId) {
        CheckReturnStatusCode test = new CheckReturnStatusCode();
        requestStatusCode = test.statusCode(Mode.valueOf(mode), keyId, roomId);
    }

    @Then("Access control service response {int}")
    public void access_control_service_response(int statusCode) {
        Assert.assertEquals(statusCode, requestStatusCode);
    }

    @When("I run positive test by logic keyId % roomId == 0 -> Access granted")
    public void getInAndOutPositiveTest() {
        System.out.println("Start");
        for (int keyId = 1; keyId < 11; keyId++) {
            System.out.println("keyId= " + keyId);
            for (int roomId = 1; roomId < 6; roomId++) {
                if (keyId % roomId == 0) {
                    System.out.println("\tAccess granted! keyId= " + keyId + " roomId= " + roomId);
                    CheckWithStatusCode check = new CheckWithStatusCode();
                    check.checkStatusCode(ENTRANCE, keyId, roomId, 200);
                    check.checkStatusCode(EXIT, keyId, roomId, 200);
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
                    CheckWithStatusCode check = new CheckWithStatusCode();
                    check.checkStatusCode(ENTRANCE, keyId, roomId, 403);
                }
            }
        }
    }


    @When("All keyId enters first room")
    public void getEveryBodyInRoom1Test() {
        System.out.println("Get Everybody In Room1");
        for (int keyId = 1; keyId < 11; keyId++) {
            int roomId = 1;
            System.out.println("keyId= " + keyId);
            CheckWithStatusCode check = new CheckWithStatusCode();
            check.checkStatusCode(ENTRANCE, keyId, roomId, 200);
        }
    }

    @Then("All keyId displayed in room request")
    public void all_keyId_displayed() {
        JsonCompareMakerRoom1 room1 = new JsonCompareMakerRoom1();
        String expectedResponseBody = room1.jsonRoom1();
        GetInfoRooms rooms = new GetInfoRooms();
        String responseBody = rooms.getInfo();
        System.out.println("expectedRBody= " + expectedResponseBody);
        System.out.println("responseBody = " + responseBody);
        Assert.assertEquals(expectedResponseBody, responseBody);
        System.out.println("Get Everybody Out Room1");
        for (int keyId = 1; keyId < 11; keyId++) {
            int roomId = 1;
            System.out.println("keyId= " + keyId);
            CheckWithStatusCode check = new CheckWithStatusCode();
            check.checkStatusCode(EXIT, keyId, roomId, 200);
        }
    }

    @AfterAll   // in the end of tests reset room status
    public static void everyBodyOut() {
        System.out.println("AfterAll everyBodyOut");
        for (int keyId = 1; keyId < 11; keyId++) {
            System.out.println("keyId= " + keyId);
            for (int roomId = 1; roomId < 6; roomId++) {
                if (keyId % roomId == 0) {
                    System.out.println("\tGo home! keyId= " + keyId + " roomId= " + roomId);
                    CheckReturnStatusCode check = new CheckReturnStatusCode();
                    check.statusCode(EXIT, keyId, roomId);
                }
            }
        }
    }
}
