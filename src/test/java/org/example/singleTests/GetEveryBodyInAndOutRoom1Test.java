package org.example.singleTests;

import org.example.metods.getCheck.CheckWithStatusCode;
import org.junit.Test;

import static org.example.entity.Mode.ENTRANCE;
import static org.example.entity.Mode.EXIT;

public class GetEveryBodyInAndOutRoom1Test {
    CheckWithStatusCode test = new CheckWithStatusCode();

    @Test
    public void getEveryBodyInRoom1Test() {
        System.out.println("getEveryBodyInRoom1Test");
        for (int keyId = 1; keyId < 11; keyId++) {
            int roomId = 1;
            System.out.println("keyId= " + keyId);
            test.checkStatusCode(ENTRANCE, keyId, roomId, 200);
        }
        System.out.println("getEveryBodyInRoom1Test");
        for (int keyId = 1; keyId < 11; keyId++) {
            int roomId = 1;
            System.out.println("keyId= " + keyId);
            test.checkStatusCode(EXIT, keyId, roomId, 200);
        }
    }
}
