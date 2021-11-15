package org.example.metods;

import java.time.LocalTime;

public class NightTimeCheck {
    Config conf = new Config();

    public boolean nightTime(){
        int hour = LocalTime.now().getHour();
        System.out.println("hour= " + hour);
        if (conf.getWorkDayStartHour() < hour & hour < conf.getWorkDayEndHour()){
            System.out.println("Its work time");
            return false;
        }else {
            System.out.println("Its NIGHT time!");
            return true;
        }
    }
}
