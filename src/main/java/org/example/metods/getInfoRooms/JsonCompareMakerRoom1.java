package org.example.metods.getInfoRooms;

import io.cucumber.messages.internal.com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.entity.Room;

import java.util.ArrayList;
import java.util.List;

public class JsonCompareMakerRoom1 {

    @SneakyThrows
    public String jsonRoom1() {
        int RoomId = 1;
        Room firstRoom = new Room();
        firstRoom.setRoomId(RoomId);
        List<Integer> list1 = new ArrayList<>();
        for (int i = 1; i<11; i++) {
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
        return jsonString;
    }
}
