package org.example;

import io.cucumber.messages.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import lombok.SneakyThrows;
import org.example.entity.Mode;
import org.example.entity.Room;

import java.util.ArrayList;
import java.util.List;

public class LogicAccessControlTestMethods {
    public String myUrl = "http://localhost:8080/";

    public int testCheckStatusCode(Mode mode, int keyId, int roomId) {
        int statusCodeAnswer = RestAssured
                .given()
                .baseUri(myUrl)
                .when()
                .request("GET",
                        "check?entrance=" + mode +
                                "&keyId=" + keyId +
                                "&roomId=" + roomId)
                .prettyPeek()
                .then()
                .extract()
                .statusCode();
//        System.out.println("statusCodeAnswer= " + statusCodeAnswer);
        return statusCodeAnswer;
    }

    public void testCheckWithStatusCode(Mode mode, int keyId, int roomId, int expectedStatusCode) {
        RestAssured
                .given()
                .baseUri(myUrl)
                .when()
                .param("Date", "Wed, 10 Nov 2021 10:40:20 GMT")
                .request("GET",
                        "check?entrance=" + mode +
                                "&keyId=" + keyId +
                                "&roomId=" + roomId)
                .prettyPeek()
                .then()
                .statusCode(expectedStatusCode);
    }

    public String testRoomInfo(){
        String body = RestAssured
                .given()
                .baseUri(myUrl)
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

    @SneakyThrows
    public String jsonCompereMaker(int keyId, int RoomId) {
        Room firstRoom = new Room();
        firstRoom.setRoomId(RoomId);
        List<Integer> list1 = new ArrayList<>();
        list1.add(keyId);
        firstRoom.setUserIds(list1);
        System.out.println("firstRoom " + firstRoom);

        ObjectMapper mapper = new ObjectMapper();
        List<Room> listRoom = new ArrayList<>();
        listRoom.add(firstRoom);
        String jsonString = mapper.writeValueAsString(listRoom);
        System.out.println("listRoom json " + jsonString);
        return jsonString;
    }


    public void testGetInfo(){
        String bodyResponse =  getInfoUsers(1, 10);
        System.out.println("bodyResponse= " + bodyResponse);
    }

    public String getInfoUsers(int start, int end){
        String body = RestAssured
                .given()
                .baseUri(myUrl)
                .when()
                .request("GET",
                        "/info/users?end=" + end +
                                "&start=" + start)
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        return body;
    }

}
