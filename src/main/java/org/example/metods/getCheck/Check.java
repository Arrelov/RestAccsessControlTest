package org.example.metods.getCheck;

import io.restassured.RestAssured;
import org.example.entity.Mode;
import org.example.metods.Config;

public class Check {
    Config page = new Config();

    @Deprecated     // better use TestCheckWithStatusCode
    public void testCheck(Mode mode, int keyId, int roomId) {
        RestAssured
                .given()
                .baseUri(page.getMyUrl())
                .when()
                .param("Date", "Wed, 10 Nov 2021 10:40:20 GMT")
                .request("GET",
                        "check?entrance=" + mode +
                                "&keyId=" + keyId +
                                "&roomId=" + roomId)
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body();
    }
}
