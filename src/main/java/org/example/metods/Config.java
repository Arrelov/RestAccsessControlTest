package org.example.metods;

import lombok.Data;
import lombok.Getter;

@Data
public class Config {
    @Getter
    String myUrl = "http://localhost:8080/";
    int workDayStartHour = 8;
    int workDayEndHour = 22;
}
