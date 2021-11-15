package org.example.singleTests;

import org.example.metods.getCheck.CheckWithStatusCode;
import org.junit.Test;

import static org.example.entity.Mode.ENTRANCE;
import static org.example.entity.Mode.EXIT;

public class InAndOutTest {
    CheckWithStatusCode test = new CheckWithStatusCode();

    @Test
    public void testCheckTest() {
        int keyId = 9;
        int roomId = 1;
        test.checkStatusCode(ENTRANCE, keyId, roomId, 200);
        test.checkStatusCode(EXIT, keyId, roomId, 200);
    }
}
