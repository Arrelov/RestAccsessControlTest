package org.example.twilightTest.twilightSteps.night;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.entity.Mode;
import org.example.metods.NightTimeCheck;
import org.example.metods.getCheck.CheckReturnStatusCode;
import org.example.metods.getInfoRooms.ServiceAvailable;
import org.junit.Assert;

public class NightPartSteps {

    @BeforeAll
    public static void serviceIsAvailable(){
        ServiceAvailable serv = new ServiceAvailable();
        serv.serviceAvailable();                         // ignore test if service offline
        NightTimeCheck night = new NightTimeCheck();
        Assert.assertTrue(night.nightTime());            // ignore test if working time
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
