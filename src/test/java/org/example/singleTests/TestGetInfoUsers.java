package org.example.singleTests;

import org.example.metods.getInfoUsers.GetInfoUsers;
import org.junit.Test;


public class TestGetInfoUsers {
    GetInfoUsers info = new GetInfoUsers();

    @Test
    public void testGetInfoUsers(){
        String bodyResponse = info.getInfo(0, 20);
        System.out.println("bodyResponse= " + bodyResponse);
    }
}
