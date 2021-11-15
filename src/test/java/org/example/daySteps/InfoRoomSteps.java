package org.example.daySteps;

import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import org.example.metods.getCheck.CheckWithStatusCode;
import org.example.metods.getInfoRooms.GetInfoRooms;
import org.example.metods.getInfoRooms.JsonCompareMaker;
import org.example.metods.getInfoRooms.ServiceAvailable;
import org.junit.Assert;

import static org.example.entity.Mode.ENTRANCE;
import static org.example.entity.Mode.EXIT;

public class InfoRoomSteps {

    @When("I run get info rooms request")
    public void i_run_get_info_users_request() {
        ServiceAvailable serv = new ServiceAvailable();
        serv.serviceAvailable();
    }

    @When("User keyId {int} enters roomId {int}")
    @SneakyThrows
    public void testSingleRoomInfoGet(int keyId, int roomId){
        CheckWithStatusCode check = new CheckWithStatusCode();
        check.checkStatusCode(ENTRANCE, keyId, roomId, 200);
        JsonCompareMaker json = new JsonCompareMaker();
        String expectedResponseBody = json.jsonCompereMaker(keyId, roomId);
        GetInfoRooms rooms = new GetInfoRooms();
        String responseBody = rooms.getInfo();
        System.out.println("expectedResponseBody= " + expectedResponseBody);
        System.out.println("responseBody        = " + responseBody);
        Assert.assertEquals(expectedResponseBody, responseBody);
        check.checkStatusCode(EXIT, keyId, roomId, 200);
    }
}
