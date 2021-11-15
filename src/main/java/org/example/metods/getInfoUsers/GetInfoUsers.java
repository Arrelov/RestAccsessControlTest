package org.example.metods.getInfoUsers;

import io.restassured.RestAssured;
import lombok.Data;
import org.example.metods.Config;
import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.hasSize;

@Data
public class GetInfoUsers {
    Config page = new Config();

    public String getInfo(int start, int end){
        String body = RestAssured
                .given()
                .baseUri(page.getMyUrl())
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
}
