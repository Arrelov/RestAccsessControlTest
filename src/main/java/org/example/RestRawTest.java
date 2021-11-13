// java –jar accesscontrol-1.0-SNAPSHOT.jar

package org.example;


import io.cucumber.messages.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import lombok.SneakyThrows;
import org.example.entity.Mode;
import org.example.entity.Room;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.entity.Mode.ENTRANCE;
import static org.example.entity.Mode.EXIT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

public class RestRawTest {
    String myUrl = "http://localhost:8080/";

public void exitRoom(int keyId, int roomId) {
    String mode = "EXIT";
    RestAssured
        .given()
            .baseUri(myUrl)
        .when()
            .request("GET",
                    "check?entrance=" + mode +
                    "&keyId=" + keyId +
                    "&roomId=" + roomId)
            .prettyPeek()
        .then()
            .statusCode(200);
}

@Test
public void getInAndOutPositiveTest() {
    System.out.println("Start");
    for (int keyId = 1; keyId < 11; keyId++) {
        System.out.println("keyId= " + keyId);
        for (int roomId = 1; roomId < 6; roomId++) {
            if (keyId % roomId == 0) {
                System.out.println("\tAccess granted! keyId= " + keyId + " roomId= " + roomId);
                testCheckWithStatusCode(ENTRANCE, keyId, roomId, 200);
                testCheckWithStatusCode(EXIT, keyId, roomId, 200);
            } else {
                System.out.println("\tAccess denied! keyId= " + keyId + " roomId= " + roomId);
            }
        }
    }
}

