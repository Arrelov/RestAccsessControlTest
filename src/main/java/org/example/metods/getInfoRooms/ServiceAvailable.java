package org.example.metods.getInfoRooms;

import io.restassured.RestAssured;
import org.example.metods.Config;

public class ServiceAvailable {

    public void serviceAvailable(){
        Config page = new Config();
        RestAssured
                .given()
                .baseUri(page.getMyUrl())
                .when()
                .request("GET","/info/rooms")
                .then()
                .statusCode(200);
    }
}
