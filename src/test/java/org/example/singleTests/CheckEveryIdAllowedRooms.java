package org.example.singleTests;

import org.example.metods.getCheck.CheckWithStatusCode;
import org.junit.Test;

import static org.example.entity.Mode.ENTRANCE;
import static org.example.entity.Mode.EXIT;

public class CheckEveryIdAllowedRooms {
    CheckWithStatusCode test = new CheckWithStatusCode();

    @Test
    public void getInAndOutPositiveTest() {
        System.out.println("Start");
        for (int keyId = 1; keyId < 11; keyId++) {
            System.out.println("keyId= " + keyId);
            for (int roomId = 1; roomId < 6; roomId++) {
                if (keyId % roomId == 0) {
                    System.out.println("\tAccess granted! keyId= " + keyId + " roomId= " + roomId);
                    test.checkStatusCode(ENTRANCE, keyId, roomId, 200);
                    test.checkStatusCode(EXIT, keyId, roomId, 200);
                } else {
                    System.out.println("\tAccess denied! keyId= " + keyId + " roomId= " + roomId);
                }
            }
        }
    }
}
