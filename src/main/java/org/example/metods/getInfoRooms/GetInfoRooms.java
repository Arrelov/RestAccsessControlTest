package org.example.metods.getInfoRooms;

import io.restassured.RestAssured;
import org.example.metods.Config;

public class GetInfoRooms {
    Config page = new Config();

    public String getInfo(){
        String body = RestAssured
                .given()
                .baseUri(page.getMyUrl())
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
}
