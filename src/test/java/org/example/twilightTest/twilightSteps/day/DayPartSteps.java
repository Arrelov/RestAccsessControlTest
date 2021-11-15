package org.example.twilightTest.twilightSteps.day;

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


public class DayPartSteps {

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

}
