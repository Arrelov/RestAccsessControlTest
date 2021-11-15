package org.example.metods.getCheck;

import io.restassured.RestAssured;
import lombok.Data;
import lombok.Getter;
import org.example.entity.Mode;
import org.example.metods.Config;

@Data
public class CheckWithStatusCode {
    Config page = new Config();

    public void checkStatusCode(Mode mode, int keyId, int roomId, int expectedStatusCode) {
        RestAssured
                .given()
                .baseUri(page.getMyUrl())
                .when()
                .request("GET",
                        "check?entrance=" + mode +
                                "&keyId=" + keyId +
                                "&roomId=" + roomId)
                .prettyPeek()
                .then()
                .statusCode(expectedStatusCode);
    }
}
