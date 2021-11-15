package org.example.metods.getInfoRooms;

import io.cucumber.messages.internal.com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.entity.Room;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class JsonCompareMaker {

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
}