    @Test
    public void getInAndOutNegativeTest() {
        System.out.println("Start");
        for (int keyId = 1; keyId < 11; keyId++) {
            System.out.println("keyId= " + keyId);
            for (int roomId = 1; roomId < 6; roomId++) {
                if (keyId % roomId == 0) {
                    System.out.println("\tAccess granted! keyId= " + keyId + " roomId= " + roomId);
                } else {
                    System.out.println("\tAccess DENIED! keyId= " + keyId + " roomId= " + roomId);
                    testCheckWithStatusCode(ENTRANCE, keyId, roomId, 403);
                }
            }
        }
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

        public void testCheck(Mode mode, int keyId, int roomId) {
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
                .statusCode(200)
                .extract()
                .body();
    }

    @Test
    public void testCheckTest() {
        int keyId = 9;
        int roomId = 1;
        testCheck(ENTRANCE, keyId, roomId);
        exitRoom(keyId, roomId);
    }

    @Test
    public void checkEntranceInAndOut() {
        String mode = "ENTRANCE";
        int keyId = 10;
        int roomId = 1;
        RestAssured
            .given()
                .baseUri(myUrl)
            .when()
                .request("GET",
                        "check?entrance=" + mode +
                        "&keyId=" + keyId +
                        "&roomId=" + roomId)
                .prettyPeek()
            .then()
                .statusCode(200)
                .extract()
                .body();
        exitRoom(keyId, roomId);
    }

    @Test
    public void checkEntranceIn() {
        String mode = "EXIT";
        int keyId = 1;
        int roomId = 1;
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
        System.out.println("statusCodeAnswer= " + statusCodeAnswer);
    }

    @Test
    public void checkEntranceOut() {
        String mode = "EXIT";
        int keyId = 3;
        int roomId = 3;
        RestAssured
                .given()
                .baseUri(myUrl)
                .when()
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

    @Test
    public void testIn() {
        RestAssured
            .when()
                    .get("http://localhost:8080/check?entrance=ENTRANCE&keyId=3&roomId=3")
                            .prettyPeek()
            .then()
                    .assertThat()
                    .statusCode(200)
            .and()
                    .body(is("You are welcome!"));
    }

    @Test
    public void testInWithParameters(){
        testCheckWithStatusCode(EXIT, 2, 2, 200);
        testCheckWithStatusCode(EXIT, 4, 2, 200);
        testCheckWithStatusCode(EXIT, 3, 3, 200);
    }

    @Test
    public void testSingleRoomGetInfo(){

        String responseBody = testRoomInfo();
    }


    @SneakyThrows
    @Test
    public void testRoomInfoGet(){
        int keyId = 1;
        int roomId = 1;
        testCheckWithStatusCode(ENTRANCE, keyId, roomId, 200);
        String expectedResponseBody = jsonCompereMaker(keyId, roomId);
        String responseBody = testRoomInfo();
        System.out.println("expectedResponseBody= " + expectedResponseBody);
        System.out.println("responseBody        = " + responseBody);
        Assert.assertEquals(expectedResponseBody, responseBody);
        testCheckWithStatusCode(EXIT, keyId, roomId, 200);

//        ObjectMapper mapper = new ObjectMapper();
//        List<Room> newListRoom = new ArrayList<>();
//        Room room1 = mapper.readValue(responseBody, ListRooms.class);
//        System.out.println("room1 " + room1);
    }

    @SneakyThrows
    @Test
    public void testMapOutput(){
        List<Room> listRoom = new ArrayList<>();
//        ListRooms listRoom = new ListRooms();

        Room firstRoom = new Room();
        firstRoom.setRoomId(2);
        List<Integer> list1 = new ArrayList<>();
        list1.add(2);
        list1.add(4);
        firstRoom.setUserIds(list1);
        System.out.println("firstRoom " + firstRoom);

        Room secondRoom = new Room();
        secondRoom.setRoomId(3);
        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        secondRoom.setUserIds(list2);
        System.out.println("secondRoom " + secondRoom);

        ObjectMapper mapper = new ObjectMapper();
        listRoom.add(firstRoom);
        listRoom.add(secondRoom);
        String jsonString = mapper.writeValueAsString(listRoom);
        System.out.println("listRoom json " + jsonString);
    }

    @SneakyThrows
    @Test
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
                .asString()
                ;
        return body;
    }

    @Test
    public void getEveryBodyInRoom1Test() {
        System.out.println("getEveryBodyInRoom1Test");
        for (int keyId = 1; keyId < 11; keyId++) {
            int roomId = 1;
            System.out.println("keyId= " + keyId);
            testCheckWithStatusCode(ENTRANCE, keyId, roomId, 200);
            }
    }

    @Test
    public void getEveryBodyOutRoom1Test() {
        System.out.println("getEveryBodyInRoom1Test");
        for (int keyId = 1; keyId < 11; keyId++) {
            int roomId = 1;
            System.out.println("keyId= " + keyId);
            testCheckWithStatusCode(EXIT, keyId, roomId, 200);
        }
    }

    @SneakyThrows
    public void jsonGetInfoUsersCompereMaker(int keyId, int roomId) {
        String user = "id";

        ObjectMapper mapper = new ObjectMapper();
        List<Integer> listUsers = new ArrayList<>();
//        listUsers.add(user);
        String jsonString = mapper.writeValueAsString(listUsers);
        System.out.println("listRoom json " + jsonString);
    }

    @Test
    public void testGetInfoUsers(){
       String bodyResponse =  getInfoUsers(0, 20);
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
                .body("", Matchers.not(hasSize(0)))
                .extract()
                .body()
                .asString();
        return body;
    }

    @Test
    public void isItNight(){
        System.out.println(timeTest());
    }

    public boolean timeTest(){
    int hour = LocalTime.now().getHour();
        System.out.println("hour= " + hour);
    if (8 < hour & hour < 22){
        return false;
    }else {
        return true;
        }
    }

    @SneakyThrows
    @Test
    public void jsonCompereMakerRoom1() {
        int RoomId = 1;
        Room firstRoom = new Room();
        firstRoom.setRoomId(RoomId);
        List<Integer> list1 = new ArrayList<>();
        for (int i = 1; i<10; i++) {
            System.out.println("Здесь выводится i "+ i);
            list1.add(i);
        }
        firstRoom.setUserIds(list1);
        System.out.println("firstRoom " + firstRoom);

        ObjectMapper mapper = new ObjectMapper();
        List<Room> listRoom = new ArrayList<>();
        listRoom.add(firstRoom);
        String jsonString = mapper.writeValueAsString(listRoom);
        System.out.println("listRoom json " + jsonString);
    }

}
