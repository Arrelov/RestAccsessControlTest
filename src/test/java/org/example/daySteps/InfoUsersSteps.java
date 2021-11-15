package org.example.daySteps;

import io.cucumber.java.en.When;
import org.example.metods.getInfoUsers.GetInfoUsers;

public class InfoUsersSteps {

    @When("I run get info users request and get response 200")
    public void i_run_get_info_users_request_and_get_response() {
        GetInfoUsers users = new GetInfoUsers();
        users.getInfo(0, 20);
    }

    @When("I send request start {int} to end {int} and get response 200")
    public void i_send_request_start_to_and_get_response(Integer start, Integer end) {
        GetInfoUsers users = new GetInfoUsers();
        String bodyResponse = users.getInfo(start, end);
        System.out.println("bodyResponse= " + bodyResponse);
    }
}
