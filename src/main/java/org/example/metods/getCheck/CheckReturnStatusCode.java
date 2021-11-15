package org.example.metods.getCheck;

import io.restassured.RestAssured;
import org.example.entity.Mode;
import org.example.metods.Config;

public class CheckReturnStatusCode {
    Config page = new Config();

    public int statusCode(Mode mode, int keyId, int roomId) {
        int statusCodeAnswer = RestAssured
                .given()
                .baseUri(page.getMyUrl())
                .when()
                .request("GET",
                        "check?entrance=" + mode +
                                "&keyId=" + keyId +
                                "&roomId=" + roomId)
                .prettyPeek()
                .then()
                .extract()
                .statusCode();
        System.out.println("statusCodeAnswer= " + statusCodeAnswer);
        return statusCodeAnswer;
    }
}
