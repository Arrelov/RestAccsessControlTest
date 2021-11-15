package org.example.singleTests;

import io.cucumber.java.AfterAll;
import org.example.metods.getCheck.CheckReturnStatusCode;
import org.junit.Test;

import static org.example.entity.Mode.EXIT;

public class ResetRoomsAllOut {

    @Test
    public void everyBodyOut() {
        System.out.println("AfterAll everyBodyOut");
        for (int keyId = 1; keyId < 11; keyId++) {
            System.out.println("keyId= " + keyId);
            for (int roomId = 1; roomId < 6; roomId++) {
                if (keyId % roomId == 0) {
                    System.out.println("\tGo home! keyId= " + keyId + " roomId= " + roomId);
                    CheckReturnStatusCode check = new CheckReturnStatusCode();
                    check.statusCode(EXIT, keyId, roomId);
                }
            }
        }
    }
}
